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
                
               
                ps = conn.prepareStatement(QUERY_REGISTER);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
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
                
             
                
                ps = conn.prepareStatement(DROP_COURSE);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
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
                
           
                ps = conn.prepareStatement(DROP_ALL);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
       
                
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
                
             
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                ps.execute();
                rs = ps.getResultSet();
                result = DAOUtility.getResultSetAsJson(rs);
               
            
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
