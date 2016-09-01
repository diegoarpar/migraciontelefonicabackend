/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mongodb.*;
import com.mongodb.util.JSON;
import java.util.*;

/**
 *
 * @author iTech-Pc
 */

public class DBMongo {
    public String insertGarantias(DBCollection collection,DBCursor curs,MongoClient mongoClient, String c){

        BasicDBList documentList =(BasicDBList) JSON.parse(c);
        BasicDBObject document;
        for (Object object : documentList) {
            document=(BasicDBObject) object;
            collection.insert(document);
        }
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

}
