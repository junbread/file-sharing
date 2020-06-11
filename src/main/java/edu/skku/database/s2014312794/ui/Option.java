package edu.skku.database.s2014312794.ui;

public class Option<T> implements Selectable {
    private String name;
    private String description;
    private T value;

    public Option() {
    }

    public Option(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Option(String name, String description, T value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    public T value() {
        return value;
    }

}
