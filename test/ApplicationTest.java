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
    public void renderTemplate() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        ma.createStory(seg1);
        Result rs = a.index();
    }

    @Test 
    public void facebookName_withName(){
        running(fakeApplication(), new Runnable() {
            public void run() {
                Map<String, String> formData = ImmutableMap.of("name", "Test Name");
                RequestBuilder rb = Helpers.fakeRequest("POST", "/FacebookName").bodyForm(formData);
                Result result = Helpers.route(rb);
                assertEquals(result.session().get("name"), "Test Name");
                assertEquals(200, status(result));
            }
        });
    }


    @Test 
    public void facebookName_noName(){
        running(fakeApplication(), new Runnable() {
            public void run() {
                RequestBuilder rb = Helpers.fakeRequest("POST", "/FacebookName");
                Result result = Helpers.route(rb);
                assertEquals(400, status(result));
                assertEquals(result.session().get("name"), null);
            }
        });
    }

    @Test
    public void noFacebookName_withName(){
        running(fakeApplication(), new Runnable() {
            public void run() {
                Map<String, String> cookies = ImmutableMap.of("name", "Test Name");
                RequestBuilder rb = Helpers.fakeRequest("POST", "/NoFacebookName").session(cookies);
                Result result = Helpers.route(rb);
                assertEquals(result.session().get("name"), null);
                assertEquals(200, status(result));
            }
        });
    }

    @Test
    public void noFacebookName_noName(){
        running(fakeApplication(), new Runnable() {
            public void run() {
                RequestBuilder rb = Helpers.fakeRequest("POST", "/NoFacebookName");
                Result result = Helpers.route(rb);
                assertEquals(result.session().get("name"), null);
                assertEquals(200, status(result));
            }
        });
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
    public void newStory_notLoggedIn(){
        running(fakeApplication(), new Runnable() {
            public void run() {
                RequestBuilder rb = Helpers.fakeRequest("GET", "/NewStory");
                Result result = Helpers.route(rb);
                assertEquals(400, status(result));
                assertEquals("text/html", contentType(result));
                assertEquals("utf-8", charset(result));
                assertTrue(contentAsString(result).contains("You must be logged in to add a story"));
            }});
    }

    @Test
    public void newStory_loggedIn(){
        running(fakeApplication(), new Runnable() {
            public void run() {
                Map<String, String> cookies = ImmutableMap.of("name", "Test Name");
                RequestBuilder rb = Helpers.fakeRequest("GET", "/NewStory").session(cookies);;
                Result result = Helpers.route(rb);
                assertEquals(200, status(result));
                assertEquals("text/html", contentType(result));
                assertEquals("utf-8", charset(result));
                assertTrue(contentAsString(result).contains("New Story"));
            }});
    }
}
