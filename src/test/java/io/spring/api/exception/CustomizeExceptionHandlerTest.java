package io.spring.api.exception;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.context.request.WebRequest;

public class CustomizeExceptionHandlerTest {

  private final CustomizeExceptionHandler handler = new CustomizeExceptionHandler();

  @Test
  public void should_keep_leaf_field_for_two_segment_property_path() {
    ConstraintViolationException constraintViolationException =
        new ConstraintViolationException("validation failed", createNestedConstraintViolations());

    ErrorResource errorResource =
        handler.handleConstraintViolation(
            constraintViolationException, Mockito.mock(WebRequest.class));

    Assertions.assertEquals(1, errorResource.getFieldErrors().size());
    Assertions.assertEquals("email", errorResource.getFieldErrors().get(0).getField());
    Assertions.assertEquals(
        "must not be blank", errorResource.getFieldErrors().get(0).getMessage());
  }

  @Test
  public void should_preserve_single_segment_property_path() {
    ConstraintViolationException constraintViolationException =
        new ConstraintViolationException(
            "validation failed", createSingleLevelConstraintViolations());

    ErrorResource errorResource =
        handler.handleConstraintViolation(
            constraintViolationException, Mockito.mock(WebRequest.class));

    Assertions.assertEquals(1, errorResource.getFieldErrors().size());
    Assertions.assertEquals("email", errorResource.getFieldErrors().get(0).getField());
    Assertions.assertEquals(
        "must not be blank", errorResource.getFieldErrors().get(0).getMessage());
  }

  private Set<ConstraintViolation<ValidationInput>> createSingleLevelConstraintViolations() {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    ValidationInput validationInput = new ValidationInput("");
    return validator.validate(validationInput);
  }

  private Set<ConstraintViolation<NestedValidationInput>> createNestedConstraintViolations() {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    NestedValidationInput nestedValidationInput =
        new NestedValidationInput(new ValidationInput(""));
    return validator.validate(nestedValidationInput);
  }

  private static class ValidationInput {
    @NotBlank private final String email;

    private ValidationInput(String email) {
      this.email = email;
    }
  }

  private static class NestedValidationInput {
    @Valid private final ValidationInput input;

    private NestedValidationInput(ValidationInput input) {
      this.input = input;
    }
  }
}
