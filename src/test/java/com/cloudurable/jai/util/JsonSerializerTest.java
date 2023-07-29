package com.cloudurable.jai.util;

import io.nats.jparse.Json;
import io.nats.jparse.node.ArrayNode;
import io.nats.jparse.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonSerializerTest {

    @Test
    void writeObjectEncode() {
        JsonSerializer serializer = new JsonSerializer();
        serializer.startObject();
        serializer.addAttribute("a", "b");
        serializer.addAttribute("c", 11);
        serializer.endObject();
        String json = serializer.toString();
        ObjectNode objectNode = Json.toObjectNode(json);
        assertEquals("b", objectNode.getString("a"));
        assertEquals(11, objectNode.getInt("c"));
    }
    @Test
    void writeObject() {
        JsonSerializer serializer = new JsonSerializer();
        serializer.startObject();
        serializer.addAttribute("a", "b");
        serializer.addAttribute("c", 11);
        serializer.endObject();
        String json = serializer.toString();
        ObjectNode objectNode = Json.toObjectNode(json);
        assertEquals("b", objectNode.getString("a"));
        assertEquals(11, objectNode.getInt("c"));
    }

    @Test
    void writeList() {
        JsonSerializer serializer = new JsonSerializer();
        serializer.startArray();
        serializer.addElement("b");
        serializer.addElement(11);
        serializer.endArray();

        String json = serializer.toString();
        ArrayNode arrayNode = Json.toArrayNode(json);
        assertEquals("b", arrayNode.getString(0));
        assertEquals(11, arrayNode.getInt(1));
    }

    @Test
    void writeNestedObject() {
        JsonSerializer serializer = new JsonSerializer();
        serializer.startObject();
        serializer.addAttribute("a", "b");
        serializer.addAttribute("c", 11);
        serializer.startNestedObjectAttribute("b");
        serializer.addAttribute("a", "b1");
        serializer.addAttribute("c", 111);
        serializer.endObject();
        serializer.endObject();
        String json = serializer.toString();
        ObjectNode objectNode = Json.toObjectNode(json);
        assertEquals("b", objectNode.getString("a"));
        assertEquals(11, objectNode.getInt("c"));
        assertEquals(111, objectNode.atPath("b.c").asScalar().intValue());


    }


    @Test
    void writeListWithNestedObject() {
        JsonSerializer serializer = new JsonSerializer();
        serializer.startArray();
        serializer.addElement("b");
        serializer.addElement(11);
        serializer.startNestedObjectElement();
        serializer.addAttribute("a", "b1");
        serializer.addAttribute("c", 111);
        serializer.endObject();
        serializer.endArray();
        String json = serializer.toString();
        ArrayNode arrayNode = Json.toArrayNode(json);
        assertEquals("b", arrayNode.getString(0));
        assertEquals(11, arrayNode.getInt(1));
        assertEquals(111, arrayNode.atPath("[2].c").asScalar().intValue());
    }


    @Test
    void writeNestedList() {
        JsonSerializer serializer = new JsonSerializer();
        serializer.startObject();
        serializer.addAttribute("a", "b");
        serializer.addAttribute("c", 11);
        serializer.startNestedArrayAttribute("b");
        serializer.addElement("b1");
        serializer.addElement(111);
        serializer.startNestedArrayElement();
        serializer.addElement("abcd");
        serializer.endArray();
        serializer.endArray();
        serializer.endObject();
        String json = serializer.toString();
        ObjectNode objectNode = Json.toObjectNode(json);
        assertEquals("b", objectNode.getString("a"));
        assertEquals(11, objectNode.getInt("c"));
        assertEquals("b1", objectNode.atPath("b[0]").asScalar().stringValue());
        assertEquals(111, objectNode.atPath("b[1]").asScalar().intValue());
        assertEquals("abcd", objectNode.atPath("b[2][0]").asScalar().stringValue());


    }

    @Test
    void encode() {
        StringBuilder builder = JsonSerializer.encodeString("Hi mom \n how are you \\ \t \b \r \"'good'\" ");
        assertEquals("Hi mom \\n how are you \\\\ \\t \\b \\r \\\"'good'\\\" ", builder.toString());
        System.out.println(builder);
    }


}
