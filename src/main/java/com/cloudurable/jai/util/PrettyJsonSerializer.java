package com.cloudurable.jai.util;


import java.util.Stack;

public class PrettyJsonSerializer implements JsonBuilder {
    private final StringBuilder builder = new StringBuilder();
    private final Stack<Integer> stack = new Stack<>();
    private final int indentationSize = 4;
    private int currentIndentation = 0;

    private void indent() {
        for (int i = 0; i < currentIndentation; i++) {
            builder.append(' ');
        }
    }

    @Override
    public void startObject() {
        stack.push(0);
        builder.append("{\n");
        currentIndentation += indentationSize;
    }

    @Override
    public void endObject() {
        stack.pop();
        builder.append("\n");
        currentIndentation -= indentationSize;
        indent();
        builder.append("}");
    }

    @Override
    public void startArray() {
        stack.push(0);
        builder.append("[\n");
        currentIndentation += indentationSize;
    }

    @Override
    public void endArray() {
        stack.pop();
        builder.append("\n");
        currentIndentation -= indentationSize;
        indent();
        builder.append("]");
    }

    @Override
    public void startNestedObjectAttribute(String name) {
        trackAndAddCommaIfNeeded();
        indent();
        addAttributeName(name);
        builder.append(": {\n");
        currentIndentation += indentationSize;
        stack.push(0);
    }

    @Override
    public void startNestedArrayAttribute(String name) {
        trackAndAddCommaIfNeeded();
        indent();
        addAttributeName(name);
        builder.append(": [\n");
        currentIndentation += indentationSize;
        stack.push(0);
    }

    @Override
    public void startNestedArrayElement() {
        trackAndAddCommaIfNeeded();
        indent();
        builder.append("[\n");
        currentIndentation += indentationSize;
        stack.push(0);
    }

    @Override
    public void startNestedObjectElement() {
        trackAndAddCommaIfNeeded();
        indent();
        builder.append("{\n");
        currentIndentation += indentationSize;
        stack.push(0);
    }

    @Override
    public void addElement(Number value) {
        trackAndAddCommaIfNeeded();
        indent();
        builder.append(value).append("\n");
    }

    @Override
    public void addElement(String value) {
        trackAndAddCommaIfNeeded();
        indent();
        if (value == null) {
            builder.append("null\n");
        } else {
            builder.append('"').append(JsonSerializer.encodeString(value)).append("\"\n");
        }
    }

    @Override
    public void addAttribute(String name, Number value) {
        trackAndAddCommaIfNeeded();
        indent();
        addAttributeName(name);
        builder.append(": ").append(value).append("\n");
    }

    @Override
    public void addAttribute(String name, boolean value) {
        trackAndAddCommaIfNeeded();
        indent();
        addAttributeName(name);
        builder.append(": ").append(value).append("\n");
    }

    @Override
    public void addAttribute(String name, String value) {
        trackAndAddCommaIfNeeded();
        indent();
        addAttributeName(name);
        if (value == null) {
            builder.append(": null\n");
        } else {
            builder.append(": \"").append(JsonSerializer.encodeString(value)).append("\"\n");
        }
    }

    private void trackAndAddCommaIfNeeded() {
        if (!stack.isEmpty()) {
            int count = stack.pop();
            if (count > 0) {
                builder.append(",\n");
            }
            stack.push(count + 1);
        }
    }

    private void addAttributeName(String name) {
        builder.append('"').append(name).append('"');
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
