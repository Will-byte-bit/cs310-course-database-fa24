package edu.jsu.mcis.cs310.coursedb.dao;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import java.io.BufferedReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Iterator;
import java.util.List;

public class SectionDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        String result = "[]";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
           
                JsonArray courseArray = new JsonArray();
                
                //setting up connection
                ps = conn.prepareStatement(QUERY_FIND);
                
                //setting query
                ps.setString(1, Integer.toString(termid));
                ps.setString(2, subjectid);
                ps.setString(3, num);
                
                
                
                boolean hasresults = ps.execute();
                
                if(hasresults){
                    rs = ps.getResultSet();
                    
                    while(rs.next()){

                        JsonObject course = new JsonObject();
                   
                        //adding to course object
                        
                        course.put("termid", rs.getString("termid"));
                        course.put("crn", rs.getString("crn"));
                        course.put("subjectid", rs.getString("subjectid"));
                        course.put("num", rs.getString("num"));
                        course.put("section", rs.getString("section"));
                        course.put("scheduletypeid", rs.getString("scheduletypeid"));
                        course.put("start", rs.getString("start"));
                        course.put("end", rs.getString("end"));
                        course.put("days", rs.getString("days"));
                        course.put("where", rs.getString("where"));
                        course.put("instructor", rs.getString("instructor"));
                        System.out.println(rs.getString("instructor"));

                        courseArray.add(course);
                    }

                    return Jsoner.serialize(courseArray);
                }
                else{
                    System.out.println("No results");
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}