package com.cloudurable.jai.util;

public interface JsonBuilder {
    void startObject();
    void endObject();
    void startArray();
    void endArray();
    void startNestedObjectAttribute(String name);
    void startNestedArrayAttribute(String name);
    void startNestedArrayElement();
    void startNestedObjectElement();
    void addElement(Number value);
    void addElement(String value);
    void addAttribute(String name, Number value);
    void addAttribute(String name, boolean value);
    void addAttribute(String name, String value);
    String toString();
}
