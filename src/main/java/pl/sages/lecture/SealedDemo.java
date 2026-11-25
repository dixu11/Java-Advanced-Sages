package pl.sages.lecture;

//sealed napisany bez permitów dostaje automatycznie permity na wszystkie implementacje
//w obrębie tego pliku .java
sealed interface A { }

record B() implements A{ }
record C() implements A{ }
non-sealed class D implements A { }


public class SealedDemo {

    static void main() {
        int non = 10;
        int sealed = 20;
        System.out.println(non-sealed);




        A a = new C();

        switch (a){
            case B b -> {
            }
            case C c -> {
            }
            case D d -> {
            }
        }
    }

}
