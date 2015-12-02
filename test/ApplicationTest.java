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
import java.io.IOException;
import java.lang.ClassNotFoundException;

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
import java.lang.*;
import java.io.*;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/

public class ApplicationTest {
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

    // @Test
    // public void newStorySubmit(){
    //     Application a = new Application(connection);
    //     running(testServer(3333),HtmlUnitDriver.class, new Callback<TestBrowser>(){
    //         public void test(TestBrowser browser){
    //             browser.goTo("http://localhost:3333");
    //         }
    //     });
        

    // }

    @Test
    public void error(){    
        Application a = new Application(connection);
        Result rs= a.error("error");
        assertTrue(contentAsString(rs).contains("Error! A story with the same content has already been made!"));
    }

    @Test
    public void index_title() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        ma.createStory(seg1);
        Result result = a.index();
        assertEquals(200, status(result));
        assertEquals("text/html", contentType(result));
        assertEquals("utf-8", charset(result));
        assertTrue(contentAsString(result).contains("Seg 1"));
    }

    @Test
    public void index_author() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        ma.createStory(seg1);
        Result result = a.index();
        assertEquals(200, status(result));
        assertEquals("text/html", contentType(result));
        assertEquals("utf-8", charset(result));
        assertTrue(contentAsString(result).contains("Auth"));
    }

    @Test
    public void index_content() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        ma.createStory(seg1);
        Result result = a.index();
        assertEquals(200, status(result));
        assertEquals("text/html", contentType(result));
        assertEquals("utf-8", charset(result));
        assertTrue(contentAsString(result).contains("Content"));
    }

    @Test
    public void index_tag() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        ma.createStory(seg1);
        Result result = a.index();
        assertEquals(200, status(result));
        assertEquals("text/html", contentType(result));
        assertEquals("utf-8", charset(result));
        assertTrue(contentAsString(result).contains("hi"));
    }

    @Test
    public void offset_renderNotFound() {
        Application app = new Application(connection);
        Result result = app.offset(2);
        assertEquals(404, status(result));
        assertEquals("text/html", contentType(result));
        assertEquals("utf-8", charset(result));
        assertTrue(contentAsString(result).contains("Page Not Found"));
    }

    @Test
    public void offset_renderIndex() {
        Application app = new Application(connection);
        Result result = app.offset(0);
        assertEquals(200, status(result));
        assertEquals("text/html", contentType(result));
        assertEquals("utf-8", charset(result));
        assertTrue(contentAsString(result).contains("Homepage"));
    }

    @Test
    public void getStoriesByTags_empty() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        // Create a small set of stories and insert them into the database
        Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
        ma.createStory(s1);
        Segment s2 = new Segment("Segment 2", "Test Author", "Some Test Content 2", new String[]{"Tag2"});
        ma.createStory(s2);
        Result result = a.getStoriesByTags("");
        assertTrue(contentAsString(result).contains("Your query cannot be empty"));
    }

    @Test
    public void getStoriesByTags_storyRoot() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        // Create a small set of stories and insert them into the database
        Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
        ma.createStory(s1);
        Segment s2 = new Segment("Segment 2", "Test Author", "Some Test Content 2", new String[]{"Tag2"});
        ma.createStory(s2);
        Result result = a.getStoriesByTags("tag1");
        assertFalse(contentAsString(result).contains("Tag2"));
        assertTrue(contentAsString(result).contains("tag1"));
    }

    @Test
    public void getStoriesByTags_storyLeaf() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        // Create a small set of stories and insert them into the database
        Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
        Story sto = ma.createStory(s1);
        Segment s2 = new Segment("Segment 2", "Test Author", "Some Test Content 2", new String[]{"Tag2"});
        ma.createStory(s2);
        Segment s3 = new Segment("Segment 3", "Test Author", "Some Test Content 3", new String[]{"Tag3"});
        ma.fork(sto, s3, 0);
        Result result = a.getStoriesByTags("Tag3");
        assertFalse(contentAsString(result).contains("Tag2"));
        assertTrue(contentAsString(result).contains("Tag3"));
    }

    @Test
    public void getStoriesByTags_incorrect() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        // Create a small set of stories and insert them into the database
        Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
        Story sto = ma.createStory(s1);
        Segment s2 = new Segment("Segment 2", "Test Author", "Some Test Content 2", new String[]{"Tag3"});
        ma.fork(sto, s2, 0);
        Result result = a.getStoriesByTags("awefewa");
        assertTrue(contentAsString(result).contains("No search results"));
    }

    @Test
    public void getStoriesByTitles_empty() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        // Create a small set of stories and insert them into the database
        Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
        ma.createStory(s1);
        Segment s2 = new Segment("Segment 2", "Test Author", "Some Test Content 2", new String[]{"Tag2"});
        ma.createStory(s2);
        Result result = a.getStoriesByTitles("");
        assertTrue(contentAsString(result).contains("Your query cannot be empty"));
    }

        @Test
    public void getStoriesByTitles_storyRoot() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        // Create a small set of stories and insert them into the database
        Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
        ma.createStory(s1);
        Segment s2 = new Segment("Segment 2", "Test Author", "Some Test Content 2", new String[]{"Tag2"});
        ma.createStory(s2);
        Result result = a.getStoriesByTitles("Segment 1");
        assertFalse(contentAsString(result).contains("Segment 2"));
        assertTrue(contentAsString(result).contains("Segment 1"));
    }

    @Test
    public void getStoriesByTitles_storyLeaf() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        // Create a small set of stories and insert them into the database
        Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
        Story sto = ma.createStory(s1);
        Segment s2 = new Segment("Segment 2", "Test Author", "Some Test Content 2", new String[]{"Tag2"});
        ma.createStory(s2);
        Segment s3 = new Segment("Segment 3", "Test Author", "Some Test Content 3", new String[]{"Tag3"});
        ma.fork(sto, s3, 0);
        Result result = a.getStoriesByTitles("Segment 3");
        assertFalse(contentAsString(result).contains("Segment 1"));
        assertTrue(contentAsString(result).contains("Segment 3"));
    }

    @Test
    public void getStoriesByTitles_incorrect() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        // Create a small set of stories and insert them into the database
        Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
        Story sto = ma.createStory(s1);
        Segment s2 = new Segment("Segment 2", "Test Author", "Some Test Content 2", new String[]{"Tag3"});
        ma.fork(sto, s2, 0);
        Result result = a.getStoriesByTitles("tag3");
        assertTrue(contentAsString(result).contains("No search results"));
    }


}
