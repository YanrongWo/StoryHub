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



import play.Configuration;
import com.typesafe.config.Config;
import com.typesafe.config.*;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/

public class ApplicationTest{
    static Database database;
    static Connection connection;
    private Configuration additionalConfigurations;

    @BeforeClass
    public static void createDatabase() throws SQLException{
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
        Config additionalConfig = ConfigFactory.parseFile(new File("conf/application.test.conf"));
        additionalConfigurations = new Configuration(additionalConfig);
    }

    @After
    public void dropTable() throws SQLException{
        String DROP_COMMAND = "drop table stories";
        PreparedStatement pstmt = connection.prepareStatement(DROP_COMMAND);
        pstmt.executeUpdate();
    }


    @Test 
    public void newStorySubmit_successful(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
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
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
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
                assertEquals(status(result2),200);
                assertTrue(html.contains("not found"));            
            }
        });
    }

    @Test
    public void newStorySubmit_notLoggedIn(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
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
    public void error_NewStoryError(){    
        Application a = new Application(connection);
        Result rs= a.error("NewStoryError");
        assertTrue(contentAsString(rs).contains("Error! A story with the same content has already been made!"));
    }

    @Test
    public void error_StoryClosed(){    
        Application a = new Application(connection);
        Result rs= a.error("StoryClosed");
        assertTrue(contentAsString(rs).contains("Error! This story has been closed. Please contibute to another story"));
    }

    @Test 
    public void facebookName_withName(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
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
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
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
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
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
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
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
    public void index() throws SQLException {
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
        assertTrue(contentAsString(result).contains("Auth"));
        assertTrue(contentAsString(result).contains("Content"));
        assertTrue(contentAsString(result).contains("hi"));
    }

    public void newFork_valid(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                Map<String, String> formData = ImmutableMap.of("title", "TestTitle","content","Some Test Content","tags","testing test tested");
                Map<String, String> cookies = ImmutableMap.of("name", "Test Name");
                RequestBuilder rb = Helpers.fakeRequest("POST", "/NewStory").session(cookies).bodyForm(formData);
                Result result = Helpers.route(rb);

                RequestBuilder rb2 = Helpers.fakeRequest("GET", "/Story/1/0/NewSegment").session(cookies);
                Result result2 = Helpers.route(rb2);
                assertEquals(200, status(result2));
                assertTrue(contentAsString(result2).contains("New Segment"));
                assertTrue(contentAsString(result2).contains("Story Content"));
            }
        });
    }

    @Test
    public void newFork_notLoggedIn(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                Map<String, String> formData = ImmutableMap.of("title", "TestTitle","content","Some Test Content","tags","testing test tested");
                Map<String, String> cookies = ImmutableMap.of("name", "Test Name");
                RequestBuilder rb = Helpers.fakeRequest("POST", "/NewStory").session(cookies).bodyForm(formData);
                Result result = Helpers.route(rb);

                RequestBuilder rb2 = Helpers.fakeRequest("GET", "/Story/1/0/NewSegment");
                Result result2 = Helpers.route(rb2);
                assertEquals(400, status(result2));
                assertTrue(contentAsString(result2).contains("You must be logged in"));
            }
        });
    }

    @Test 
    public void newForkSubmit_valid(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                Map<String, String> formData = ImmutableMap.of("title", "TestTitle","content","Some Test Content","tags","testing test tested");
                Map<String, String> cookies = ImmutableMap.of("name", "Test Name");
                RequestBuilder rb = Helpers.fakeRequest("POST", "/NewStory").session(cookies).bodyForm(formData);
                Result result = Helpers.route(rb);
                
                formData = ImmutableMap.of("title", "TestTitle","content","Some More Test Content","tags","testing test tested");
                RequestBuilder rb2 = Helpers.fakeRequest("POST", "/Story/1/0/NewSegment").session(cookies).bodyForm(formData);
                Result result2 = Helpers.route(rb2);
                assertEquals(200, status(result2));
                assertTrue(contentAsString(result2).contains("1,1"));
            };  
        });
    }

    @Test 
    public void newForkSubmit_notLoggedIn(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                Map<String, String> formData = ImmutableMap.of("title", "TestTitle","content","Some Test Content","tags","testing test tested");
                Map<String, String> cookies = ImmutableMap.of("name", "Test Name");
                RequestBuilder rb = Helpers.fakeRequest("POST", "/NewStory").session(cookies).bodyForm(formData);
                Result result = Helpers.route(rb);
                formData = ImmutableMap.of("title", "TestTitle","content","Some More Test Content","tags","testing test tested");
                RequestBuilder rb2 = Helpers.fakeRequest("POST", "/Story/1/0/NewSegment").bodyForm(formData);
                Result result2 = Helpers.route(rb2);
                assertEquals(400, status(result2));
                assertTrue(contentAsString(result2).contains("You must be logged in"));
            };  
        });
    }

    @Test
    public void newForkSubmit_invalidStory(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                Map<String, String> formData = ImmutableMap.of("title", "TestTitle","content","Some Test Content","tags","testing test tested");
                Map<String, String> cookies = ImmutableMap.of("name", "Test Name");
                RequestBuilder rb2 = Helpers.fakeRequest("POST", "/Story/2/0/NewSegment").session(cookies).bodyForm(formData);
                Result result2 = Helpers.route(rb2);
                assertEquals(404, status(result2));
                assertTrue(contentAsString(result2).contains("Page Not Found"));
            }
        });
    }

    @Test
    public void newStory_notLoggedIn(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
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
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                Map<String, String> cookies = ImmutableMap.of("name", "Test Name");
                RequestBuilder rb = Helpers.fakeRequest("GET", "/NewStory").session(cookies);
                Result result = Helpers.route(rb);
                assertEquals(200, status(result));
                assertEquals("text/html", contentType(result));
                assertEquals("utf-8", charset(result));
                assertTrue(contentAsString(result).contains("New Story"));
            }});

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


    @Test
    public void getSegmentInfo_noFormData(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                RequestBuilder rb = Helpers.fakeRequest("POST", "/AddSegment");
                Result result = Helpers.route(rb);
                assertEquals(400, status(result));
            }
        });
    }

    @Test 
    public void story_notFound(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                Map<String, String> cookies = ImmutableMap.of("name", "Test Name");
                RequestBuilder rb = Helpers.fakeRequest("GET", "/Story/1/0").session(cookies);
                Result result = Helpers.route(rb);
                assertTrue(contentAsString(result).contains("Page Not Found"));
                assertEquals(404, status(result));
            };  
        });
    }

    @Test 
    public void getSegmentInfo_valid() throws SQLException{
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
        Story sto = ma.createStory(s1);

        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                Map<String, String> formData = ImmutableMap.of("storyId", "1", "segmentId", "0");
                RequestBuilder rb = Helpers.fakeRequest("POST", "/AddSegment").bodyForm(formData);
                Result result = Helpers.route(rb);
                assertEquals(200, status(result));
                assertTrue(contentAsString(result).contains("Segment 1"));
                assertTrue(contentAsString(result).contains("Test Author"));
                assertTrue(contentAsString(result).contains("Some Test Content"));
                assertTrue(contentAsString(result).contains("tag1"));
                assertTrue(contentAsString(result).contains("tag2"));
                assertTrue(contentAsString(result).contains("-1"));
                assertTrue(contentAsString(result).contains("Segment 1"));
                assertTrue(contentAsString(result).contains("childrenid\":[]"));
                assertTrue(contentAsString(result).contains("childrentitle\":[]"));
            }
        });
    }

    public void story_segNotFound(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                Map<String, String> formData = ImmutableMap.of("title", "TestTitle","content","Test Content4538","tags","testing test tested");
                Map<String, String> cookies = ImmutableMap.of("name", "Test Name");
                RequestBuilder rb = Helpers.fakeRequest("POST", "/NewStory").session(cookies).bodyForm(formData);
                Result result = Helpers.route(rb);
                RequestBuilder rb2 = Helpers.fakeRequest("GET", "/Story/1/1").session(cookies);
                Result result2 = Helpers.route(rb2);
                assertTrue(contentAsString(result2).contains("Page Not Found"));
                assertEquals(404, status(result2));
            };  
        });
    }

    @Test 
    public void story_valid(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                Map<String, String> formData = ImmutableMap.of("title", "TestTitle","content","Test Content4538","tags","testing test tested");
                Map<String, String> cookies = ImmutableMap.of("name", "Test Name");
                RequestBuilder rb = Helpers.fakeRequest("POST", "/NewStory").session(cookies).bodyForm(formData);
                Result result = Helpers.route(rb);
                RequestBuilder rb2 = Helpers.fakeRequest("GET", "/Story/1/0").session(cookies);
                Result result2 = Helpers.route(rb2);
                assertFalse(contentAsString(result2).contains("Page Not Found"));
                assertEquals(200, status(result2));
            };  
        });
    }

    @Test
    public void txt_valid(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                Map<String, String> formData = ImmutableMap.of("content", "Some random ass string.");
                RequestBuilder rb = Helpers.fakeRequest("POST", "/ExportToTxt").bodyForm(formData);
                Result result = Helpers.route(rb);
                assertTrue(contentAsString(result).contains("Some random ass string."));
                assertEquals(200, status(result));

            };
        });    
    }

    @Test
    public void txt_noForm(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                Map<String, String> formData = new HashMap<String, String>();
                RequestBuilder rb = Helpers.fakeRequest("POST", "/ExportToTxt").bodyForm(formData);
                Result result = Helpers.route(rb);
                assertTrue(contentAsString(result).contains("Form Error"));
                assertEquals(400, status(result));
            };
        });    
    }

    @Test
    public void txt_noContent(){
        running(fakeApplication(additionalConfigurations.asMap()), new Runnable() {
            public void run() {
                Map<String, String> formData = ImmutableMap.of("not_content", "Some random ass string.");
                RequestBuilder rb = Helpers.fakeRequest("POST", "/ExportToTxt").bodyForm(formData);
                Result result = Helpers.route(rb);
                assertTrue(contentAsString(result).contains("Missing content for txt file"));
                assertEquals(400, status(result));
            };
        });    
    }
}
