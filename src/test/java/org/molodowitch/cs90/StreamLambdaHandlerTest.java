package org.molodowitch.cs90;


import com.amazonaws.serverless.proxy.internal.LambdaContainerHandler;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.molodowitch.cs90.todos.StreamLambdaHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

public class StreamLambdaHandlerTest {

    private static StreamLambdaHandler handler;
    private static Context lambdaContext;

    @BeforeAll
    public static void setUp() {
        handler = new StreamLambdaHandler();
        lambdaContext = new MockLambdaContext();
    }

    @Test
    public void ping_streamRequest_respondsWithHello() {
        InputStream requestStream = new AwsProxyRequestBuilder("/ping", HttpMethod.GET)
                                            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                            .buildStream();
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

        handle(requestStream, responseStream);

        AwsProxyResponse response = readResponse(responseStream);
        assertThat(response, is(notNullValue()));
        checkOkResponse(response);
        checkJsonResponseType(response);

        assertThat(response.getBody(), containsString("pong"));
        assertThat(response.getBody(), containsString("Hello, World!"));
    }

    @Test
    public void getSingleList_exists() {
        InputStream requestStream = new AwsProxyRequestBuilder("/lists/2", HttpMethod.GET)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .buildStream();
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

        handle(requestStream, responseStream);

        AwsProxyResponse response = readResponse(responseStream);
        assertThat(response, is(notNullValue()));
        checkOkResponse(response);
        checkJsonResponseType(response);

        assertThat(response.getBody(), is("{\"id\":2,\"name\":\"Second list\",\"items\":[]}"));
    }

    @Test
    public void getSingleList_doesNotExist() {
        InputStream requestStream = new AwsProxyRequestBuilder("/lists/100100", HttpMethod.GET)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .buildStream();
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

        handle(requestStream, responseStream);

        AwsProxyResponse response = readResponse(responseStream);
        assertThat(response, is(notNullValue()));
        checkNotFoundResponse(response);
    }

    @Test
    public void invalidResource_streamRequest_responds404() {
        InputStream requestStream = new AwsProxyRequestBuilder("/pong", HttpMethod.GET)
                                            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                                            .buildStream();
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

        handle(requestStream, responseStream);

        AwsProxyResponse response = readResponse(responseStream);
        assertThat(response, is(notNullValue()));
        checkNotFoundResponse(response);
    }

    private void handle(InputStream is, ByteArrayOutputStream os) {
        try {
            handler.handleRequest(is, os, lambdaContext);
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private AwsProxyResponse readResponse(ByteArrayOutputStream responseStream) {
        try {
            return LambdaContainerHandler.getObjectMapper().readValue(responseStream.toByteArray(), AwsProxyResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Error while parsing response: " + e.getMessage());
        }
        return null;
    }

    private void checkOkResponse(AwsProxyResponse response) {
        assertThat(response.getStatusCode(), is(Response.Status.OK.getStatusCode()));

        assertThat(response.isBase64Encoded(), is(false));
    }

    private void checkJsonResponseType(AwsProxyResponse response) {
        assertThat(response.getMultiValueHeaders(), hasKey(HttpHeaders.CONTENT_TYPE));
        assertThat(response.getMultiValueHeaders().getFirst(HttpHeaders.CONTENT_TYPE),
                           startsWith(MediaType.APPLICATION_JSON));
    }

    private void checkNotFoundResponse(AwsProxyResponse response) {
        assertThat(response.getStatusCode(), is(Response.Status.NOT_FOUND.getStatusCode()));
    }
}
