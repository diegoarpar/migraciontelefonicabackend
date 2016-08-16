package services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import configuration.ConfigurationExample;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by root on 14/06/16.
 */

@Path("/migracion/docs")
@Produces(MediaType.APPLICATION_JSON)


public class ConfigServices {
    HashMap<String, String> criterial= new HashMap<>();
    private AdjuntarArchivos adjuntarArchivos= new AdjuntarArchivos();

    @POST
    //@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/insert-file")
    @PermitAll
    public String insertFile(@Context HttpServletRequest req) throws IOException {

        fillCriterialFromString(req.getQueryString());
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String read;
        while((read=br.readLine()) != null) {
            stringBuilder.append(read);
        }
        br.close();
        JSONObject jsonObj = new JSONObject(stringBuilder.toString());

        //fm.insertGarantiasParametricValues(stringBuilder.toString());
        adjuntarArchivos.AdjuntarArchivos(jsonObj,req.getHeader("authorization"), ConfigurationExample.UPLOAD_FILE_PATH+ jsonObj.get("name"), ConfigurationExample.URL_UPLOAD_FILE);
        return  "FIRMANDO";
    }

    @POST
    //@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/insert-log")
    @PermitAll
    public String insertLog(@Context HttpServletRequest req) throws IOException {

        fillCriterialFromString(req.getQueryString());
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String read;
        while((read=br.readLine()) != null) {
            stringBuilder.append(read);
        }
        br.close();
        JSONObject jsonObj = new JSONObject(stringBuilder.toString());

        //fm.insertGarantiasParametricValues(stringBuilder.toString());

        return  "FIRMANDO";
    }
    @GET
    //@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/insert-log")
    @PermitAll
    public String getLog(@Context HttpServletRequest req, @QueryParam(value="startDate") String startDate,@QueryParam(value="endDate") String endDate) throws IOException {

        fillCriterialFromString(req.getQueryString());
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String read;
        while((read=br.readLine()) != null) {
            stringBuilder.append(read);
        }
        br.close();
        JSONObject jsonObj = new JSONObject(stringBuilder.toString());
        //get date
        //fm.insertGarantiasParametricValues(stringBuilder.toString());

        return  "FIRMANDO";
    }

    /*OTHER METHOD*/
    private void fillCriterialFromString( String queryString){
        criterial.clear();
        if(queryString!=null)
            for (String split : queryString.split("&")) {
                if (split.split("=").length == 2) {
                    criterial.put(split.split("=")[0], split.split("=")[1]);
                }
            }
    }
}
