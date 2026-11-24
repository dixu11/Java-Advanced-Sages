package pl.sages.lecture;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class ExceptionsDemo {

    static void main() throws FileNotFoundException {

        int[] szczesliweLiczby = {23, 45, 56, 243, 23, 12};
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Podaj index");
            int wybrana = scanner.nextInt();
            System.out.println("twoja liczba: " + szczesliweLiczby[wybrana]);
        } catch (InputMismatchException e) {
            System.out.println("Nie wolno pisać tekstu");
            main();
        }


        getLuckyNumber();

      




        Arrays.stream(szczesliweLiczby)
                .peek(number -> {
                            try {
                                new Scanner(new File("text" + number + ".txt"));
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .sum();


    }


    static int getLuckyNumber() throws FileNotFoundException {
        int[] szczesliweLiczby = {23, 45, 56, 243, 23, 12};
        Scanner scanner2 = new Scanner(new File("text.txt"));
        try {
            Scanner scanner = new Scanner(new File("text.txt"));
            System.out.println("Podaj index");
            int wybrana = scanner.nextInt();
//          System.out.println("twoja liczba: " + szczesliweLiczby[wybrana]);
            System.out.println("Nie wolno pisać tekstu");
            return wybrana;
        } catch (FileNotFoundException e) {
            System.out.println("plik nie zlaleziony");
            return -1;
        }
    }
}
