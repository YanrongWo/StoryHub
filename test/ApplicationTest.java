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

import play.db.Database;
import play.db.Databases;
import play.db.evolutions.*;
import java.sql.Connection;

import org.junit.*;
import static org.junit.Assert.*;
import static play.test.Helpers.*;
import static org.junit.Assert.*;
import com.google.common.collect.*;
import java.sql.SQLException;

import models.*;
import controllers.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    @Test
    public void renderTemplate() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        ma.createStory(seg1);
        Result rs = a.index();
        //System.out.println(contentAsString(rs));
    }

    @Test
    public void offset_renderNotFound() {
        Application app = new Application(connection);
        Result result = app.offset(2);
        System.out.println(status(result));
        System.out.println(contentType(result));
        System.out.println(charset(result));
        assertEquals(404, status(result));
        assertEquals("text/html", contentType(result));
        assertEquals("utf-8", charset(result));
        assertTrue(contentAsString(result).contains("Page Not Found"));
    }
    @Test
    public void offset_renderIndex() {
        Application app = new Application(connection);
        Result result = app.offset(0);
        System.out.println(status(result));
        System.out.println(contentType(result));
        System.out.println(charset(result));
        assertEquals(200, status(result));
        assertEquals("text/html", contentType(result));
        assertEquals("utf-8", charset(result));
        assertTrue(contentAsString(result).contains("Homepage"));
    }
}
