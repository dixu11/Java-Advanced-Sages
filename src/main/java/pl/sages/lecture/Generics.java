package pl.sages.lecture;

import java.util.Collections;
import java.util.List;

interface Employee{
    void work();
}
record Programmer() implements Employee{

    @Override
    public void work() {

    }
}
record Tester() implements Employee{

    @Override
    public void work() {

    }
}
public class Generics {

    static void main() {
        Programmer programmer = new Programmer();
        Tester tester = new Tester();
        List<Employee> list = List.of(programmer, tester);
        actions(list);
        List<Programmer> programmers = List.of(programmer);
        actions(list);
        List<?> test = list;

    }



    static void actions(List<? super Employee> employees){
        for (Object employee : employees) {
            //employee.work();
        }
    }
}
