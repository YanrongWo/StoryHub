import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;

import java.sql.SQLException;

import play.db.Database;
import play.db.Databases;
import play.db.evolutions.*;
import java.sql.Connection;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import models.*;
import controllers.*;

import org.junit.*;
import static org.junit.Assert.*;
import static play.test.Helpers.*;
import static org.junit.Assert.*;
import com.google.common.collect.*;
import controllers.*;
import models.*;
import java.util.*;
import java.sql.SQLException;

import models.*;
import controllers.*;
import java.lang.*;
import java.io.*;

public class AppControllerTest{
	static Database database;
    static Connection connection;

    @BeforeClass
    public static void createDatabase() {
        database = Databases.createFrom(
            "test",
            "com.mysql.jdbc.Driver",
            "jdbc:mysql://test.ctsufn7qqcwv.us-west-2.rds.amazonaws.com:3306/test",
            ImmutableMap.of(
                "username", "javathehutt",
                "password", "starwars"
            )
        );
        connection = database.getConnection();
    }

    @AfterClass
    public static void shutdownDatabase() {
        database.shutdown();
    }

    @Test 
    public void createStory_valid() throws SQLException{
        Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
        s1.setSegmentId(0);
        AppController a = new AppController(connection);
        a.createStory(s1);
        assertTrue(connection.prepareStatement("Select * from stories").executeQuery().next());
    }	

    @Test(expected=NullPointerException.class)
    public void createStory_null() throws SQLException{
    	AppController a = new AppController(connection);
        a.createStory(null);
    }

    @Test 
    public void createStory_size() throws SQLException{
        Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
        s1.setSegmentId(0);
        AppController a = new AppController(connection);
        a.createStory(s1);
        assertEquals(a.getStories().size(), 1);
    }	

    @Test
    public void getNextStoryId() throws SQLException{
        AppController a = new AppController(connection);
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        a.createStory(seg1);
        int nextId = a.getNextStoryId();
        assertEquals(nextId, 2);
    }

    @Before
    public void createTable() throws SQLException{
        String CREATE_COMMAND = "CREATE TABLE stories (storyid int(11) NOT NULL auto_increment, serialized_object blob, PRIMARY KEY (storyid))";
        PreparedStatement pstmt = connection.prepareStatement(CREATE_COMMAND);
        pstmt.executeUpdate();
    }

    @After
    public void dropTable() throws SQLException{
        String DROP_COMMAND = "drop table stories";
        PreparedStatement pstmt = connection.prepareStatement(DROP_COMMAND);
        pstmt.executeUpdate();
    }

    @Test
    public void fork_true() throws SQLException {
    	AppController a = new AppController(connection);
    	String[] t1 = {"tag1", "tag2"};
        Segment seg = new Segment("Seg 1", "Auth", "Content", t1);
    	Story sto = a.createStory(seg);
    	boolean result = a.fork(sto, seg, 0);
    	boolean expected = true;
    	assertEquals(expected, result);
    }

    @Test
    public void fork_false() throws SQLException{
    	AppController a = new AppController(connection);
    	String[] t1 = {"tag1", "tag2"};
        Segment seg = new Segment("Seg 1", "Auth", "Content", t1);
    	Story sto = a.createStory(seg);
    	boolean result = a.fork(sto, seg, 2);
    	boolean expected = false; 
    	assertEquals(expected, result);
    }


    @Test
    public void findByTag() throws SQLException{
    	AppController a = new AppController(this.connection);

    	// Create a small set of stories and insert them into the database
    	Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
    	a.createStory(s1);

    	Segment s2 = new Segment("Segment 2", "Test Author", "Some Test Content 2", new String[]{"Tag2"});
    	a.createStory(s2);

    	//Test that you can't search for an empty tag
    	ArrayList<Segment> results = a.findByTag("");
    	ArrayList<Segment> expected = new ArrayList<Segment>();
    	assertEquals(results,expected);

    	//Test that searching by "tag2" returns correcy query (both segments)
    	results = a.findByTag("tag2");
    	Set<Segment> s = new HashSet<Segment>(results);
    	Set<Segment> expected_segs = new HashSet<Segment>();
    	expected_segs.add(s1);
    	expected_segs.add(s2);
    	assertEquals(s,expected_segs);
    	//Test that searching by "tag1" returns correct query
    	results = a.findByTag("tag1");
    	assertEquals(s1,results.get(0));
    	
    }


    @Test
    public void findByTitle() throws SQLException{
    	AppController a = new AppController(this.connection);

    	//Create a smallset of stories and insert them into the database
    	// Create a small set of stories and insert them into the database
    	Segment s1 = new Segment("Segment Holiday 1", "Test Author", "Some Testing Content", new String[]{"tag1", "tag2"});
    	a.createStory(s1);

    	Segment s2 = new Segment("Segment Yeti 2", "Test Author", "Some Test Content 2", new String[]{"Tag2"});
    	a.createStory(s2);

    	//Test that you cannot search for an empty title
    	ArrayList<Segment> results = a.findByTitle("");
    	ArrayList<Segment> expected = new ArrayList<Segment>();
    	assertEquals(results,expected);

    	//Test that searching by "Segment" returns both segments
    	results = a.findByTitle("segment");
    	assertEquals(2,results.size());
    	Set<Segment> s = new HashSet<Segment>(results);
    	Set<Segment> expected_segs = new HashSet<Segment>();
    	expected_segs.add(s1);
    	expected_segs.add(s2);
    	//Print out results
    	assertEquals(s,expected_segs);
    	//Test that searching  Yeti returns the target segment
    	results = a.findByTitle("yeti");
    	assertEquals(results.get(0),s2);

    }

    @Test
    public void storeThenGetStory() throws SQLException, IOException, ClassNotFoundException {
    	AppController a = new AppController(connection);
    	Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
        s1.setSegmentId(0);
        int storyid = a.getNextStoryId();
        Story sto = new Story(s1, storyid);
        a.storeStory(sto);
        Story sto2 = a.getStory(storyid);
        assertEquals(sto , sto2);
    }

}