package io.spring.graphql.exception;

import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.ErrorType;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import io.spring.api.exception.FieldErrorResource;
import io.spring.api.exception.InvalidAuthenticationException;
import io.spring.graphql.types.Error;
import io.spring.graphql.types.ErrorItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

@Component
public class GraphQLCustomizeExceptionHandler implements DataFetcherExceptionHandler {

  private final DefaultDataFetcherExceptionHandler defaultHandler =
      new DefaultDataFetcherExceptionHandler();

  // graphql-java 17.x requires this legacy override even with modern handleException flow.
  @SuppressWarnings("deprecation")
  @Override
  public DataFetcherExceptionHandlerResult onException(
      DataFetcherExceptionHandlerParameters handlerParameters) {
    return handleException(handlerParameters).join();
  }

  @Override
  public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(
      DataFetcherExceptionHandlerParameters handlerParameters) {
    Throwable exception = handlerParameters.getException();

    if (exception instanceof InvalidAuthenticationException) {
      return CompletableFuture.completedFuture(
          buildUnauthenticatedResult(handlerParameters, exception));
    }

    if (exception instanceof ConstraintViolationException) {
      ConstraintViolationException constraintViolationException =
          (ConstraintViolationException) exception;
      return CompletableFuture.completedFuture(
          buildConstraintViolationResult(handlerParameters, constraintViolationException));
    }

    return defaultHandler.handleException(handlerParameters);
  }

  public static Error getErrorsAsData(ConstraintViolationException constraintViolationException) {
    List<FieldErrorResource> errors =
        collectFieldErrors(constraintViolationException.getConstraintViolations());

    Map<String, List<String>> errorMap = new HashMap<>();
    for (FieldErrorResource fieldErrorResource : errors) {
      errorMap.computeIfAbsent(fieldErrorResource.getField(), key -> new ArrayList<>());
      errorMap.get(fieldErrorResource.getField()).add(fieldErrorResource.getMessage());
    }

    List<ErrorItem> errorItems =
        errorMap.entrySet().stream()
            .map(kv -> ErrorItem.newBuilder().key(kv.getKey()).value(kv.getValue()).build())
            .collect(Collectors.toList());
    return Error.newBuilder().message("BAD_REQUEST").errors(errorItems).build();
  }

  private DataFetcherExceptionHandlerResult buildUnauthenticatedResult(
      DataFetcherExceptionHandlerParameters handlerParameters, Throwable exception) {
    GraphQLError graphqlError =
        TypedGraphQLError.newBuilder()
            .errorType(ErrorType.UNAUTHENTICATED)
            .message(exception.getMessage())
            .path(handlerParameters.getPath())
            .build();
    return DataFetcherExceptionHandlerResult.newResult().error(graphqlError).build();
  }

  private DataFetcherExceptionHandlerResult buildConstraintViolationResult(
      DataFetcherExceptionHandlerParameters handlerParameters,
      ConstraintViolationException constraintViolationException) {
    List<FieldErrorResource> errors =
        collectFieldErrors(constraintViolationException.getConstraintViolations());

    GraphQLError graphqlError =
        TypedGraphQLError.newBadRequestBuilder()
            .message(constraintViolationException.getMessage())
            .path(handlerParameters.getPath())
            .extensions(errorsToMap(errors))
            .build();
    return DataFetcherExceptionHandlerResult.newResult().error(graphqlError).build();
  }

  private static List<FieldErrorResource> collectFieldErrors(
      Set<? extends ConstraintViolation<?>> violations) {
    List<FieldErrorResource> errors = new ArrayList<>();
    for (ConstraintViolation<?> violation : violations) {
      FieldErrorResource fieldErrorResource =
          new FieldErrorResource(
              violation.getRootBeanClass().getName(),
              getParam(violation.getPropertyPath().toString()),
              violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
              violation.getMessage());
      errors.add(fieldErrorResource);
    }
    return errors;
  }

  private static String getParam(String pathValue) {
    String[] segments = pathValue.split("\\.");
    if (segments.length <= 2) {
      return segments[segments.length - 1];
    }
    return String.join(".", Arrays.copyOfRange(segments, 2, segments.length));
  }

  private static Map<String, Object> errorsToMap(List<FieldErrorResource> errors) {
    Map<String, List<String>> errorsByField = new HashMap<>();
    for (FieldErrorResource fieldErrorResource : errors) {
      errorsByField.computeIfAbsent(fieldErrorResource.getField(), key -> new ArrayList<>());
      errorsByField.get(fieldErrorResource.getField()).add(fieldErrorResource.getMessage());
    }

    Map<String, Object> json = new HashMap<>();
    errorsByField.forEach(json::put);
    return json;
  }
}
