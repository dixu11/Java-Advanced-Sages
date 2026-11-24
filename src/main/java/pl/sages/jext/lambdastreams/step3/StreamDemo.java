package pl.sages.jext.lambdastreams.step3;

import java.util.*;
import java.util.stream.Stream;

class NewPerson implements Comparable<NewPerson> {

    private String name;
    private int age;

    public NewPerson(String name) {
        this.name = name;
        age = new Random().nextInt(10, 51);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        NewPerson newPerson = (NewPerson) o;
        return Objects.equals(name, newPerson.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "NewPerson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(NewPerson o) {
        return name.compareTo(o.name);
    }
}

public class StreamDemo {

    static void main() {
        List<String> namesOriginal = List.of("Adrian", "Adam", "Ania", "Zofia", "Radek", "Adrian");
        List<String> names = new ArrayList<>(namesOriginal);
        names.sort((name1, name2) -> -name1.compareTo(name2));
        System.out.println(names);
        names.removeIf(name -> name.endsWith("a"));
        System.out.println(names);
        names.replaceAll(String::toUpperCase);
        System.out.println(names);

        System.out.println(namesOriginal);

        String abc = "abc";


        // Stream.of("text1","text2")
        //Arrays.stream(new int[]{1, 2, 3, 45});
        List<String> result = namesOriginal.stream()
                .sorted((name1, name2) -> -name1.compareTo(name2))
                .filter(name -> !name.endsWith("a"))
                .map(name -> name.toUpperCase())
                .toList();

        System.out.println(result);
        int maxAge = namesOriginal.stream()
                //.limit(0)
                .map((String name) -> new NewPerson(name))
                .sorted()
                .distinct()
                .peek(person -> System.out.println(person))
                .mapToInt(person -> person.getAge())
                .peek(age -> System.out.println(age))
                .max()
                .orElseThrow(()-> new IllegalStateException("Pusty zbiór"));
                //.orElse(-1);
        System.out.println(maxAge);


        NewPerson maxAgePerson = namesOriginal.stream()
                //.limit(0)
                .map((String name) -> new NewPerson(name))
                .sorted()
                .distinct()
                .peek(person -> System.out.println(person))
                .filter(person -> person.getName().equals("Zofia"))
                //.max((p1, p2) -> p1.getAge() - p2.getAge())
                .findAny()
                .orElse(null);
        System.out.println(maxAgePerson);
        //.orElse(-1);

        //.limit(3)


        Random random = new Random();


        //sposoby wygenerowania liczb do stream
  /*      List<Integer> numbers = random.ints(100_000_000, 1, 11)
                .boxed()
                .toList();
        System.out.println(numbers);
        List<Integer> viaGenerate = Stream.generate(() -> random.nextInt(FROM, TO + 1))
                .limit(100_000_000)                                      // generate() jest nieskończony, limit go ucina
                .toList();
        List<Integer> viaRange = IntStream.range(0, COUNT)
                .map(i -> random.nextInt(FROM, TO + 1))
                .boxed()
                .toList();*/


    }


}
