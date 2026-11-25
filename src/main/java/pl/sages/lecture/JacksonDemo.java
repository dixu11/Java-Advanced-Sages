package pl.sages.lecture;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Demo: podstawy Jacksona na przykładzie biblioteki książek
 * (nie płatności ze slajdów ani zamówień z ćwiczeń). Każdy krok to osobny blok w main.
 */
public class JacksonDemo {

    // Rekord działa z Jacksonem od razu: nazwy pól z komponentów, odczyt przez kanoniczny konstruktor.
    record Book(String title, String author, int year, List<String> tags) {
    }

    // Adnotacje zmieniają kontrakt JSON bez zmiany nazw w Javie.
    record Reader(
            @JsonProperty("full_name") String name,   // w JSON inna nazwa pola
            String email,
            @JsonIgnore String password) {            // nigdy nie trafia do JSON
    }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();     // ciężki w tworzeniu, bezpieczny wątkowo: jeden na aplikację

        // 1. Obiekt -> JSON (serializacja)
        Book book = new Book("Solaris", "Lem", 1961, List.of("sf", "pl"));
        String json = mapper.writeValueAsString(book);
        System.out.println("1. " + json);
        // {"title":"Solaris","author":"Lem","year":1961,"tags":["sf","pl"]}

        // 2. Ładniej, z wcięciami (do logów albo pliku)
        System.out.println("2.\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(book));

        // 3. JSON -> obiekt (deserializacja): podajesz klasę docelową
        Book back = mapper.readValue(json, Book.class);
        System.out.println("3. " + back + ", równy oryginałowi: " + back.equals(book));

        // 4. Lista: List.class nie zna typu elementów (wymazywanie typów), dlatego TypeReference
        String many = """
                [{"title":"Lalka","author":"Prus","year":1890,"tags":["klasyka"]},
                 {"title":"Ferdydurke","author":"Gombrowicz","year":1937,"tags":[]}]""";
        List<Book> books = mapper.readValue(many, new TypeReference<List<Book>>() {});
        System.out.println("4. " + books.get(0).title() + ", " + books.get(1).title());

        // 5. Nieznane pole w JSON: domyślnie wyjątek (bezpiecznie, ale kruche, gdy API dorzuci pole)
        String fromNewerApi = """
                {"title":"Quo vadis","author":"Sienkiewicz","year":1896,"tags":[],"isbn":"978-83"}""";
        try {
            mapper.readValue(fromNewerApi, Book.class);
        } catch (Exception e) {
            System.out.println("5. wyjątek: " + e.getClass().getSimpleName());   // UnrecognizedPropertyException
        }
        ObjectMapper tolerant = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        System.out.println("5. tolerancyjny mapper: " + tolerant.readValue(fromNewerApi, Book.class).title());

        // 6. Adnotacje: inna nazwa pola i pole pominięte
        System.out.println("6. " + mapper.writeValueAsString(new Reader("Ala Kot", "ala@example.com", "tajne")));
        // {"full_name":"Ala Kot","email":"ala@example.com"}  <- bez hasła

        // 7. Drzewo JsonNode: bez własnej klasy, gdy chcesz tylko jedno pole z dużego JSON
        JsonNode root = mapper.readTree(many);
        System.out.println("7. autor drugiej książki: " + root.get(1).get("author").asText());
    }
}
