package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;

public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        
        //complete record of data
        JsonArray records = new JsonArray();
        
        try {
        
            if (rs != null) {
                
                
                ResultSetMetaData rsMeta = rs.getMetaData();
                int numberOfCols = rsMeta.getColumnCount();
               
                while(rs.next()) {

                    JsonObject currentRow = new JsonObject();
                    for (int i=1; i<=numberOfCols; i++) {
                      String colName = rsMeta.getColumnName(i);

                      currentRow.put(colName, rs.getString(colName));

                    }

                    records.add(currentRow);

                 
                 }
                    
            }
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return Jsoner.serialize(records);
        
    
    
}
}
