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

import models.*;
import controllers.*;

public class AppControllerTest{
	Database database;
    Connection connection;

    @Before
    public void createDatabase() {
        database = Databases.createFrom(
            "test",
            "com.mysql.jdbc.Driver",
            "jdbc:mysql://test.ctsufn7qqcwv.us-west-2.rds.amazonaws.com/test",
            ImmutableMap.of(
                "username", "javathehutt",
                "password", "starwars"
            )
        );
        connection = database.getConnection();
    }

    @Test
    public void getNextStoryId() throws SQLException{
        AppController a = new AppController(this.connection);
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        a.createStory(seg1);
        int nextId = a.getNextStoryId();
        assertEquals(nextId, 2);
        String DELETE_COMMAND = "DELETE FROM stories";
        PreparedStatement pstmt = this.connection.prepareStatement(DELETE_COMMAND);
        pstmt.executeQuery();
    }

    @After
    public void shutdownDatabase() {
        database.shutdown();
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
}