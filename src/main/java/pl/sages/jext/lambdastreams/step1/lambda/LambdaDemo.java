package pl.sages.jext.lambdastreams.step1.lambda;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class LambdaDemo {

    private int myNumber;

    public LambdaDemo(int myNumber) {
        this.myNumber = myNumber;
//        Inner myMinion = this.new Inner();
    }

    static class Inner {
        public Inner() {
//            System.out.println("My master holds: " + myNumber);
        }
    }


    static void main() {

        LambdaDemo lambdaDemo = new LambdaDemo(7);
        //bez static inner klasa korzysta z pól obiektu który musi zostać wskazany przy tworzeniu
//        Inner inner = lambdaDemo.new Inner();
        //ze static niezależny od obiektu zewnętrznej
        LambdaDemo.Inner staticInner = new Inner();


        // MyButton button = new MyButton(10,30,"Calculate");

        //button.setAction(calculate);

        CalculateButton button = new CalculateButton(10, 30, "Calculate");
        System.out.println(button);
        button.click();
        System.out.println(button);
        button.click();
        System.out.println(button);
        button.setOperation(OperationType.MINUS);
        button.click();
        System.out.println(button);

     /*   class LocalClass extends MyButton{

            public LocalClass(int y, int x, String textLabel) {
                super(y, x, textLabel);
            }

            @Override
            void click() {

            }
        }*/

//        MyButton localClass = new LocalClass();


        MyButton addButton = new MyButton(10, 20, "Add") {
            @Override
            void click() {
                number++;
            }
        };
        MyButton subtractButton = new MyButton(10, 20, "Add") {
            @Override
            void click() {
                number--;
            }
        };


        JButton button1 = new JButton();
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Przyciśnięty");
            }
        });


        JButton button2 = new JButton();
        button1.addActionListener(
                (ActionEvent e) -> {
                    System.out.println("Przyciśnięty");
                }
        );


        Calculator calculator = new Calculator();
        calculator.setOperation(new CalcOperation() {
            @Override
            public int calculate(int a, int b) {
                return a+b;
            }
        });
        System.out.println(calculator.execute(2,2));
        calculator.setOperation(( a,  b) -> a-b);
        System.out.println(calculator.execute(10, 3));
    }

  /*  static void calcuate(){
        number++;
    }*/


}

interface CalcOperation{
 int calculate(int a, int b);
}

class Calculator{

    private CalcOperation operation;

    int execute(int a, int b) {
        return operation.calculate(a, b);
    }

    public void setOperation(CalcOperation operation) {
        this.operation = operation;
    }
}











enum OperationType {
    PLUS, MINUS
}

abstract class MyButton {

    //calc
    OperationType operation = OperationType.PLUS;
    int number = 0;

    //view
    private String textLabel;
    private int x, y;

    public MyButton(int y, int x, String textLabel) {
        this.y = y;
        this.x = x;
        this.textLabel = textLabel;
    }

    abstract void click();

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    /*void setAction(){

    }*/


    @Override
    public String toString() {
        return "MyButton{" +
                "operation=" + operation +
                ", number=" + number +
                '}';
    }
}

class CalculateButton extends MyButton {

    public CalculateButton(int y, int x, String textLabel) {
        super(y, x, textLabel);
    }

    @Override
    void click() {
        switch (operation) {
            case PLUS -> number++;
            case MINUS -> number--;
        }
    }
}

//part 1 - problem
/*

package pl.sages.jext.lambdastreams.step1.lambda;

public class LambdaDemo {



    static void main() {
        MyButton button = new MyButton(10,30,"Calculate");

        //button.setAction(calculate);
    }

  *//*  static void calcuate(){
        number++;
    }*//*




}

enum OperationType{
    PLUS, MINUS
}

class MyButton{

    //calc
    private OperationType operation = OperationType.PLUS;
    private int number = 0;

    //view
    private String textLabel;
    private int x,y;

    public MyButton(int y, int x, String textLabel) {
        this.y = y;
        this.x = x;
        this.textLabel = textLabel;
    }

    void click(){
        System.out.println("Przycisk robi....");
    }

    void setAction(){

    }


}*/

