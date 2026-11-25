package pl.sages.jext.ch2.practice;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import pl.sages.jext.ch2.practice.model.Entity;

import java.util.List;

/**
 * Ćwiczenie 2. Sprawdzenie: test {@code Zad02ValidatorTest}.
 *
 * Ćwiczenie 2c, dla chętnych (slajdy 54-60): skanowanie classpath biblioteką ClassGraph.
 */
public class EntityScanner {

    /**
     * Zwróć pełne nazwy klas z podanego pakietu, które mają adnotację @Entity, posortowane alfabetycznie.
     *
     * Podpowiedź:
     * new ClassGraph().enableAnnotationInfo().acceptPackages(packageName).scan()
     * (w try-with-resources), potem scan.getClassesWithAnnotation(Entity.class).getNames().
     */
    public static List<String> findEntityClasses(String packageName) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
