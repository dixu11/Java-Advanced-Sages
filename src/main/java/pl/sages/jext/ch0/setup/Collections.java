package pl.sages.jext.ch0.setup;

import java.util.*;

public class Collections {
    static void main() {
        List<Integer> immutableNumberList = List.of(1, 2, 3);
        System.out.println(immutableNumberList.getClass());
//        immutableNumberList.add(4);
//        immutableNumberList.set(0, 7);
        List<Integer> mutable = new ArrayList<>(immutableNumberList);

        mutable.add(4);
        mutable.set(0, 7);
        System.out.println(mutable);
        System.out.println(immutableNumberList);

        System.out.println("rev example");
        List<Integer> elements = new ArrayList<>(List.of(1, 2, 3));
        List<Integer> reversedView = elements.reversed();
        System.out.println(elements);
        System.out.println(reversedView);
        List<Integer> kopiaOdwrotnieModyfikowalna = new ArrayList<>(elements.reversed());
        reversedView.add(7);
        reversedView.addFirst(8);
        System.out.println(elements);
        System.out.println(reversedView);

        //System.out.println(List.of(1, null));

        //System.out.println(Set.of("a","a"));
        Set<String> name = new HashSet<>();
        name.add("Adam");
        name.add("Adam");
        System.out.println(name);


    }
}
