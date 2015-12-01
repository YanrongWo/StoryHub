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
<<<<<<< HEAD
import controllers.*;
import models.*;
import java.util.*;
import java.lang.*;
import java.io.*;
=======
import java.sql.SQLException;

import models.*;
import controllers.*;
>>>>>>> 8f19c04a3be143739f38e6b39ae994f9a2808dd2

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

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(2, a);
    }

    @Test
    public void error(){    
        Application a = new Application();
        Result html= a.error("error");
        assertEquals("text/html", contentType(html));
        assertTrue(contentAsString(html).contains("Error! A story with the same content has already been made!"));
    }
    // @Test
    // public void renderTemplate() {
    //     Content html = views.html.index.render("Your new application is ready.");
    //     assertEquals("text/html", contentType(html));
    //     assertTrue(contentAsString(html).contains("Your new application is ready."));
    // }
    public void renderTemplate() throws SQLException {
        Application a = new Application(connection);
        AppController ma = a.getMyAppController();
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        System.out.println(ma);
        ma.createStory(seg1);
        Result rs = a.index();
        System.out.println(contentAsString(rs));
    }


}
