package services;


import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by root on 14/06/16.
 */

@Path("/garantias/config")
@Produces(MediaType.APPLICATION_JSON)


public class ConfigServices {
    HashMap<String, String> criterial= new HashMap<>();


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/garantias-parametricvalues")
    @PermitAll
    public String insertGarantiasParametricValues(@Context HttpServletRequest req) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String read;
        while((read=br.readLine()) != null) {
            stringBuilder.append(read);
        }
        br.close();
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
