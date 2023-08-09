package com.cloudurable.jai.util;

import java.util.Stack;

/**
 * A utility class for serializing Java objects to JSON.
 * The JsonSerializer class is a utility class that provides methods to serialize objects into JSON format. It allows you to build JSON strings by sequentially adding elements, attributes, and nested objects or lists.
 * <p>
 * Here's a detailed description of what the class does:
 * <p>
 * The class has a Stack called stack and a StringBuilder called builder as instance variables.
 * <p>
 * The constructor initializes the builder as a new StringBuilder.
 * <p>
 * The class provides methods to start and end the serialization of objects and lists. The startObject() method starts the serialization of an object by pushing a 0 onto the stack and appending a '{' character to the builder. The endObject() method pops the top element from the stack and appends a '}' character to the builder.
 * <p>
 * Similarly, the startArray() and endArray() methods perform similar operations for lists.
 * <p>
 * There are methods to start the serialization of nested object attributes and nested list attributes. These methods take a name parameter representing the attribute name. The methods append the attribute name, ':', and either '{' or '[' characters to the builder, push 0 onto the stack, and update the serialization state.
 * <p>
 * The class also provides methods to start the serialization of nested list elements and nested object elements. These methods are similar to the attribute methods but don't require a name parameter.
 * <p>
 * There are methods to add number and string elements to the serialization. The addElement(Number number) method appends the number value to the builder, while the addElement(String value) method appends the string value, enclosed in double quotes, to the builder. If the string value is null, it appends the string "null" without quotes.
 * <p>
 * The class provides methods to add number and string attributes to the serialization. The addAttribute(String name, Number number) method appends the attribute name, ':', and the number value to the builder. The addAttribute(Number key, Number number) method is similar but takes a numeric key instead of a name. The addAttribute(String name, String value) method appends the attribute name, ':', and the string value (enclosed in double quotes) to the builder. If the string value is null, it appends the string "null" without quotes.
 * <p>
 * The private method trackAndAddCommaIfNeeded() is used internally to track the serialization state and add a comma before adding a new element or attribute. It pops the top element from the stack, checks if the count is greater than zero, and if so, appends a ',' character to the builder. Then it increments the count, pushes it back to the stack, and updates the serialization state.
 * <p>
 * The private method addAttributeName(String name) is used internally to append an attribute name, enclosed in double quotes, to the builder.
 * <p>
 * The toString() method overrides the toString() method of the Object class and returns the serialized JSON string.
 * <p>
 * In summary, the JsonSerializer class provides a convenient way to build JSON strings by sequentially adding elements, attributes, and nested objects or lists. It maintains the serialization state using a stack and a StringBuilder for efficient string concatenation.
 */
public class JsonSerializer implements JsonBuilder{

    /**
     * The StringBuilder used to build the JSON string.
     */
    private final StringBuilder builder;
    /**
     * The stack used to track the nesting level of objects and lists.
     */
    private Stack<Integer> stack;

    /**
     * Constructs a new JsonSerializer.
     */
    public JsonSerializer() {
        builder = new StringBuilder();
        stack = new Stack<>();
    }

    /**
     * Starts a new JSON object.
     */
    public void startObject() {
        stack.push(0);
        builder.append('{');
    }

    /**
     * Ends the current JSON object.
     */
    public void endObject() {
        stack.pop();
        builder.append('}');
    }

    /**
     * Starts a new JSON list.
     */
    public void startArray() {
        stack.push(0);
        builder.append('[');
    }

    /**
     * Ends the current JSON list.
     */
    public void endArray() {
        stack.pop();
        builder.append(']');
    }

    /**
     * Starts a new nested JSON object attribute.
     *
     * @param name The name of the attribute.
     */
    public void startNestedObjectAttribute(String name) {
        trackAndAddCommaIfNeeded();
        addAttributeName(name);
        builder.append(':');
        stack.push(0);
        builder.append('{');
    }

    /**
     * Starts a new nested JSON list attribute.
     *
     * @param name The name of the attribute.
     */
    public void startNestedArrayAttribute(String name) {
        trackAndAddCommaIfNeeded();
        addAttributeName(name);
        builder.append(':');
        stack.push(0);
        builder.append('[');
    }

    /**
     * Starts a new nested JSON list element.
     */
    public void startNestedArrayElement() {
        trackAndAddCommaIfNeeded();
        stack.push(0);
        builder.append('[');
    }

    /**
     * Starts a new nested JSON object element.
     */
    public void startNestedObjectElement() {
        trackAndAddCommaIfNeeded();
        stack.push(0);
        builder.append('{');
    }

    /**
     * Adds a JSON element to the current object or list.
     *
     * @param value The value of the element.
     */
    public void addElement(Number value) {
        trackAndAddCommaIfNeeded();
        builder.append(value);
    }

    /**
     * Adds a JSON element to the current object or list.
     *
     * @param value The value of the element.
     */
    public void addElement(String value) {
        trackAndAddCommaIfNeeded();
        if (value == null) {
            builder.append("null");
        } else {
            StringBuilder strBuilder = encodeString(value);
            builder.append('"').append(strBuilder).append('"');
        }
    }


    /**
     * Adds a JSON attribute to the current object.
     *
     * @param name  The name of the attribute.
     * @param value The value of the attribute.
     */
    public void addAttribute(String name, Number value) {
        trackAndAddCommaIfNeeded();
        addAttributeName(name);
        builder.append(':').append(value);
    }

    /**
     * Adds a JSON attribute to the current object.
     *
     * @param name  The name of the attribute.
     * @param value The value of the attribute.
     */
    public void addAttribute(String name, boolean value) {
        trackAndAddCommaIfNeeded();
        addAttributeName(name);
        builder.append(':').append(value);
    }


    //TODO FIX
//    /**
//     * Adds a JSON attribute to the current object.
//     *
//     * @param key   The key of the attribute.
//     * @param value The value of the attribute.
//     */
//    public void addAttribute(Number key, Number value) {
//        trackAndAddCommaIfNeeded();
//        builder.append(key).append(':').append(value);
//    }

    /**
     * Adds a JSON attribute to the current object.
     *
     * @param name  The name of the attribute.
     * @param value The value of the attribute.
     */

    /**
     * Adds a JSON attribute to the current object.
     *
     * @param name  The name of the attribute.
     * @param value The value of the attribute.
     */
    public void addAttribute(String name, String value) {
        trackAndAddCommaIfNeeded();
        addAttributeName(name);
        if (value == null) {
            builder.append(':').append("null");
        } else {
            StringBuilder strBuilder = encodeString(value);
            builder.append(":\"").append(strBuilder).append('"');
        }
    }

    public static StringBuilder encodeString(String value) {
        StringBuilder strBuilder = new StringBuilder(value.length());
        char[] charArray = value.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];

            switch (ch) {
                case '"':
                    strBuilder.append("\\\"");
                    break;
                case '\n':
                    strBuilder.append("\\n");
                    break;
                case '\r':
                    strBuilder.append("\\r");
                    break;
                case '\t':
                    strBuilder.append("\\t");
                    break;
                case '\b':
                    strBuilder.append("\\b");
                    break;
                case '\\':
                    strBuilder.append("\\\\");
                    break;
                case '/':
                    strBuilder.append("\\/");
                    break;
                default:
                    strBuilder.append(ch);
            }

        }
        return strBuilder;
    }

    /**
     * Adds a JSON attribute to the current object.
     *
     * @param name  The name of the attribute.
     * @param value The value of the attribute.
     */
    public void addAttributeIf(String name, String value) {
        if (value != null && !value.isBlank()) {
            trackAndAddCommaIfNeeded();
            addAttributeName(name);
            builder.append(":\"").append(value).append('"');
        }
    }

    /**
     * Adds a JSON attribute to the current object.
     *
     * @param name  The name of the attribute.
     * @param value The value of the attribute.
     */
    public void addAttributeIf(String name, Number value) {
        if (value != null) {
            trackAndAddCommaIfNeeded();
            addAttributeName(name);
            builder.append(":\"").append(value).append('"');
        }
    }

    /**
     * Tracks the nesting level of objects and lists, and adds a comma if needed.
     */
    private void trackAndAddCommaIfNeeded() {
        int count = stack.pop();
        if (count > 0) {
            builder.append(',');
        }
        count++;
        stack.push(count);
    }

    /**
     * Adds the attribute name to the JSON string.
     *
     * @param name The name of the attribute.
     */
    private void addAttributeName(String name) {
        builder.append('"').append(name).append('"');
    }

    /**
     * Returns the JSON string representation of the object.
     *
     * @return The JSON string representation of the object.
     */
    @Override
    public String toString() {
        return builder.toString();
    }
}
