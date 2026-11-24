package pl.sages.jext.examples.ch7.optimization;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.StructLayout;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.Optional;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

/**
 * Slajdy 172-175: {@code ProcessHandle}, FFM API: arena i segmenty pamięci, wyszukanie funkcji, deskryptor,
 * downcall ({@code strlen}) i upcall ({@code qsort} z porównaniem w Javie).
 * <p>
 * Poprawki (dziennik błędów): slajd 172 {@code processHandle.command()} i {@code user()} nie istnieją
 * ({@code processInfo.command()}); slajd 174 {@code processing.allocate} to {@code arena.allocate};
 * slajd 175 brak przecinka w {@code methodType}. Slajd 175 ładuje {@code libGL.so}, która jest tylko na Linuksie,
 * więc przykład szuka jej bez przerywania programu.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch7.optimization.ProcessesAndMemory}
 */
public class ProcessesAndMemory {

    // --- slajd 175 ---
    static class Qsort {
        static int qsortCompare(MemorySegment elem1, MemorySegment elem2) {
            return Integer.compare(elem1.get(JAVA_INT, 0), elem2.get(JAVA_INT, 0));
        }
    }

    public static void main(String[] args) throws Throwable {
        // --- slajd 172 ---
        ProcessHandle processHandle = ProcessHandle.current();
        ProcessHandle.Info processInfo = processHandle.info();
        System.out.println("138: pid = " + processHandle.pid());
        System.out.println("138: command = " + processInfo.command().orElse("?")); // na slajdzie processHandle.command().get()
        System.out.println("138: user = " + processInfo.user().orElse("?"));       // na slajdzie processHandle.user().get()

        // --- slajd 174 ---
        try (var arena = Arena.ofConfined()) {
            MemorySegment input = arena.allocate(100);  // na slajdzie processing.allocate
            MemorySegment output = arena.allocate(100);
            var firstByte = input.get(ValueLayout.JAVA_BYTE, 0);
            System.out.println("140: firstByte = " + firstByte + ", rozmiary " + input.byteSize() + " i " + output.byteSize() + " B");

            // --- slajd 175 ---
            MemorySegment numbers = arena.allocate(JAVA_INT, 4);
            for (int i = 0; i < 4; i++) {
                numbers.setAtIndex(JAVA_INT, i, (i + 1) * 10);
            }
            System.out.println("141: toArray = " + Arrays.toString(numbers.toArray(JAVA_INT))
                    + ", asSlice(4, 8) = " + Arrays.toString(numbers.asSlice(4, 8).toArray(JAVA_INT)));
            MemorySegment cString = arena.allocateFrom("Java");
            System.out.println("141: getString = " + cString.getString(0) + ", mismatch z \"Jawa\" = "
                    + MemorySegment.mismatch(cString, 0, cString.byteSize(), arena.allocateFrom("Jawa"), 0, 5));
            try {
                numbers.getAtIndex(JAVA_INT, 4);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("141: indeks poza segmentem: IndexOutOfBoundsException");
            }
            var emptySegment = MemorySegment.ofAddress(100);
            System.out.println("141: ofAddress(100): adres " + emptySegment.address() + ", rozmiar " + emptySegment.byteSize());
        }

        // --- slajd 175 ---
        try (Arena arena = Arena.ofConfined()) {
            SymbolLookup opengl = SymbolLookup.libraryLookup("libGL.so", arena);
            // metoda o podanej nazwie może nie zostać znaleziona, stąd Optional
            Optional<MemorySegment> glVersion = opengl.find("glGetString");
            System.out.println("142: glGetString znalezione: " + glVersion.isPresent());
        } catch (IllegalArgumentException e) {
            System.out.println("142: libGL.so niedostępna w tym systemie (" + e.getMessage() + ")");
        }
        Linker nativeLinker = Linker.nativeLinker();
        SymbolLookup stdlib = nativeLinker.defaultLookup();
        MemorySegment malloc = stdlib.find("malloc").orElseThrow();
        System.out.println("142: malloc pod adresem " + malloc.address());

        // --- slajd 175 ---
        StructLayout RECTANGLE = MemoryLayout.structLayout(ValueLayout.ADDRESS.withTargetLayout(
                MemoryLayout.sequenceLayout(4, MemoryLayout.structLayout( // 4 elementowa tablica struktur
                        ValueLayout.JAVA_INT.withName("x"), ValueLayout.JAVA_INT.withName("y")).withName("point"))
        ).withName("points"));
        System.out.println("143: " + RECTANGLE + ", rozmiar " + RECTANGLE.byteSize() + " B");

        // --- slajd 175 ---
        Linker linker = Linker.nativeLinker();
        MethodHandle strlen = linker.downcallHandle(
                linker.defaultLookup().find("strlen").get(),
                FunctionDescriptor.of(JAVA_LONG, ADDRESS)
        );
        MethodHandle qsort = linker.downcallHandle(
                linker.defaultLookup().find("qsort").get(),
                FunctionDescriptor.ofVoid(ADDRESS, JAVA_LONG, JAVA_LONG, ADDRESS)
        );

        // --- slajd 175 ---
        MethodHandle comparHandle = MethodHandles.lookup().findStatic(Qsort.class, "qsortCompare",
                MethodType.methodType(int.class, MemorySegment.class, MemorySegment.class)); // na slajdzie brak przecinka
        MemorySegment comparFunc = linker.upcallStub(comparHandle, FunctionDescriptor.of(
                        JAVA_INT,
                        ADDRESS.withTargetLayout(JAVA_INT),
                        ADDRESS.withTargetLayout(JAVA_INT)),
                Arena.ofAuto());

        try (var arena = Arena.ofConfined()) {
            long length = (long) strlen.invokeExact(arena.allocateFrom("Zaawansowana Java"));
            System.out.println("144: strlen(\"Zaawansowana Java\") = " + length);

            int[] data = {42, 7, 19, 3, 25};
            MemorySegment array = arena.allocateFrom(JAVA_INT, data);
            qsort.invokeExact(array, (long) data.length, JAVA_INT.byteSize(), comparFunc);
            System.out.println("145: qsort z porównaniem w Javie: " + Arrays.toString(data) + " -> "
                    + Arrays.toString(array.toArray(JAVA_INT)));
        }
    }
}
