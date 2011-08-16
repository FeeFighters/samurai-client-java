package com.feefighers.http;

import java.io.IOException;
import java.io.InputStream;

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

    private static final String DEFAULT_CONTENT_TYPE = "application/xml";

	enum RequestMethod {
        GET, POST, PUT
    }    

    private String username;
    private String password;
    private String baseUrl;

    public Http(String username, String password, String baseUrl) {
    	this.username = username;
    	this.password = password;
        this.baseUrl = baseUrl;
        
    }

    public String get(String url) {
        return httpRequest(RequestMethod.GET, url, null, null);
    }
    
    public String put(String url, String body) {
    	return httpRequest(RequestMethod.PUT, url, body, DEFAULT_CONTENT_TYPE);
    }
    
    public String post(String url, String body) {
    	return httpRequest(RequestMethod.POST, url, body, DEFAULT_CONTENT_TYPE);
    }
    
    public String post(String url, String body, String contentType) {
    	return httpRequest(RequestMethod.POST, url, body, contentType);
    }

	private String httpRequest(RequestMethod requestMethod, String url, String body, String contentType) {
		HttpEntity entity = null;
		HttpClient client = null;
		InputStream responseInputStream = null;
		
		try {
			HttpParams httpParams = new BasicHttpParams();
			HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
			HttpProtocolParams.setUserAgent(httpParams, "Samurai Java Client");
			
			client = new DefaultHttpClient(httpParams);

			String uri = baseUrl + (url.startsWith("/") ? url : "/" + url);
			HttpUriRequest request = null;
			HttpEntityEnclosingRequest entityRequest = null;
			
			if(RequestMethod.GET.equals(requestMethod)) {
				request = new HttpGet(uri);
			} else if(RequestMethod.POST.equals(requestMethod)) {
				HttpPost postRequest = new HttpPost(uri);
				request = postRequest;
				entityRequest = postRequest;
			} else if(RequestMethod.PUT.equals(requestMethod)) {
				HttpPut putRequest = new HttpPut(uri);
				request = putRequest;
				entityRequest = putRequest;
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
			
			HttpResponse response = client.execute(request);
			checkHttpStatus(response);
			entity = response.getEntity();
			
			responseInputStream = entity.getContent();
			String output = IOUtils.toString(responseInputStream);
			
			return output;
			
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
    
    public static void checkHttpStatus(HttpResponse response) {
    	int statusCode = response.getStatusLine().getStatusCode();
        if (isErrorCode(statusCode)) {
            throw new HttpException(statusCode);
        }
    }

    private static boolean isErrorCode(int responseCode) {
        return responseCode != 200 && responseCode != 201 && responseCode != 422 && responseCode != 302;
    }
    
    public static void enableDebug() {
		java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(java.util.logging.Level.FINEST);
		java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.FINEST);

		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "debug");    	
    }
}
