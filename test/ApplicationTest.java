import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

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
import java.util.*;
import java.lang.*;
import java.io.*;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import play.mvc.Http.RequestBuilder;
import play.test.Helpers;
import play.test.*;

import org.junit.*;
import static org.junit.Assert.*;
import static play.test.Helpers.*;
import static org.junit.Assert.*;
import com.google.common.collect.*;


import play.test.*;
import static play.test.Helpers.*;
import play.libs.ws.*;
import play.libs.*;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.test.FakeRequest;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/

public class ApplicationTest{
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

    @Test 
    public void newStorySubmit_successful(){
        running(fakeApplication(), new Runnable() {
            public void run() {
                Map<String, String> cookies = ImmutableMap.of("name", "Test Name");
                Map<String, String> formData = ImmutableMap.of("title", "TestTitleSuccess","content","Test Content Success1323","tags","testing test tested");
                
                RequestBuilder rb = Helpers.fakeRequest("POST", "/NewStory").session(cookies).bodyForm(formData);
                Result result = Helpers.route(rb);
                String segmentInfoString = Helpers.contentAsString(result);
                assertEquals(status(result),200);
                assertTrue(segmentInfoString.contains("0") && segmentInfoString.contains(","));
                           
            }
        });
    }


    @Test
    public void newStorySubmit_duplicateStory(){
        running(fakeApplication(), new Runnable() {
            public void run() {

                //Log in
                Map<String, String> cookies = ImmutableMap.of("name", "Test Name");

                //Create a new story
                Map<String, String> formData = ImmutableMap.of("title", "TestTitle","content","Test Content","tags","testing test tested");
                RequestBuilder rb1 = Helpers.fakeRequest("POST", "/NewStory").session(cookies).bodyForm(formData);
                Result result1 = Helpers.route(rb1);

                //Create story with same data: should return error message
                RequestBuilder rb2 = Helpers.fakeRequest("POST", "/NewStory").session(cookies).bodyForm(formData);
                Result result2 = Helpers.route(rb2);
                String html = Helpers.contentAsString(result2);
                System.out.println(html);
                assertEquals(status(result2),200);
                assertTrue(html.contains("not found"));            
            }
        });
    }

    @Test
    public void newStorySubmit_notLoggedIn(){
        running(fakeApplication(), new Runnable() {
            public void run() {
                Map<String, String> formData = ImmutableMap.of("title", "TestTitle","content","Test Content","tags","testing test tested");
                RequestBuilder rb = Helpers.fakeRequest("POST", "/NewStory").bodyForm(formData);
                Result result = Helpers.route(rb);
                assertEquals(status(result),400);
                assertTrue(Helpers.contentAsString(Helpers.route(rb)).contains("You must be logged in to add a story"));            
            }
        });
    }


    @Test
    public void error(){    
        Application a = new Application(connection);
        Result rs= a.error("error");
        assertTrue(contentAsString(rs).contains("Error! A story with the same content has already been made!"));
    }

    @Test
    public void renderTemplate() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        ma.createStory(seg1);
        Result rs = a.index();
    }

    @Test 
    public void facebookName(){
        running(fakeApplication(), new Runnable() {
            public void run() {
                Map<String, String> formData = ImmutableMap.of("name", "Test Name");
                RequestBuilder rb = Helpers.fakeRequest("POST", "/FacebookName").bodyForm(formData);
                Result result = Helpers.route(rb);
                assertEquals(result.session().get("name"), "Test Name");            
            }});
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
}
