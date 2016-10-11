package services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DBObject;
import configuration.ConfigurationExample;
import db.FactoryMongo;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by alejandro on 14/06/16.
 * Munkys APPS copyright
 * CERTICAMARA TELEFONICA
 */

@Path("/migracion/docs")
@Produces(MediaType.APPLICATION_JSON)

public class ConfigServices {

    HashMap<String, String> criterial= new HashMap<>();

    /**/
    private AdjuntarArchivos adjuntarArchivos= new AdjuntarArchivos();


    private FactoryMongo db = new FactoryMongo();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/insert-file")
    @PermitAll
    public String insertFile(@Context HttpServletRequest req) throws Exception {

        fillCriterialFromString(req.getQueryString());
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String read;
        while((read=br.readLine()) != null) {
            stringBuilder.append(read);
        }
        br.close();
        JSONObject jsonObj = new JSONObject(stringBuilder.toString());
        JSONObject jsonObj2 = new JSONObject(stringBuilder.toString());
        jsonObj2.put("dateLog",ZonedDateTime.now().format( DateTimeFormatter.ISO_INSTANT ));
        String fileName = jsonObj.getString("fileName");
        jsonObj.remove("fileName");
        jsonObj2.remove("fileName");
        try {
            jsonObj2.put("eventLog","Success_Upload_Document");
            if(fileName!=null)
                jsonObj2.put("fileName",fileName);
            String adjuntarString = adjuntarArchivos.AdjuntarArchivos(
                    jsonObj, req.getHeader("authorization"),
                    ConfigurationExample.UPLOAD_FILE_PATH + jsonObj.get("name"),
                    ConfigurationExample.URL_UPLOAD_FILE);
            jsonObj2.put("SuccessUploadDocument",adjuntarString);
            db.insertLog(jsonObj2.toString());
        } catch (Exception e) {
            jsonObj2.put("eventLog","Failed_Upload_Document");
            if(fileName!=null)
                jsonObj2.put("fileName",fileName);
            jsonObj2.put("FailedUploadDocument",e.getMessage());
            db.insertLog(jsonObj2.toString());
            Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            throw new Exception(jsonObj2.toString());
        }


        return   jsonObj2.toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/log")
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

        jsonObj.put("dateLog", ZonedDateTime.now().format( DateTimeFormatter.ISO_INSTANT ));
        db.insertLog(jsonObj.toString());

        return  jsonObj.toString();
    }
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/log")
    @PermitAll
    public String getLog(@Context HttpServletRequest req, @QueryParam(value="startDate") String stringStartDate, @QueryParam(value="endDate") String stringEndDate) throws IOException {

        try {
            fillCriterialFromString(req.getQueryString());
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
            String read;
            while ((read = br.readLine()) != null) {
                stringBuilder.append(read);
            }
            br.close();
            DateFormat sourceFormatS = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat sourceFormatE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");

            TimeZone tz = TimeZone.getTimeZone("UTC");
            sourceFormatE.setTimeZone(tz);
            sourceFormatS.setTimeZone(tz);

            Calendar c = Calendar.getInstance();
            c.setTime(sourceFormatS.parse(stringEndDate));
            c.add(Calendar.DATE, 1);  // number of days to add
            Date endDate = c.getTime();

            Date startDate = sourceFormatS.parse(stringStartDate);


            return db.getLog(startDate, endDate).toString();
        }catch (Exception e) {

            return  "ERROR";
        }

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
