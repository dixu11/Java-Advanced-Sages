package pl.sages.jext.examples.ch2.annotations.model;

/** Klasa bez @Entity: skaner ma ją pominąć, choć ma @Index. */
@Index(name = "ignored", properties = "x")
public class NotAnEntity {
    String x;
}
