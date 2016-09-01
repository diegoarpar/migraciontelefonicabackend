/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;


import com.mongodb.*;
import configuration.ConfigurationExample;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author iTech-Pc
 */
public class FactoryMongo {
    private static final String COLLECTION_LOG= "logs";

    private MongoClient mongoClient =null;
    private DB database =null ;
    private DBCursor curs;

    DBMongo dbP= new DBMongo();

    public FactoryMongo() {
    }

    public MongoClient getMongoClient(String user, String pass, String url, String dataBase){
        if(mongoClient==null){
            mongoClient = new MongoClient(
                    new MongoClientURI("mongodb://"+user+":"+pass+"@"+url+":27017/?authSource="+dataBase+"&authMechanism=MONGODB-CR"));
        }
        return mongoClient;
    }

    public DB getDatabase(String dataBase){
        if(mongoClient!=null){
            if(database==null){
                database=mongoClient.getDB(dataBase);
            }
        }
        return database;
    }


    public DBCollection getCollection(String name, String user, String pass, String url, String dataBase){
        getMongoClient(user,pass,url,dataBase);
        getDatabase(dataBase);
        switch (name){
            case COLLECTION_LOG:
                return database.getCollection(name);
            default:
                break;
        }

        return database.getCollection(name);
    }

    public DBCollection getCollection(String collection){
        return getCollection(collection, ConfigurationExample.DATABASE_USER,ConfigurationExample.DATABASE_PASS,
                ConfigurationExample.DATABASE_SERVER_URL,ConfigurationExample.DATABASE_NAME);
    }

    public void insertLog(String c){

        dbP.insertGarantias(getCollection(COLLECTION_LOG), curs, mongoClient, c);

    }

    public List<DBObject> getLog(HashMap criterial){
        return dbP.getLogCriterial(getCollection(COLLECTION_LOG), curs, mongoClient, criterial);
    }

}
