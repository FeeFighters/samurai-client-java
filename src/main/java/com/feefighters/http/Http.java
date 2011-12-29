package com.feefighters.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class Http {

    private static final String CONTENT_CHARSET = "UTF-8";
	private static final String SAMURAI_USER_AGENT = "Samurai Java Client";
	private static final String DEFAULT_CONTENT_TYPE = "application/xml";

    private String username;
    private String password;
    private String baseUrl;

    public Http(String username, String password, String baseUrl) {
    	this.username = username;
    	this.password = password;
        this.baseUrl = baseUrl;
        
    }

    public String get(String url) throws HttpException {
        return httpRequest(HttpRequestMethod.GET, url, null, null);
    }
    
    public String put(String url, String body) throws HttpException {
    	return httpRequest(HttpRequestMethod.PUT, url, body, DEFAULT_CONTENT_TYPE);
    }
    
    public String post(String url, String body) throws HttpException {
    	return httpRequest(HttpRequestMethod.POST, url, body, DEFAULT_CONTENT_TYPE);
    }
    
    public String post(String url, String body, String contentType) throws HttpException {
    	return httpRequest(HttpRequestMethod.POST, url, body, contentType);
    }

	private String httpRequest(HttpRequestMethod requestMethod, String url, String body, String contentType) throws HttpException {
		HttpEntity entity = null;
		HttpClient client = null;
		InputStream responseInputStream = null;
		
		try {			
			client = newHttpClientInstance();

			String uri = baseUrl + (url.startsWith("/") ? url : "/" + url);
			HttpUriRequest request = createHttpRequest(requestMethod, body, contentType, uri);
			
			HttpResponse response = client.execute(request);
			checkHttpStatus(response);
			entity = response.getEntity();
			
			responseInputStream = entity.getContent();

            return IOUtils.toString(responseInputStream);
			
		} catch (IOException ex) {
			throw new HttpException(ex);
		} catch (AuthenticationException ex) {
			throw new HttpException(ex);
		} finally {
			if(responseInputStream != null) {
				IOUtils.closeQuietly(responseInputStream);
			}
			if(client != null) {
				client.getConnectionManager().shutdown();
			}
		}    	
    }

	protected DefaultHttpClient newHttpClientInstance() {
		HttpParams httpParams = new BasicHttpParams();
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParams, CONTENT_CHARSET);
		HttpProtocolParams.setUserAgent(httpParams, SAMURAI_USER_AGENT);
		
		return new DefaultHttpClient(httpParams);
	}

	protected HttpUriRequest createHttpRequest(HttpRequestMethod requestMethod, String body,
			String contentType, String uri)
			throws UnsupportedEncodingException, AuthenticationException {
		HttpUriRequest request = null;
		HttpEntityEnclosingRequest entityRequest = null;
		
		if(HttpRequestMethod.GET.equals(requestMethod)) {
			request = new HttpGet(uri);
		} else if(HttpRequestMethod.POST.equals(requestMethod)) {
			HttpPost postRequest = new HttpPost(uri);
			request = postRequest;
			entityRequest = postRequest;
		} else if(HttpRequestMethod.PUT.equals(requestMethod)) {
			HttpPut putRequest = new HttpPut(uri);
			request = putRequest;
			entityRequest = putRequest;
		} else {
			throw new HttpException("Unknown request method " + requestMethod.name());
		}
		
		if(entityRequest != null) {
			if(StringUtils.isNotBlank(body)) {
				entityRequest.setEntity(new StringEntity(body));
			}
			if(contentType != null) {
				entityRequest.addHeader("Content-Type", contentType);
			}
		}
		
		if(StringUtils.isNotBlank(username)) {
			Credentials creds = new UsernamePasswordCredentials(username, password);
			request.addHeader(new BasicScheme().authenticate(creds, request));
		}
		
		request.setHeader("Connection", "close");
		request.setHeader("Accept", "*/*");
		
		return request;
	}
    
    public static void checkHttpStatus(HttpResponse response) {
    	int statusCode = response.getStatusLine().getStatusCode();
        if (isErrorCode(statusCode)) {
            try {
                String body = IOUtils.toString(response.getEntity().getContent());
                throw new HttpException(body);
            } catch (IOException ex) {
                throw new HttpException(ex);
            }
        }
    }

    private static boolean isErrorCode(int responseCode) {
        return responseCode != 200 && responseCode != 201 && responseCode != 422 && responseCode != 302;
    }

}
