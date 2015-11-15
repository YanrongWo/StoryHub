package models;

import play.db.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;

public class AppController{

    private int max; //max stories on front page
    private ArrayList<Story> stories;
    private int storyIndex;
    private Connection connection;
    
	
    public AppController() {
        this.max = 10;
        //this.stornew ArrayList<Story>();
        this.storyIndex = 0;
        this.connection = play.db.DB.getConnection();
        this.stories = new ArrayList<Story>();
        
	}
    
    public Story createStory(Segment seg) throws SQLException{
        //add null to table and gets last inserted id
        int sId = getNextStoryId();
        //int sId = 0;
        //new Story(Segment )
        Story newOne = new Story(seg, sId);
        stories.add(newOne);
        //save the story to database
        storeStory(newOne);
        return newOne;
    }
    
    public int getNextStoryId() throws SQLException {
        int storyId = -1;
        String INSERT_COMMAND = "INSERT INTO stories(storyid, serialized_object) VALUES (NULL, NULL)";
        String LAST_INSERT = "SELECT LAST_INSERT_ID()";
        PreparedStatement pstmt = this.connection.prepareStatement(INSERT_COMMAND);
        pstmt.executeUpdate();
        pstmt.close();
        pstmt = this.connection.prepareStatement(LAST_INSERT);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            storyId = rs.getInt(1);
        }
        rs.close();
        pstmt.close();
        return storyId;
    }
    
    //not tested
    public boolean fork(Story sto, Segment seg, int segmentId) throws SQLException {
        if (sto.addSegment(seg, segmentId)) {
            storeStory(sto);
            return true;
        }
        return false;
        
    }
    
    public ArrayList<Segment> find(String search) {
        System.out.println("Finding!!!");
        ArrayList<Segment> results = new ArrayList<Segment>();
        for(int i=0; i<stories.size(); i++) {
            results.addAll(stories.get(i).findTags(search));
        }
        return results;
    }

    public Story getStory(int storyId) throws SQLException, IOException, ClassNotFoundException {
        //do table query
        return (Story) deserializeObjectFromDB(storyId);
    }
    
    public ArrayList<Story> getStories() {
        return this.stories;
    }
    
    public void storeStory(Story sto) throws SQLException {
        serializeJavaObjectToDB(sto, sto.getStoryId());
    }
    
    public void serializeJavaObjectToDB(Object objectToSerialize, int storyId) throws SQLException {
        String SQL_SERIALIZE_OBJECT = "UPDATE stories SET serialized_object=? WHERE storyid=?";
        PreparedStatement pstmt = this.connection.prepareStatement(SQL_SERIALIZE_OBJECT);
        pstmt.setInt(2, storyId);
        pstmt.setObject(1, objectToSerialize);
        pstmt.executeUpdate();
        pstmt.close();
    }
    
    public ArrayList<Story> getFrontPageStories(int i) {
        if (stories.size() < max) {
            return stories;
        } else {
            return new ArrayList<Story>(stories.subList(i, i+max));
        }
    }
    
    //http://javapapers.com/core-java/serialize-de-serialize-java-object-from-database/
    public Object deserializeObjectFromDB(int storyId) throws SQLException, IOException, ClassNotFoundException {
        String SQL_DESERIALIZE_OBJECT = "SELECT serialized_object FROM stories WHERE storyId = ?";
        PreparedStatement pstmt = this.connection.prepareStatement(SQL_DESERIALIZE_OBJECT);
        pstmt.setInt(1, storyId);
        ResultSet rs = pstmt.executeQuery();
        Object deSerializedObject = null;
        if (rs.isBeforeFirst()){
            rs.next();
            byte[] buf = rs.getBytes(1);
            ObjectInputStream objectIn = null;
            if (buf != null) {
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
            }
            deSerializedObject = objectIn.readObject();
        }      
        rs.close();
        pstmt.close();
        return deSerializedObject;
        //return null;
    }
    
    public void loadAll() throws SQLException, IOException, ClassNotFoundException {
        this.stories = new ArrayList<Story>();
        String SQL_DESERIALIZE_OBJECT = "SELECT serialized_object FROM stories";
        PreparedStatement pstmt = this.connection.prepareStatement(SQL_DESERIALIZE_OBJECT);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            byte[] buf = rs.getBytes(1);
            ObjectInputStream objectIn = null;
            if (buf != null) {
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                Object deSerializedObject = objectIn.readObject();
                this.stories.add((Story) deSerializedObject);
            }
        }
        rs.close();
        pstmt.close();
    }
    
}