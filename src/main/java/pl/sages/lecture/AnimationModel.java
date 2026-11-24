package pl.sages.lecture;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AnimationModel {
    // AnimationType type();
    String quality() default "MEDIUM";

    AnimationType value();// default AnimationType.HUMANOID;


}

class Demo {
    void main() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Person person = new Person();

        Class<Person> myClass1 = Person.class;
        Class<?> myClass2 = person.getClass();
        Class<?> myClass3 = Class.forName("pl.sages.lecture.Person");
        System.out.println(myClass3.isAnnotation());
        System.out.println(myClass3.getSimpleName());
        System.out.println(myClass3.getFields().length);
        System.out.println(myClass3.getName());
//        System.out.println(myClass3.);
//        System.out.println(myClass3.);
        Field[] fields = myClass3.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
            System.out.println(field.getType());
        }

        AnimationModel annotation = myClass3.getAnnotation(AnimationModel.class);
        System.out.println(annotation);
        if (annotation != null) {
            System.out.println("Znaleziono:");
            System.out.println("Quality: " + annotation.quality());
            System.out.println("Value: " + annotation.value());
        }


        Optional.ofNullable(myClass3.getAnnotation(AnimationModel.class))
                .ifPresent(model -> {
                    System.out.println("Znaleziono:");
                    System.out.println("Quality: " + model.quality());
                    System.out.println("Value: " + model.value());
                });

        Person person2 = new Person();
        System.out.println(person2);
        Field fieldName = myClass3.getDeclaredField("name");
        fieldName.setAccessible(true);
        fieldName.set(person2,"Roman");
        System.out.println(person2);

        Constructor<?> constructor = myClass3.getConstructor(int.class, String.class);
       Person arnold = (Person) constructor.newInstance(20,"Arnold");
        System.out.println(arnold);

        Method birthday = myClass3.getMethod("birthday");
        birthday.invoke(arnold);
        System.out.println(arnold);
    }
}


//@AnimationModel(type = AnimationType.HUMANOID, quality = "HIGH")
@AnimationModel(AnimationType.HUMANOID)
class Person {

    //    @AnimationModel
    private int age;
    //    @AnimationModel
    private String name;
    public String text;

    //@MyAnnotation
    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public Person() {
        name = "Ania";
        age = 18;
    }

    public void birthday() {
        System.out.println(name + "ma urodziny!");
        System.out.println("Nowy wiek: " + ++age);
    }


    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    //    @AnimationModel
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    static void main() {
//        Person person = new Person(10,"Jan");
//        System.out.println(person);
        Person person = new Person();
        System.out.println(person);
        for (Integer number : List.of(1, 2, 3)) {
            System.out.println(number);
        }
    }
}
