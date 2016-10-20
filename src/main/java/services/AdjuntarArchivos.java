package services;

import com.google.common.io.Files;
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

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * Created by root on 16/07/16.
 * Munkys APPS copyright
 * CERTICAMARA TELEFONICA
 */

public class AdjuntarArchivos {
        private static HttpClient httpclient = new DefaultHttpClient();
        private String responseBody="";
        private static HttpEntity entity;
        private static File file;
        private static HttpPost post;
        private static ResponseHandler<String> responseHandler = new BasicResponseHandler();
        public String AdjuntarArchivos(JSONObject json, String token, String ubicacionArchivo, String urlAdjuntar) throws Exception {
            try {

                token=token.replace("Bearer ","");
                file = new File(ubicacionArchivo);
                json.put("name",file.getName());
                json.put("fileType", Files.getFileExtension(file.getName()));
                json.put("fileSize",file.length());
                entity = MultipartEntityBuilder.create()
                    .addTextBody("model", json.toString())
                    .addBinaryBody("file", file)
                    .build();
                post = new HttpPost(urlAdjuntar);
                post.setHeader("Accept", "application/json, text/plain, */*");
                post.setHeader("Accept-Encoding", "gzip, deflate");
                post.setHeader("Accept-Language", "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3");
                post.addHeader("Authorization",token);
                post.setEntity(entity);
                System.out.println("Requesting : " + post.getRequestLine());
                responseBody = httpclient.execute(post, responseHandler);
                //System.out.println("responseBody : " + responseBody);


            } catch (Exception e) {
                e.printStackTrace();
                Response.status(Response.Status.INTERNAL_SERVER_ERROR);
                throw new Exception("Error "+ e.getMessage() + e.getCause()+ e.toString());
            }
            return "OK";
        }
    }
