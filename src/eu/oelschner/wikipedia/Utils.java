package eu.oelschner.wikipedia;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.net.http.HttpRequest.Builder;


public class Utils {

	public static HttpResponse<String> httpRequest (URI uri, HashMap<String, String> headers) throws IOException, InterruptedException {
		//Creating client
		HttpClient client = HttpClient.newHttpClient();
        Builder builder= HttpRequest.newBuilder();
        builder.uri(uri);
        
        //Adding Header to Request
        for(String key : headers.keySet()) {
        	builder.header(key, headers.get(key));
        }
              
        //Building request
        HttpRequest request = builder.build();

        //Request
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        return response;
		
	}
	
}
