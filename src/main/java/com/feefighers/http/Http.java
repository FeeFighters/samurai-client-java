package com.feefighers.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
        return httpRequest(RequestMethod.GET, url);
    }

	private String httpRequest(RequestMethod requestMethod, String url) {
		HttpEntity entity = null;
		
		try {
			HttpParams httpParams = new BasicHttpParams();
			HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
			
			HttpClient client = new DefaultHttpClient(httpParams);

			String uri = baseUrl + url;
			HttpGet request = new HttpGet(uri);
			
			Credentials creds = new UsernamePasswordCredentials(username, password);
			request.addHeader(new BasicScheme().authenticate(creds, request));

			HttpResponse response = client.execute(request);
			checkHttpStatus(response);
			entity = response.getEntity();
			
			InputStream inputStream = entity.getContent();
			String output = IOUtils.toString(inputStream);
			
			return output;
			
		} catch (IOException ex) {
			throw new HttpException(ex);
		} catch (AuthenticationException ex) {
			throw new HttpException(ex);
		} finally {
		
		}    	
    }
    
    public static void checkHttpStatus(HttpResponse response) {
    	int statusCode = response.getStatusLine().getStatusCode();
        if (isErrorCode(statusCode)) {
            throw new HttpException(statusCode);
        }
    }

    private static boolean isErrorCode(int responseCode) {
        return responseCode != 200 && responseCode != 201 && responseCode != 422;
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
