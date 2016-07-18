/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package migracion;

import configuration.ConfigurationExample;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Environment;
import oauth.Autenticator;
import oauth.Autorization;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import pojo.User;
import services.ConfigServices;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 *
 * @author iTech-Pc
 */
public class Migracion extends Application<ConfigurationExample> {

       public static void main (String[] args) throws Exception{

       if(args.length > 0) new Migracion().run(args);
       else{
        new Migracion().run(new String[] { "server","./src/main/java/migracion/config.yml" });
           System.err.println("qui");
       }
    }


    @Override
    public void run(ConfigurationExample t, Environment e) throws Exception {
        t.getTemplate();
        t.getDefaultName();
        t.getFilePath();
        configureCors(e);

        e.jersey().register(ConfigServices.class);

        e.jersey().register(new AuthDynamicFeature(
        new OAuthCredentialAuthFilter.Builder<User>()
            .setAuthenticator(new Autenticator())
            .setAuthorizer(new Autorization())
            .setPrefix("Bearer")
            .buildAuthFilter()));

        e.jersey().register(RolesAllowedDynamicFeature.class);
    }

    private void configureCors(Environment e) {
        FilterRegistration.Dynamic filter = e.servlets().addFilter("CORSFilter", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, e.getApplicationContext().getContextPath() + "*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "Origin, Content-Type, Accept, Authorization");
        filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
    }
}


