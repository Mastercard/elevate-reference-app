package com.mastercard.developers.mbep.exception;

import com.jayway.jsonpath.JsonPath;

import com.mastercard.developers.mbep.generated.invokers.ApiException;
import com.mastercard.developers.mbep.generated.models.Error;
import com.mastercard.developers.mbep.generated.models.ErrorWrapper;
import com.mastercard.developers.mbep.generated.models.Errors;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler implements ResponseErrorHandler {


    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ErrorWrapper> handleResourceNotFoundExceptions(Exception ex) {
        int httpErrorCode = ((ApiException) ex).getCode();
        String responseBody = ((ApiException) ex).getResponseBody();
        log.error("Error occurred : {}",  responseBody);
        Object error = JsonPath.parse(responseBody).read("$.Errors.Error");
        Error er= new Error();
        er.setDescription((String) ((LinkedHashMap) ((JSONArray) error).get(0)).get("Description"));
        er.setRecoverable((Boolean) ((LinkedHashMap) ((JSONArray) error).get(0)).get("Recoverable"));
        er.setDetails((String) ((LinkedHashMap) ((JSONArray) error).get(0)).get("Details"));
        er.setReasonCode((String) ((LinkedHashMap) ((JSONArray) error).get(0)).get("ReasonCode"));
        er.setSource((String) ((LinkedHashMap) ((JSONArray) error).get(0)).get("Source"));
        return getErrorWrapperResponseEntity(er, HttpStatus.valueOf(httpErrorCode));
    }

    /**
     * @param error      Is a type of Error Object.
     * @param httpStatus Http status like Server Side Error ,Bed Request.
     * @return Response Entity.
     */

    private ResponseEntity<ErrorWrapper> getErrorWrapperResponseEntity(Error error, HttpStatus httpStatus) {
        Errors errors = new Errors();
        errors.addErrorItem(error);
        ErrorWrapper errorWrapper = new ErrorWrapper();
        errorWrapper.setErrors(errors);
        return new ResponseEntity<>(errorWrapper, httpStatus);
    }

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return (
                clientHttpResponse.getStatusCode().series() == CLIENT_ERROR
                        || clientHttpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Nullable
    protected Charset getCharset(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        MediaType contentType = headers.getContentType();
        return (contentType != null ? contentType.getCharset() : null);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        switch (statusCode.series()) {
            case CLIENT_ERROR:
                throw new HttpClientErrorException(statusCode, response.getStatusText(),
                        response.getHeaders(), response.getBody().readAllBytes(), getCharset(response));
            case SERVER_ERROR:
                throw new HttpServerErrorException(statusCode, response.getStatusText(),
                        response.getHeaders(), response.getBody().readAllBytes(), getCharset(response));
            default:
                throw new RestClientException("Unknown status code [" + statusCode + "]");
        }
    }


}