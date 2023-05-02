package com.mastercard.developers.elevate.accelerator.exception;

import com.mastercard.developers.elevate.accelerator.generated.invokers.ApiException;
import com.mastercard.developers.elevate.accelerator.generated.models.ErrorWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomExceptionHandlerTest {

    @InjectMocks
    CustomExceptionHandler customExceptionHandler;


    @Test()
    void test_hasError() throws IOException {
        ClientHttpResponse mockClientHttpResponse = Mockito.mock(ClientHttpResponse.class);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        when(mockClientHttpResponse.getStatusCode()).thenReturn(status);
        boolean errorWrapperResponseEntity = customExceptionHandler.hasError(mockClientHttpResponse);
        assertTrue(errorWrapperResponseEntity);
    }

    @Test()
    void test_hasError_Internal_server_error() throws IOException {
        ClientHttpResponse mockClientHttpResponse = Mockito.mock(ClientHttpResponse.class);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        when(mockClientHttpResponse.getStatusCode()).thenReturn(status);
        boolean errorWrapperResponseEntity = customExceptionHandler.hasError(mockClientHttpResponse);
        assertTrue(errorWrapperResponseEntity);
    }

    @Test()
    void test_hasError_server() throws IOException {
        ClientHttpResponse mockClientHttpResponse = Mockito.mock(ClientHttpResponse.class);
        HttpStatus status = HttpStatus.CREATED;
        when(mockClientHttpResponse.getStatusCode()).thenReturn(status);
        boolean errorWrapperResponseEntity = customExceptionHandler.hasError(mockClientHttpResponse);
        assertFalse(errorWrapperResponseEntity);
    }


    @Test()
    void test_hasErrorServer() throws IOException {
        ClientHttpResponse mockClientHttpResponse = Mockito.mock(ClientHttpResponse.class);
        when(mockClientHttpResponse.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        boolean errorWrapperResponseEntity = customExceptionHandler.hasError(mockClientHttpResponse);
        assertTrue(errorWrapperResponseEntity);
    }

    @Test()
    void test_handleError_client() {
        InputStream body = Mockito.mock(InputStream.class);
        MockClientHttpResponse clientHttpResponse = new MockClientHttpResponse(body, HttpStatus.BAD_REQUEST);
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> customExceptionHandler.handleError(clientHttpResponse));
        assertNotNull(exception);
    }

    @Test()
    void test_handleError_Server() {
        InputStream body = Mockito.mock(InputStream.class);
        MockClientHttpResponse clientHttpResponse = new MockClientHttpResponse(body, HttpStatus.BAD_GATEWAY);
        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class, () -> customExceptionHandler.handleError(clientHttpResponse));
        assertNotNull(exception);
    }

    @Test()
    void test_handleError_info() {
        InputStream body = Mockito.mock(InputStream.class);
        MockClientHttpResponse clientHttpResponse = new MockClientHttpResponse(body, HttpStatus.CREATED);
        RestClientException exception = assertThrows(RestClientException.class, () -> customExceptionHandler.handleError(clientHttpResponse));
        assertNotNull(exception);
    }


    @Test()
    void test_handleAllExceptions() {
        ApiException apiException = Mockito.mock(ApiException.class);
        String errorMessage = "{\"Errors\": {\"Error\": [{\"Source\": \"Elevate API\",\"ReasonCode\": \"402\",\"Description\": \"API MISSING PARTNER ID\",\"Recoverable\": true,\"Details\": \"\"}]}}";
        when(apiException.getResponseBody()).thenReturn(errorMessage);
        when(apiException.getCode()).thenReturn(402);
        ResponseEntity<ErrorWrapper> errorWrapperResponseEntity = customExceptionHandler.handleResourceNotFoundExceptions(apiException);
        assertNotNull(errorWrapperResponseEntity);
        assertEquals(402, errorWrapperResponseEntity.getStatusCodeValue());
    }

    @Test
    void test_charset(){
        ClientHttpResponse response = new MockClientHttpResponse(new byte[10], 200);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        Charset charset = customExceptionHandler.getCharset(response);
        assertNull(charset);
    }
}