package com.feefighters.samurai.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.Assert;
import org.testng.IObjectFactory;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

@PrepareForTest(DefaultHttpClient.class) 
public class HttpTest {

	@ObjectFactory
	public IObjectFactory setObjectFactory() {
		return new PowerMockObjectFactory();
	}
	
	@Test
	public void shouldMakeGetRequest() throws Exception {
		final String output = "OK";
		
		final StatusLine statusLine = Mockito.mock(StatusLine.class);
		Mockito.when(statusLine.getStatusCode()).thenReturn(200);
		
		final HttpEntity entity = Mockito.mock(HttpEntity.class);
		Mockito.when(entity.getContent()).thenReturn(new ByteArrayInputStream(output.getBytes()));
		
		final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
		Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
		Mockito.when(httpResponse.getEntity()).thenReturn(entity);
		
		final ClientConnectionManager connectionManager = Mockito.mock(ClientConnectionManager.class);
		
		final DefaultHttpClient httpClient = PowerMockito.mock(DefaultHttpClient.class);
		final ArgumentCaptor<HttpUriRequest> uriRequestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
		PowerMockito.when(httpClient.execute(uriRequestCaptor.capture())).thenReturn(httpResponse);
		PowerMockito.when(httpClient.getConnectionManager()).thenReturn(connectionManager);

		final Http http = Mockito.spy(new Http("user", "pass", "http://baseurl.com"));
		Mockito.when(http.newHttpClientInstance()).thenReturn(httpClient);
		
		// test
		final String httpOutput = http.get("geturl");
		
		// verify
		Assert.assertEquals(httpOutput, output);
		
		final HttpUriRequest uriRequest = uriRequestCaptor.getValue();
		Assert.assertNotNull(uriRequest);
		Assert.assertEquals(uriRequest.getMethod(), "GET");
		Assert.assertEquals(uriRequest.getRequestLine().getUri(), "http://baseurl.com/geturl");
		
		Mockito.verify(connectionManager).shutdown();
	}
	
	@Test
	public void shouldMakePostRequest() throws Exception {
		final String output = "OK";
		
		final StatusLine statusLine = Mockito.mock(StatusLine.class);
		Mockito.when(statusLine.getStatusCode()).thenReturn(200);
		
		final HttpEntity entity = Mockito.mock(HttpEntity.class);
		Mockito.when(entity.getContent()).thenReturn(new ByteArrayInputStream(output.getBytes()));
		
		final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
		Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
		Mockito.when(httpResponse.getEntity()).thenReturn(entity);
		
		final ClientConnectionManager connectionManager = Mockito.mock(ClientConnectionManager.class);
		
		final DefaultHttpClient httpClient = PowerMockito.mock(DefaultHttpClient.class);
		final ArgumentCaptor<HttpUriRequest> uriRequestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
		PowerMockito.when(httpClient.execute(uriRequestCaptor.capture())).thenReturn(httpResponse);
		PowerMockito.when(httpClient.getConnectionManager()).thenReturn(connectionManager);

		final Http http = Mockito.spy(new Http("user", "pass", "http://baseurl.com"));
		Mockito.when(http.newHttpClientInstance()).thenReturn(httpClient);
		
		// test
		final String httpOutput = http.post("posturl", "body");
		
		// verify
		Assert.assertEquals(httpOutput, output);
		
		final HttpUriRequest uriRequest = uriRequestCaptor.getValue();
		Assert.assertNotNull(uriRequest);
		Assert.assertEquals(uriRequest.getMethod(), "POST");
		Assert.assertEquals(uriRequest.getRequestLine().getUri(), "http://baseurl.com/posturl");
		
		Mockito.verify(connectionManager).shutdown();
	}	
	
	@Test
	public void shouldMakePutRequest() throws Exception {
		final String output = "OK";
		
		final StatusLine statusLine = Mockito.mock(StatusLine.class);
		Mockito.when(statusLine.getStatusCode()).thenReturn(200);
		
		final HttpEntity entity = Mockito.mock(HttpEntity.class);
		Mockito.when(entity.getContent()).thenReturn(new ByteArrayInputStream(output.getBytes()));
		
		final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
		Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
		Mockito.when(httpResponse.getEntity()).thenReturn(entity);
		
		final ClientConnectionManager connectionManager = Mockito.mock(ClientConnectionManager.class);
		
		final DefaultHttpClient httpClient = PowerMockito.mock(DefaultHttpClient.class);
		final ArgumentCaptor<HttpUriRequest> uriRequestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
		PowerMockito.when(httpClient.execute(uriRequestCaptor.capture())).thenReturn(httpResponse);
		PowerMockito.when(httpClient.getConnectionManager()).thenReturn(connectionManager);

		final Http http = Mockito.spy(new Http("user", "pass", "http://baseurl.com"));
		Mockito.when(http.newHttpClientInstance()).thenReturn(httpClient);
		
		// test
		final String httpOutput = http.put("puturl", "body");
		
		// verify
		Assert.assertEquals(httpOutput, output);
		
		final HttpUriRequest uriRequest = uriRequestCaptor.getValue();
		Assert.assertNotNull(uriRequest);
		Assert.assertEquals(uriRequest.getMethod(), "PUT");
		Assert.assertEquals(uriRequest.getRequestLine().getUri(), "http://baseurl.com/puturl");
		
		Mockito.verify(connectionManager).shutdown();
	}		
	
	@Test
	public void shouldThrowHttpExceptionWithStatus404() throws Exception {
		final StatusLine statusLine = Mockito.mock(StatusLine.class);
		Mockito.when(statusLine.getStatusCode()).thenReturn(404);
		
		final HttpEntity entity = Mockito.mock(HttpEntity.class);
		Mockito.when(entity.getContent()).thenReturn(new ByteArrayInputStream("FAIL".getBytes()));
		
		final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
		Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
		Mockito.when(httpResponse.getEntity()).thenReturn(entity);
		
		final ClientConnectionManager connectionManager = Mockito.mock(ClientConnectionManager.class);
		
		final DefaultHttpClient httpClient = PowerMockito.mock(DefaultHttpClient.class);
		PowerMockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenReturn(httpResponse);
		PowerMockito.when(httpClient.getConnectionManager()).thenReturn(connectionManager);

		final Http http = Mockito.spy(new Http("user", "pass", "http://baseurl.com"));
		Mockito.when(http.newHttpClientInstance()).thenReturn(httpClient);
		
		// test
		try {
			http.get("geturl");
		} catch(HttpException ex) {
			Assert.assertTrue(ex.getMessage().contains("FAIL"));
			return;
		}
		Assert.fail(); // 	@Test(expectedExceptions=HttpException.class) does not work
	}
	
	@Test
	public void shouldThrowHttpExceptionWithWrappedIOException() throws Exception {
		final StatusLine statusLine = Mockito.mock(StatusLine.class);
		Mockito.when(statusLine.getStatusCode()).thenReturn(200);
		
		final HttpEntity entity = Mockito.mock(HttpEntity.class);
		Mockito.when(entity.getContent()).thenReturn(new ByteArrayInputStream("FAIL".getBytes()));
		
		final HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
		Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
		Mockito.when(httpResponse.getEntity()).thenReturn(entity);
		
		final ClientConnectionManager connectionManager = Mockito.mock(ClientConnectionManager.class);
		
		final DefaultHttpClient httpClient = PowerMockito.mock(DefaultHttpClient.class);
		final IOException ioException = new IOException();
		PowerMockito.when(httpClient.execute(Mockito.any(HttpUriRequest.class))).thenThrow(ioException);
		PowerMockito.when(httpClient.getConnectionManager()).thenReturn(connectionManager);

		final Http http = Mockito.spy(new Http("user", "pass", "http://baseurl.com"));
		Mockito.when(http.newHttpClientInstance()).thenReturn(httpClient);
		
		// test
		try {
			http.get("geturl");
		} catch(HttpException ex) {
			Assert.assertEquals(ex.getCause(), ioException);
			return;
		}
		Assert.fail(); 
	}	
}
