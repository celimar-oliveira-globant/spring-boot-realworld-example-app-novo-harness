package io.spring.api.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ErrorResourceSerializer extends JsonSerializer<ErrorResource> {
  @Override
  public void serialize(ErrorResource value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException, JsonProcessingException {
    Map<String, List<String>> errorsByField = new LinkedHashMap<>();
    gen.writeStartObject();
    gen.writeObjectFieldStart("errors");
    for (FieldErrorResource fieldErrorResource : value.getFieldErrors()) {
      errorsByField.computeIfAbsent(fieldErrorResource.getField(), key -> new ArrayList<>());
      errorsByField.get(fieldErrorResource.getField()).add(fieldErrorResource.getMessage());
    }
    for (Map.Entry<String, List<String>> pair : errorsByField.entrySet()) {
      gen.writeArrayFieldStart(pair.getKey());
      for (String content : pair.getValue()) {
        gen.writeString(content);
      }
      gen.writeEndArray();
    }
    gen.writeEndObject();
    gen.writeEndObject();
  }
}
