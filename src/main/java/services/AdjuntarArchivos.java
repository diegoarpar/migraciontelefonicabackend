package services;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * Created by root on 16/07/16.
 */

    public class AdjuntarArchivos {

        public String AdjuntarArchivos(JSONObject json, String token, String ubicacionArchivo, String urlAdjuntar) throws UnsupportedEncodingException, IOException {
            try {
            HttpClient httpclient = new DefaultHttpClient();
            String responseBody="";
            token=token.replace("Bearer ","");
            HttpEntity entity = MultipartEntityBuilder.create()
                    .addTextBody("model", json.toString())
                    .addBinaryBody("file", (new File(ubicacionArchivo)))
                    .build();

            HttpPost post = new HttpPost(urlAdjuntar);
            post.setHeader("Accept", "application/json, text/plain, */*");
            post.setHeader("Accept-Encoding", "gzip, deflate");
            post.setHeader("Accept-Language", "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3");

            post.addHeader("Authorization",token);
            post.setEntity(entity);
            //System.out.println("Requesting : " + post.getRequestLine());
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = httpclient.execute(post, responseHandler);

            //System.out.println("responseBody : " + responseBody);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "{response:ok}";
        }
    }
