package com.cloudurable.jai.util;

import io.nats.jparse.Json;
import io.nats.jparse.node.ArrayNode;
import io.nats.jparse.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonSerializerTest {

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
        serializer.startList();
        serializer.addElement("b");
        serializer.addElement(11);
        serializer.endList();

        String json = serializer.toString();
        System.out.println(json);
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
        serializer.startList();
        serializer.addElement("b");
        serializer.addElement(11);
        serializer.startNestedObjectElement();
        serializer.addAttribute("a", "b1");
        serializer.addAttribute("c", 111);
        serializer.endObject();
        serializer.endList();
        String json = serializer.toString();
        System.out.println(json);
        ArrayNode arrayNode = Json.toArrayNode(json);
        assertEquals("b", arrayNode.getString(0));
        assertEquals(11, arrayNode.getInt(1));
        assertEquals(111, arrayNode.atPath("[2].c").asScalar().intValue());
    }

}
