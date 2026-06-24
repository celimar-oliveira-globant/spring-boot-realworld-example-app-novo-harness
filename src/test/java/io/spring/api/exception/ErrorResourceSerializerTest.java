package io.spring.api.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorResourceSerializerTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void should_preserve_first_field_occurrence_order_when_serializing_errors()
      throws Exception {
    ErrorResource errorResource =
        new ErrorResource(
            Arrays.asList(
                new FieldErrorResource("User", "username", "NotBlank", "must not be blank"),
                new FieldErrorResource("User", "email", "Email", "must be a well-formed email"),
                new FieldErrorResource(
                    "User", "username", "Size", "size must be between 3 and 20")));

    String jsonPayload = objectMapper.writeValueAsString(errorResource);
    JsonNode errorsNode = objectMapper.readTree(jsonPayload).get("errors");

    Iterator<String> fieldNames = errorsNode.fieldNames();
    Assertions.assertTrue(fieldNames.hasNext());
    Assertions.assertEquals("username", fieldNames.next());
    Assertions.assertTrue(fieldNames.hasNext());
    Assertions.assertEquals("email", fieldNames.next());
    Assertions.assertFalse(fieldNames.hasNext());

    List<String> usernameMessages =
        objectMapper.convertValue(
            errorsNode.get("username"),
            objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
    Assertions.assertEquals(
        Arrays.asList("must not be blank", "size must be between 3 and 20"), usernameMessages);
  }
}
