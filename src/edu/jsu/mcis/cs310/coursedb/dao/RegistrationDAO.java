package edu.jsu.mcis.cs310.coursedb.dao;

import com.github.cliftonlabs.json_simple.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    private static final String QUERY_REGISTER = "insert into registration values (?, ?, ?)";
    private static final String DROP_COURSE = "delete from registration where studentid=? and termid=? and crn =?";
    private static final String DROP_ALL = "delete from registration where studentid=? and termid=?";
    private static final String QUERY_FIND = "SELECT * FROM registration where studentid=? and termid=? ORDER BY crn";
     
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            
            
            if (conn.isValid(0)) {
                
                // INSERT YOUR CODE HERE
                ps = conn.prepareStatement(QUERY_REGISTER);
                ps.setString(1, Integer.toString(studentid));
                ps.setString(2, Integer.toString(termid));
                ps.setString(3, Integer.toString(crn));
                
                int rowsChanged = ps.executeUpdate();
                System.out.println(rowsChanged);
                if(rowsChanged > 0){
                    return true;
                }
                else{
                    System.out.println("Did not update rows");
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

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // INSERT YOUR CODE HERE
                
                ps = conn.prepareStatement(DROP_COURSE);
                ps.setString(1, Integer.toString(studentid));
                ps.setString(2, Integer.toString(termid));
                ps.setString(3, Integer.toString(crn));
                
                int rowsChanged = ps.executeUpdate();
                if(rowsChanged > 0){
                    return true;
                }
                else{
                    System.out.println("Did not update rows");
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // INSERT YOUR CODE HERE
                ps = conn.prepareStatement(DROP_ALL);
                ps.setString(1, Integer.toString(studentid));
                ps.setString(2, Integer.toString(termid));
       
                
                int rowsChanged = ps.executeUpdate();
                if(rowsChanged > 0){
                    return true;
                }
                else{
                    System.out.println("Did not update rows");
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        
        String result = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                JsonArray courseEnrolled = new JsonArray();
                
                // INSERT YOUR CODE HERE
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, Integer.toString(studentid));
                ps.setString(2, Integer.toString(termid));
                
                ps.execute();
                rs = ps.getResultSet();
                
                while(rs.next()){
                    JsonObject course = new JsonObject();
                    course.put("studentid", rs.getString("studentid"));
                    course.put("termid", rs.getString("termid"));
                    course.put("crn", rs.getString("crn"));
                    
                    courseEnrolled.add(course);
                    
               
              }
              result = Jsoner.serialize(courseEnrolled);
            
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
