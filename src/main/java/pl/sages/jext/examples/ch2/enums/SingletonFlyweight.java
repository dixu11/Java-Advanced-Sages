package pl.sages.jext.examples.ch2.enums;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Slajdy 80-81: enum jako Singleton oraz Flyweight w wersji dynamicznej (fabryka z pulą) i statycznej (stałe enuma).
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.enums.SingletonFlyweight}
 */
public class SingletonFlyweight {

    // --- slajd 80 ---
    enum Configuration {
        INSTANCE;

        String region() { return "EU"; }
    }

    interface Terrain {
        int cost();
    }

    record Cell(int x, int y, Terrain terrain) {}

    // --- slajd 81: dynamiczna ---
    record TerrainSpec(String name, int cost) {}

    record DynamicTerrain(String name, int cost)
            implements Terrain {}

    static final class TerrainFactory {
        private final Map<TerrainSpec, Terrain> pool =
                new ConcurrentHashMap<>();

        Terrain get(TerrainSpec spec) {
            return pool.computeIfAbsent(spec,
                    key -> new DynamicTerrain(
                            key.name(), key.cost()));
        }

        int size() { return pool.size(); } // dopisane do pokazu
    }

    // --- slajd 81: statyczna ---
    enum StaticTerrain implements Terrain {
        GRASS(1), WATER(3);

        private final int cost;

        StaticTerrain(int cost) {
            this.cost = cost;
        }

        @Override
        public int cost() { return cost; }
    }

    public static void main(String[] args) {
        String region = Configuration.INSTANCE.region();
        System.out.println("47: region = " + region + ", ta sama instancja: " + (Configuration.INSTANCE == Configuration.valueOf("INSTANCE")));

        // slajd 81: ten sam sposób użycia
        var factory = new TerrainFactory();
        Terrain dynamic = factory.get(
                new TerrainSpec("mud", 2));
        Terrain fixed = StaticTerrain.GRASS;
        var first = new Cell(0, 0, dynamic);
        var next = new Cell(1, 0, fixed);
        System.out.println("48: " + first + ", " + next);

        // Ten sam opis zwraca tę samą instancję z danej fabryki
        Terrain again = factory.get(new TerrainSpec("mud", 2));
        System.out.println("48: mud drugi raz to ta sama instancja: " + (dynamic == again) + ", w puli: " + factory.size());
        var grid = new Cell[100];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = new Cell(i % 10, i / 10, i % 2 == 0 ? factory.get(new TerrainSpec("mud", 2)) : StaticTerrain.WATER);
        }
        System.out.println("48: 100 komórek, a obiektów terenu w puli nadal " + factory.size() + " (+ stała WATER)");
    }
}
