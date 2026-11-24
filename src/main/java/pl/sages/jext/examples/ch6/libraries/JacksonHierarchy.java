package pl.sages.jext.examples.ch6.libraries;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

/**
 * Slajdy 167-168: Jackson NAME z istniejącym polem oraz DEDUCTION po zestawie nazw pól.
 * Jackson 2.23.0 z pom wymaga rejestracji podtypów; sam permits nie wystarcza.
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch6.libraries.JacksonHierarchy}
 */
public class JacksonHierarchy {
    static class ByName {
        // Tekst musi pasować do nazwy w enumie ComponentType,
        // bo enum zapisuje się w JSON swoją nazwą.
        static final String CONTENT_COMPONENT_NAME = "CONTENT";
        static final String FAQ_COMPONENT_NAME = "FAQ";
        enum ComponentType { CONTENT, FAQ }
        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "componentType")
        @JsonSubTypes({ // typy i nazwy wykorzystywane w JSON
            @JsonSubTypes.Type(value = ContentComponentConfiguration.class, name = CONTENT_COMPONENT_NAME),
            @JsonSubTypes.Type(value = FAQComponentConfiguration.class, name = FAQ_COMPONENT_NAME)
        })
        sealed interface ComponentConfiguration
            permits ContentComponentConfiguration, FAQComponentConfiguration {
            @JsonGetter ComponentType componentType();
        }
        record ContentComponentConfiguration(String content) implements ComponentConfiguration {
            @Override public ComponentType componentType() { return ComponentType.CONTENT; }
        }
        record FAQComponentConfiguration(List<String> faqList) implements ComponentConfiguration {
            @Override public ComponentType componentType() { return ComponentType.FAQ; }
        }
    }
    static class ByDeduction {
        @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
        @JsonSubTypes({ // Jackson 2.23.0 nie odczytuje podtypów z permits
            @JsonSubTypes.Type(ContentComponentConfiguration.class),
            @JsonSubTypes.Type(URLComponentConfiguration.class),
            @JsonSubTypes.Type(FAQComponentConfiguration.class)
        })
        sealed interface ComponentConfiguration permits ContentComponentConfiguration,
            URLComponentConfiguration, FAQComponentConfiguration {}
        record ContentComponentConfiguration(String content) implements ComponentConfiguration {}
        record URLComponentConfiguration(String url) implements ComponentConfiguration {}
        record FAQComponentConfiguration(List<String> faqList) implements ComponentConfiguration {}
        // {"content":"Witaj"} -> ContentComponentConfiguration
        // {"url":"https://sages.pl"} -> URLComponentConfiguration
        // {"faqList":["Q1"]} -> FAQComponentConfiguration
    }

    public static void main(String[] args) throws Exception {
        var mapper = new ObjectMapper();
        List<ByName.ComponentConfiguration> configs = List.of(
                new ByName.ContentComponentConfiguration("Witaj"),
                new ByName.FAQComponentConfiguration(List.of("Q1", "Q2")));
        for (var c : configs) {
            String json = mapper.writeValueAsString(c);
            var back = mapper.readValue(json, ByName.ComponentConfiguration.class);
            System.out.println("167 NAME:      " + json + " -> " + back);
        }
        for (String json : List.of("{\"content\":\"Witaj\"}", "{\"url\":\"https://sages.pl\"}", "{\"faqList\":[\"Q1\"]}")) {
            var back = mapper.readValue(json, ByDeduction.ComponentConfiguration.class);
            System.out.println("168 DEDUCTION: " + json + " -> " + back);
        }
        try {
            mapper.readValue("{}", ByDeduction.ComponentConfiguration.class);
            throw new AssertionError("Pusty JSON pasuje do trzech typów");
        } catch (com.fasterxml.jackson.databind.exc.InvalidTypeIdException e) {
            System.out.println("168 DEDUCTION: {} -> " + e.getClass().getSimpleName() + " (trzy pasujące typy)");
        }
    }
}
