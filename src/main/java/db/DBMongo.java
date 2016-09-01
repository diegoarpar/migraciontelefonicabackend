/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mongodb.*;
import com.mongodb.util.JSON;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * @author iTech-Pc
 */

public class DBMongo {
    public String insertLog(DBCollection collection,DBCursor curs,MongoClient mongoClient, String c){

        BasicDBObject o = (BasicDBObject) JSON.parse(c);
         collection.insert(o);
    return "Insertado";
    }

    public List<DBObject> getLogCriterial(DBCollection collection,DBCursor curs,MongoClient mongoClient, HashMap criterial){
        List<DBObject> data= new ArrayList<>();
        BasicDBObject searchQuery2  = new BasicDBObject();
        Iterator it = criterial.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            searchQuery2.append(pair.getKey().toString(),pair.getValue().toString().equals("null")?null:pair.getValue().toString().equals("true")?true:pair.getValue().toString());
            it.remove();
        }
        //BasicDBObject searchQuery2  = new BasicDBObject();
        curs=collection.find(searchQuery2);

        while(curs.hasNext()) {
                DBObject o = curs.next();
                data.add(o);
            }
        return data;
    }

    public List<DBObject> getLogBetweenDate(DBCollection collection,DBCursor curs,MongoClient mongoClient, Object startDate, Object endDate){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String nowAsString = df.format(new Date());
        List<DBObject> data= new ArrayList<>();

        DBObject query1= (DBObject) QueryBuilder.start().put("dateLog").greaterThanEquals(df.format(startDate)).lessThanEquals(df.format(endDate)).get();
        curs=collection.find(query1);

        while(curs.hasNext()) {
            DBObject o = curs.next();
            data.add(o);
        }
        return data;
    }
}
