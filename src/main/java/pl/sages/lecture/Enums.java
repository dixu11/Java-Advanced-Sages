package pl.sages.lecture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum Size {
    S(5), M(7), L(10), XL(15);

    private int numericSize;

    Size(int numericSize) {
        this.numericSize = numericSize;
    }

    public int getNumericSize() {
        return numericSize;
    }

    public boolean isLarge() {
        return numericSize > 10;
    }

    public String describe() {
        return numericSize > 10 ? "wielka" : "mała";
    }

//    abstract List<String> howToCreate();
}

record Terrain(String name) {
}

public class Enums {


    static void main() {


        Size size = Size.valueOf("M");
        System.out.println(size.getNumericSize());
        System.out.println(size.name());
        System.out.println(size.ordinal());
        for (Size value : Size.values()) {
            System.out.println(value);
        }

        System.out.println("-----");
        System.out.println(terrains);
        System.out.println(getTerrain("terrain a"));
        System.out.println(terrains);
        System.out.println(getTerrain("terrain a"));
        System.out.println(terrains);
        System.out.println(getTerrain("terrain b"));
        System.out.println(terrains);
        System.out.println(getTerrain2("terrain b"));
        System.out.println(terrains);
        System.out.println(getTerrain2("terrain c"));
        System.out.println(terrains);
        System.out.println(getTerrain2("terrain c"));
        System.out.println(terrains);
    }

    static Map<String, Terrain> terrains = new HashMap<>();

    static Terrain getTerrain(String askedTerrain) {
        if (terrains.containsKey(askedTerrain)) {
            return terrains.get(askedTerrain);
        }
        Terrain created = new Terrain(askedTerrain);
        terrains.put(askedTerrain, created);
        return created;
    }

    static Terrain getTerrain2(String askedTerrain) {
        return terrains.computeIfAbsent(askedTerrain,
                element -> new Terrain(element));
    }

}
