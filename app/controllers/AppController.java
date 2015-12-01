package controllers;

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
import models.*;

/**
 * AppController controls the storylist and connecting to the database
 * @author      Java the Hutt
 * @version     0.1
 */
public class AppController{

    private int max; //max stories on front page
    private ArrayList<Story> stories;
    private int storyIndex;
    private Connection connection;
    
	
    public AppController(Connection conn) {
        this.max = 10;
        this.storyIndex = 0;
        this.connection = conn;
        //this.connection = play.db.DB.getConnection();
        this.stories = new ArrayList<Story>();
        
	}

    public void setConnection(Connection conn) {
        this.connection = conn;
    }
    
   /**
    * Creates story and saves it into the stories list and stores it in the database
    * @param Segment to create story with
    * @return Story created
    */
    public Story createStory(Segment seg) throws SQLException{
        //add null to table and gets last inserted id
        int sId = getNextStoryId();
        Story newOne = new Story(seg, sId);
        stories.add(newOne);
        //save the story to database
        storeStory(newOne);
        return newOne;
    }

   /**
    * Executes a query in the database to get the next story id
    * @return next story id
    */
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
    
   /**
    * Adds a segment to story at segment with segmentId
    * @param story to add it to
    * @param segment to add
    * @param segment id at which to add the segment
    * @return true if adding segment was successful
    */
    public boolean fork(Story sto, Segment seg, int segmentId) throws SQLException {
        if (sto.addSegment(seg, segmentId)) {
            storeStory(sto);
            return true;
        }
        return false;
        
    }
    
   /**
    * finds and returns all segments with matching tag
    * @param string tag to search
    * @return arraylist of segments with matching tag
    */
    public ArrayList<Segment> findByTag(String search) {
        ArrayList<Segment> results = new ArrayList<Segment>();
        for(int i=0; i<stories.size(); i++) {
            results.addAll(stories.get(i).findTags(search));
        }
        return results;
    }

   /**
    * finds and returns all segments with matching title
    * @param string title keyword to search
    * @return arraylist of segments with matching title
    */
    public ArrayList<Segment> findByTitle(String search){
        ArrayList<Segment> results = new ArrayList<Segment>();
        for (int i = 0 ; i < stories.size(); i ++){
            results.addAll(stories.get(i).findTitles(search));
        }
        return results;
    }

   /**
    * Executes a query to find the total number of stories in the database
    * @return story size
    */
    public int getStoryListSize() throws SQLException {
        int storySize = -1;
        String SIZE_COMMAND = "SELECT count(serialized_object) from stories";
        PreparedStatement pstmt = this.connection.prepareStatement(SIZE_COMMAND);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            storySize = rs.getInt(1);
        }
        rs.close();
        pstmt.close();
        return storySize;
    }

   /**
    * stores story by serializing and saving to database
    * @param story object
    */
    public void storeStory(Story sto) throws SQLException {
        String SQL_SERIALIZE_OBJECT = "UPDATE stories SET serialized_object=? WHERE storyid=?";
        PreparedStatement pstmt = this.connection.prepareStatement(SQL_SERIALIZE_OBJECT);
        pstmt.setInt(2, sto.getStoryId());
        pstmt.setObject(1, sto);
        pstmt.executeUpdate();
        pstmt.close();
    }
    
   /**
    * Executes a query to the number of stories within the offset
    * @param offset
    * @return arraylist of stories with story ids within the offset
    */
    public ArrayList<Story> getFrontPageStories(int i) throws SQLException, IOException, ClassNotFoundException {
        int storySize = getStoryListSize();
        int maxLoad = i+this.max;
        if(maxLoad>storySize) {
            maxLoad = storySize;
        } else {
            maxLoad--;
        }
        ArrayList<Story> frontStories = new ArrayList<Story>();
        String SQL_DESERIALIZE_OBJECT = "SELECT serialized_object FROM stories WHERE storyid BETWEEN ? AND ?";
        PreparedStatement pstmt = this.connection.prepareStatement(SQL_DESERIALIZE_OBJECT);
        pstmt.setInt(1, i);
        pstmt.setInt(2, maxLoad);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            byte[] buf = rs.getBytes(1);
            ObjectInputStream objectIn = null;
            if (buf != null) {
                objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                Object deSerializedObject = objectIn.readObject();
                frontStories.add((Story) deSerializedObject);
            }
        }
        rs.close();
        pstmt.close();
        return frontStories;
    }

   /**
    * gets story with story id by deserializing it from the database
    * @param story id
    * @return story object
    */
    public Story getStory(int storyId) throws SQLException, IOException, ClassNotFoundException {
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
        return (Story) deSerializedObject;
    }
    
   /**
    * Executes a query in the database to retrieve all objects in table stories and saving it
    * to stories arraylist in AppController
    */
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

    //accessors
    public int getMax() {
        return this.max;
    }

    public ArrayList<Story> getStories() {
        return this.stories;
    }


    
}