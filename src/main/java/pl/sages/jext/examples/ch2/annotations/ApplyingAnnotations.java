package pl.sages.jext.examples.ch2.annotations;

import java.awt.geom.Point2D;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

/**
 * Slajd 42: gdzie można postawić adnotację (klasa, pole, konstruktor, metoda, użycie typu, zmienna lokalna).
 * <p>
 * Poprawka względem slajdu: konstruktor {@code Soldier}, nie {@code Solider} (dziennik błędów).
 * Definicji adnotacji na slajdzie nie ma, więc są niżej. {@code main} odczytuje je refleksją;
 * adnotacji zmiennej lokalnej refleksja nie widzi nigdy (nie trafia do pliku .class w czytelnej formie).
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.annotations.ApplyingAnnotations}
 */
public class ApplyingAnnotations {

    @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.TYPE, ElementType.TYPE_USE}) @interface TypeAnnotation {}
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.FIELD) @interface FieldAnnotation {}
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.CONSTRUCTOR) @interface ConstructorAnnotation {}
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD) @interface MethodAnnotation {}
    @Retention(RetentionPolicy.RUNTIME) @Target(ElementType.LOCAL_VARIABLE) @interface LocalVariableAnnotation {}

    @TypeAnnotation
    static class Soldier {
        @FieldAnnotation
        private int x;
        private int y;

        @ConstructorAnnotation
        public Soldier(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @MethodAnnotation
        public double calculateDistance(Integer y,
                                        @TypeAnnotation Integer x) {
            @LocalVariableAnnotation double result =
                    Point2D.distance(this.x, this.y, x, y);
            return result;
        }
    }

    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
        var type = Soldier.class;
        System.out.println("klasa:       " + Arrays.toString(type.getAnnotations()));
        System.out.println("pole x:      " + Arrays.toString(type.getDeclaredField("x").getAnnotations()));
        System.out.println("konstruktor: " + Arrays.toString(type.getConstructor(int.class, int.class).getAnnotations()));
        var method = type.getMethod("calculateDistance", Integer.class, Integer.class);
        System.out.println("metoda:      " + Arrays.toString(method.getAnnotations()));
        System.out.println("typ param. x:" + Arrays.toString(method.getAnnotatedParameterTypes()[1].getAnnotations()));
        System.out.println("odległość:   " + new Soldier(0, 0).calculateDistance(4, 3));
    }
}
