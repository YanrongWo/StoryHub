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

import models.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class StoryTest{
	
	@Test
    public void findSegByIdLeaf() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        int segId = sto.findSegById(rootSegId+2).getSegmentId();
        assertEquals(segId, 2);
    }

    @Test
    public void findSegByIdIntermediate() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        int segId = sto.findSegById(rootSegId+1).getSegmentId();
        assertEquals(segId, 1);
    }

    @Test
    public void findSegByIdRoot() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        int segId = sto.findSegById(rootSegId).getSegmentId();
        assertEquals(segId, 0);
    }

    //-1
    @Test
    public void findSegByIdNull() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        Segment found = sto.findSegById(12);
        assertEquals(found, null);
    }

    @Test
    public void findTagsRoot() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        String tag = sto.findTags("ho").get(0).getTags()[1];
        assertEquals(tag, "ho");
    }

    @Test
    public void findTagsIntermediate() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        int numTags = sto.findTags("hum").size();
        assertEquals(numTags, 2);
    }

    @Test
    public void findTagsinAll() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        int numTags = sto.findTags("hi").size();
        assertEquals(numTags, 3);
    }

    @Test
    public void findTagsinAllCaseTest() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        int numTags = sto.findTags("Hi").size();
        assertEquals(numTags, 3);
    }

    //empty string input
    @Test
    public void findTagsEmptyString() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        int size = sto.findTags("").size();
        assertEquals(size, 0);
    }

    //empty string input
    @Test
    public void findTagsNotFound() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        int size = sto.findTags("Hello").size();
        assertEquals(size, 0);
    }

    @Test
    public void findTitlesRoot() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        String title = sto.findTitles("Seg 1").get(0).getTitle();
        assertEquals(title, "Seg 1");
    }

    @Test
    public void findTitlesIntermediate() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        String title = sto.findTitles("Seg 3").get(0).getTitle();
        assertEquals(title, "Seg 3");
    }

    @Test
    public void findTitlesCaseContains() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        int numTitles = sto.findTitles("seg").size();
        assertEquals(numTitles, 3);
    }


    //empty string
    @Test
    public void findTitlesEmpty() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        int size = sto.findTitles("").size();
        assertEquals(size, 0);
    }

    //not present
    @Test
    public void findTitlesNotFound() {
    	String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        int size = sto.findTitles("Coolit").size();
        assertEquals(size, 0);
    }


    @Test
    public void equals_valid() {
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        String[] tags3 = {"hi", "hum", "ha"};
        Segment seg3 = new Segment("Seg 3", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        sto.addSegment(seg3, rootSegId+1);
        Story sto2 = new Story(seg1, 1);
        rootSegId = sto2.getRoot().getSegmentId();
        sto2.addSegment(seg2, rootSegId);
        sto2.addSegment(seg3, rootSegId+1);
        assertEquals(sto, sto2);
    }

    @Test
    public void equals_null() {
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        Story sto = new Story(seg1, 1);
        Story sto2 = null;
        assertNotEquals(sto, sto2);
    }

    @Test
    public void equals_diffClasses(){
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        Story sto = new Story(seg1, 1);
        Integer i = new Integer(1);
        assertNotEquals(sto, i);
    }

    @Test
    public void equals_diffRoots(){
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);  
        Story sto = new Story(seg1, 1); 
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags1);  
        Story sto2 = new Story(seg2, 1);
        assertNotEquals(sto, sto2);
    }

    @Test
    public void equals_diffIds(){
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);  
        Story sto = new Story(seg1, 1); 
        Story sto2 = new Story(seg1, 2);
        assertNotEquals(sto, sto2);
    }

    @Test
    public void equals_diffClosed(){
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);  
        Story sto = new Story(seg1, 1); 
        sto.setClosed();
        Story sto2 = new Story(seg1, 1);
        assertNotEquals(sto, sto2);
    }

    @Test
    public void equals_diffSegment(){
        String[] tags1 = {"hi", "ho"};
        Segment seg1 = new Segment("Seg 1", "Auth", "Content", tags1);
        String[] tags2 = {"hi", "hum"};
        Segment seg2 = new Segment("Seg 2", "Auth", "Content", tags2);
        Story sto = new Story(seg1, 1);
        int rootSegId = sto.getRoot().getSegmentId();
        sto.addSegment(seg2, rootSegId);
        Story sto2 = new Story(seg1, 1);
        assertNotEquals(sto, sto2);
    }
}