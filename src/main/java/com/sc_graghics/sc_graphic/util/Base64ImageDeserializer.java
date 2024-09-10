package com.sc_graghics.sc_graphic.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Base64;

/**
 * @author : Chanuka Weerakkody
 * @since : 20.1.1
 **/

public class Base64ImageDeserializer extends JsonDeserializer<byte[]> {
    @Override
    public byte[] deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String imageData = p.getText();

        if (imageData != null && imageData.startsWith("data:image")) {
            // Strip the base64 image prefix and decode the image
            String base64Image = imageData.split(",", 2)[1];
            return Base64.getDecoder().decode(base64Image);
        } else {
            // Handle case where image data is already in byte array format or invalid
            return Base64.getDecoder().decode(imageData); // Adjust this line based on your requirements
        }
    }
}
