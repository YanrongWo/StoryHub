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

public class AppControllerTest{
	Database database;
    Connection connection;

    @Before
    public void createDatabase() {
        this.database = Databases.createFrom(
            "test",
            "com.mysql.jdbc.Driver",
            "jdbc:mysql://test.ctsufn7qqcwv.us-west-2.rds.amazonaws.com:3306/test",
            ImmutableMap.of(
                "username", "javathehutt",
                "password", "starwars"
            )
        );
        this.connection = database.getConnection();
    }

    @Test
    public void getNextStoryId() throws SQLException{
        AppController a = new AppController(this.connection);
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        a.createStory(seg1);
        int nextId = a.getNextStoryId();
        assertEquals(nextId, 2);

    }

    @After
    public void shutdownDatabase() {
        database.shutdown();
    }

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(2, a);
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
    	System.out.println(results.get(0).getTitle());
    	assertEquals(s,expected_segs);
    	//Test that searching  Yeti returns the target segment
    	results = a.findByTitle("yeti");
    	assertEquals(results.get(0),s2);

    }
}