package pl.sages.jext.examples.ch2.annotations;

import org.jboss.jandex.AnnotationTarget;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.IndexReader;
import org.jboss.jandex.IndexView;
import pl.sages.jext.examples.ch2.annotations.route.AdminPage;
import pl.sages.jext.examples.ch2.annotations.route.Helper;
import pl.sages.jext.examples.ch2.annotations.route.HomePage;

import java.io.IOException;
import java.io.InputStream;

/**
 * Slajd 63: odczyt indeksu Jandex i wyszukanie klas z adnotacją {@code @Route} bez ładowania klas.
 * <p>
 * Indeks {@code META-INF/jandex.idx} buduje {@code jandex-maven-plugin} przy {@code mvn compile}
 * (patrz {@code pom.xml}). Gdy go nie ma (np. budowanie wbudowanym kompilatorem IntelliJ), przykład
 * buduje indeks w kodzie przez {@code Index.of(...)} i mówi o tym.
 * <p>
 * Klasa {@code App} ze slajdu to tutaj {@code JandexRoute}; {@code com.xyz.Route} to {@code ...route.Route}.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.annotations.JandexRoute}
 */
public class JandexRoute {

    public static void main(String[] args) throws IOException {
        InputStream resource = JandexRoute.class.getResourceAsStream("/META-INF/jandex.idx");
        IndexView index;
        if (resource != null) {
            // --- kod ze slajdu ---
            try (var input = resource) {
                index = new IndexReader(input).read();
            }
            System.out.println("Indeks z META-INF/jandex.idx (zbudowany przez plugin)");
        } else {
            index = Index.of(HomePage.class, AdminPage.class, Helper.class);
            System.out.println("Brak META-INF/jandex.idx: indeks zbudowany w kodzie (Index.of)");
        }
        DotName route =
                DotName.createSimple("pl.sages.jext.examples.ch2.annotations.route.Route");
        for (var annotation :
                index.getAnnotations(route)) {
            if (annotation.target().kind()
                    == AnnotationTarget.Kind.CLASS) {
                System.out.println(
                        annotation.target().asClass().name() + " -> " + annotation.value().asString());
            }
        }
    }
}
