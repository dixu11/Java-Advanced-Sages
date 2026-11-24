package pl.sages.jext.ch0.setup;

class A{

    int numerA;

    public A(int numberA){
        this.numerA = numberA;
        System.out.println("Buduję A");
        super();

    }

    public A(){
        //this(7); //przekieruje do innego konstruktora z tej klasy
        System.out.println("Buduję A bez parametru");
    }



    void upperClassMethod() {
        System.out.println("Up");
    }

    void display() {
        System.out.println("I am A");
    }

}


class B extends A {

    int numberB;


    public B(int numberB){
        if (numberB < 0) {
            throw new IllegalArgumentException("parametry muszą być dodatnie");
        }
      /*  this.numberB = numerA;
        System.out.println(this.numberB);
        lower();*/
        numberB = 0;
//        Inheritance.consume(this);
//        System.out.println(this);
        super(numberB);
        this.numberB = numerA;
        System.out.println("Buduję B");
//        A a = this;
        System.out.println(this);

    }

    void lower() {
        System.out.println("Down");
    }

    void display() {
        System.out.println("I am B");
    }
}

class C extends  A{
    void special() {
        System.out.println("special implementation");
    }
}

class D extends  A{
    void specialExtra() {
        System.out.println("extra special implementation");
    }
}


public class Inheritance {
    static void consume(A a){

    }

    static void main() {
        A a = new A();
        A b = new B(10);
        System.out.println(a);


        System.out.println("A");
        a.upperClassMethod();
        a.display();

        System.out.println("B");
        b.upperClassMethod();
        b.display();
       // b.lower();


        A superclassReference = new B(10);
        superclassReference = new D();
        String classType = "";

        if (superclassReference instanceof B subclassReference) {
            //B subclassReference = (B) superclassReference;
            subclassReference.lower();
            classType = "B";
        } else if (superclassReference instanceof C specialReference) {
            //C specialReference = (C) superclassReference;
            specialReference.special();
            classType = "C";
        } else {
            classType = "Unknown";
        }
        System.out.println(classType);

        classType = switch (superclassReference){
            case B subclassB -> "B";
            case C subclassC -> "C";
            case null, default -> "Unknown";
        };
        System.out.println(classType);




    }
}
