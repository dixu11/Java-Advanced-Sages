package pl.sages.jext.ch0.setup;

public class ExhaustiveSwitchExpression {

    enum Example{
        A,B,C, D
    }

     void main() {
         Example example = Example.A;

        String result =  switch (example){
            case A -> "result for A";
            case B -> "result for B";
            case C -> "result for C";
            case D -> "result for D";
        };
         System.out.println(result);
    }
}
