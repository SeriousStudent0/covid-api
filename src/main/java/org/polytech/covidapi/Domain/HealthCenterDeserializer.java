package org.polytech.covidapi.Domain;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HealthCenterDeserializer extends JsonDeserializer<HealthCenter> {
    @Override
    public HealthCenter deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode node = mapper.readTree(jp);
        
        // Extract the fields from the JSON node and construct a HealthCenter object
        // Make sure to handle null values or missing fields appropriately

        Integer id = node.get("id").asInt();
        String name = node.get("name").asText();
        //Address address = node.get("address").asAddress();
        
        // Handle the address field in a similar way, extracting its properties
        
        HealthCenter healthCenter = new HealthCenter();
        //Address address = new Address():
        healthCenter.setId(id);
        healthCenter.setName(name);
        // Set other fields accordingly
        
        return healthCenter;
    }
}

