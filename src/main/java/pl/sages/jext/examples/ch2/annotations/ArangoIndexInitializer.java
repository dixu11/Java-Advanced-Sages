package pl.sages.jext.examples.ch2.annotations;

import io.github.classgraph.ClassGraph;
import pl.sages.jext.examples.ch2.annotations.model.Entity;
import pl.sages.jext.examples.ch2.annotations.model.Index;

import java.util.Set;

/**
 * Slajdy 57-58: tworzenie indeksów bazy na podstawie adnotacji {@code @Entity} i {@code @Index}
 * (encje w pakiecie {@code ...examples.ch2.annotations.model}, na slajdzie {@code com.example.model}).
 * <p>
 * Zamiast ArangoDB zaślepka w pamięci: {@code getOrCreateArangoCollection} i {@code createIndex}
 * tylko wypisują, co poszłoby do bazy.
 * <p>
 * Poprawka względem slajdu: ClassGraph nie ma metody {@code enableClasspath()} (dziennik błędów),
 * classpath skanuje domyślnie.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.annotations.ArangoIndexInitializer}
 */
public class ArangoIndexInitializer {

    static final String BASE_PACKAGE = "pl.sages.jext.examples.ch2.annotations.model";

    /** Zaślepka kolekcji ArangoDB. */
    record ArangoCollection(String name) {}

    public void init() throws ClassNotFoundException {
        try (var scan = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .acceptPackages(BASE_PACKAGE)
                .scan()) {
            var entities = scan
                    .getClassesWithAnnotation(
                            Entity.class.getName());
            for (var info : entities) {
                var type = Class.forName(info.getName());
                generateIndexesForEntity(type);
            }
        }
    }

    private void generateIndexesForEntity(
            Class<?> type) {
        var collection = getOrCreateArangoCollection(
                type.getAnnotation(Entity.class));
        for (var index :
                type.getAnnotationsByType(Index.class)) {
            createIndex(collection, index,
                    Set.of(index.properties()));
        }
        for (var field : type.getDeclaredFields()) {
            for (var index :
                    field.getDeclaredAnnotationsByType(
                            Index.class)) {
                createIndex(collection, index,
                        Set.of(field.getName()));
            }
        }
    }

    // --- zaślepki operacji bazy (na slajdzie „operacje bazy pominięto”) ---

    private ArangoCollection getOrCreateArangoCollection(Entity entity) {
        // @Entity nie ma atrybutów, więc z samej adnotacji nie wynika nazwa kolekcji (dziennik: USPRAWNIENIE)
        System.out.println("getOrCreateArangoCollection(" + entity + ")");
        return new ArangoCollection("?");
    }

    private void createIndex(ArangoCollection collection, Index index, Set<String> properties) {
        System.out.println("  createIndex(name=" + index.name() + ", type=" + index.indexType()
                + ", properties=" + properties + ")");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        new ArangoIndexInitializer().init();
    }
}
