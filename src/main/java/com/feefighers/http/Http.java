package com.feefighers.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class Http {

    enum RequestMethod {
        GET, POST
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
        return httpRequest(RequestMethod.GET, url, null);
    }
    
    public String post(String url, String body) {
    	return httpRequest(RequestMethod.POST, url, body);
    }

	private String httpRequest(RequestMethod requestMethod, String url, String body) {
		HttpEntity entity = null;
		HttpClient client = null;
		InputStream responseInputStream = null;
		
		try {
			HttpParams httpParams = new BasicHttpParams();
			HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
			
			client = new DefaultHttpClient(httpParams);

			String uri = baseUrl + (url.startsWith("/") ? url : "/" + url);
			HttpUriRequest request = null;
			if(RequestMethod.GET.equals(requestMethod)) {
				request = new HttpGet(uri);
			} else if(RequestMethod.POST.equals(requestMethod)) {
				HttpPost postRequest = new HttpPost(uri);
				if(StringUtils.isNotBlank(body)) {
					postRequest.setEntity(new StringEntity(body));
				}
				request = postRequest;
				request.addHeader("Content-Type", "application/x-www-form-urlencoded");
			}
			
			if(StringUtils.isNotBlank(username)) {
				Credentials creds = new UsernamePasswordCredentials(username, password);
				request.addHeader(new BasicScheme().authenticate(creds, request));
			}
			
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
