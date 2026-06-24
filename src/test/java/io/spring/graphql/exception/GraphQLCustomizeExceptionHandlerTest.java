package io.spring.graphql.exception;

import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.execution.ResultPath;
import io.spring.api.exception.InvalidAuthenticationException;
import io.spring.graphql.types.Error;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GraphQLCustomizeExceptionHandlerTest {

  private final GraphQLCustomizeExceptionHandler handler = new GraphQLCustomizeExceptionHandler();

  @Test
  public void should_handle_invalid_authentication_as_unauthenticated_graphql_error() {
    DataFetcherExceptionHandlerParameters parameters =
        Mockito.mock(DataFetcherExceptionHandlerParameters.class);
    InvalidAuthenticationException exception = new InvalidAuthenticationException();

    Mockito.when(parameters.getException()).thenReturn(exception);
    Mockito.when(parameters.getPath())
        .thenReturn(ResultPath.fromList(List.of("mutation", "login")));

    CompletableFuture<DataFetcherExceptionHandlerResult> resultFuture =
        handler.handleException(parameters);

    DataFetcherExceptionHandlerResult result = resultFuture.join();
    Assertions.assertEquals(1, result.getErrors().size());

    GraphQLError graphQLError = result.getErrors().get(0);
    Assertions.assertEquals("invalid email or password", graphQLError.getMessage());
    Assertions.assertEquals(List.of("mutation", "login"), graphQLError.getPath());
    Assertions.assertEquals("UNAUTHENTICATED", graphQLError.getExtensions().get("errorType"));
  }

  @Test
  public void should_convert_constraint_violations_into_graphql_extensions_map() {
    DataFetcherExceptionHandlerParameters parameters =
        Mockito.mock(DataFetcherExceptionHandlerParameters.class);

    ConstraintViolationException constraintViolationException =
        new ConstraintViolationException("validation failed", createConstraintViolations());

    Mockito.when(parameters.getException()).thenReturn(constraintViolationException);
    Mockito.when(parameters.getPath())
        .thenReturn(ResultPath.fromList(List.of("mutation", "createUser")));

    DataFetcherExceptionHandlerResult result = handler.handleException(parameters).join();

    Assertions.assertEquals(1, result.getErrors().size());
    GraphQLError graphQLError = result.getErrors().get(0);

    Assertions.assertEquals("validation failed", graphQLError.getMessage());
    Assertions.assertTrue(graphQLError.getExtensions().containsKey("email"));
    Assertions.assertEquals(
        List.of("must not be blank"), graphQLError.getExtensions().get("email"));
  }

  @Test
  public void should_route_on_exception_through_the_same_handling_flow() {
    DataFetcherExceptionHandlerParameters parameters =
        Mockito.mock(DataFetcherExceptionHandlerParameters.class);
    InvalidAuthenticationException exception = new InvalidAuthenticationException();

    Mockito.when(parameters.getException()).thenReturn(exception);
    Mockito.when(parameters.getPath())
        .thenReturn(ResultPath.fromList(List.of("mutation", "login")));

    DataFetcherExceptionHandlerResult result = handler.onException(parameters);

    Assertions.assertEquals(1, result.getErrors().size());
    Assertions.assertEquals("invalid email or password", result.getErrors().get(0).getMessage());
  }

  @Test
  public void should_keep_error_data_format_for_constraint_violations() {
    ConstraintViolationException constraintViolationException =
        new ConstraintViolationException("validation failed", createConstraintViolations());

    Error error = GraphQLCustomizeExceptionHandler.getErrorsAsData(constraintViolationException);

    Assertions.assertEquals("BAD_REQUEST", error.getMessage());
    Assertions.assertEquals(1, error.getErrors().size());
    Assertions.assertEquals("email", error.getErrors().get(0).getKey());
    Assertions.assertEquals(List.of("must not be blank"), error.getErrors().get(0).getValue());
  }

  @Test
  public void should_keep_leaf_field_for_two_segment_property_path() {
    ConstraintViolationException constraintViolationException =
        new ConstraintViolationException("validation failed", createNestedConstraintViolations());

    Error error = GraphQLCustomizeExceptionHandler.getErrorsAsData(constraintViolationException);

    Assertions.assertEquals(1, error.getErrors().size());
    Assertions.assertEquals("email", error.getErrors().get(0).getKey());
    Assertions.assertEquals(List.of("must not be blank"), error.getErrors().get(0).getValue());
  }

  private Set<ConstraintViolation<ValidationInput>> createConstraintViolations() {
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
