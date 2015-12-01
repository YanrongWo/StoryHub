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

import models.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public class SegmentTest{

	@Test
	public void getParentSegIds_valid(){
		Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content", new String[]{"tag1", "tag2"});
		s1.setSegmentId(0);
		Segment s2 = new Segment("Segment 2", "Test Author", "Some Test Content",  new String[]{"tag1", "tag3"});
		s2.setParentSeg(s1);
		s2.setSegmentId(1); 
		ArrayList<Integer> result = s2.getParentSegIds();
		ArrayList<Integer> expected = new ArrayList<Integer>(Arrays.asList(0, 1));
		assertEquals(expected, result);
	}

	@Test
	public void getParentSegIds_noParent(){
		Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content",  new String[]{"tag1", "tag2"});
		s1.setSegmentId(0);
		ArrayList<Integer> result = s1.getParentSegIds();
		ArrayList<Integer> expected = new ArrayList<Integer>(Arrays.asList(0));
		assertEquals(expected, result);
	}

	@Test
	public void displayContent_noReplace_under250(){
		Segment s1 = new Segment("Segment 1", "Test Author", "Some Test Content",  new String[]{"tag1", "tag2"});
		String result = s1.displayContent();
		String expected = "Some Test Content";
		assertEquals(expected, result);
	}

	@Test
	public void displayContent_replace_under250(){
		Segment s1 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>",  new String[]{"tag1", "tag2"});
		String result = s1.displayContent();
		String expected = "Some Test Content";
		assertEquals(expected, result);
	}

	@Test
	public void displayContent_noReplace_over250(){
		Segment s1 = new Segment("Segment 1", "Test Author", "This is my sentence where I need more than 250 characters to make it cut off some of the characters. Please be long enough now, so I can stop typing. Oh no, this is still not long enough, so I need to keep typing some more stuff. I really have nothing to say. I should have probably just copied and pasted something instead of typing it myself. ", new String[]{"tag1", "tag2"});
		String result = s1.displayContent();
		String expected = "This is my sentence where I need more than 250 characters to make it cut off some of the characters. Please be long enough now, so I can stop typing. Oh no, this is still not long enough, so I need to keep typing some more stuff. I really have nothin...";
		assertEquals(expected, result);
	}

	@Test 
	public void displayContent_replace_over250(){
		Segment s1 = new Segment("Segment 1", "Test Author", "</tag>This is my sentence where I need more than 250 characters to make it cut off some of the characters. </tag>Please be long enough now, so I can stop typing. <tag>Oh no, this is still not long enough, so I need to keep typing some more stuff. </tag>I really have nothing to say. I should have probably just copied and pasted something instead of typing it myself. ", new String[]{"tag1", "tag2"});
		String result = s1.displayContent();
		String expected = "This is my sentence where I need more than 250 characters to make it cut off some of the characters. Please be long enough now, so I can stop typing. Oh no, this is still not long enough, so I need to keep typing some more stuff. I really have nothin...";
		assertEquals(expected, result);
	}

	@Test
	public void displayContent_nullContent(){
		Segment s1 = new Segment("Segment 1", "Test Author", null, new String[]{"tag1", "tag2"});
		String result = s1.displayContent();
		String expected = "";
		assertEquals(expected, result);
	}

	@Test
	public void isLeafNode_leaf(){
		Segment s1 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		assertEquals(s1.isLeafNode(), true);
	}

	@Test
	public void isLeafNode_branch(){
		Segment s1 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		Segment s2 = new Segment("Segment 2", "Test Author", "Some Test Content", new String[]{"tag1", "tag3"});
		s1.addChild(s2);
		assertEquals(s1.isLeafNode(), false);
	}

	@Test
	public void equals_valid(){
		Segment s1 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		Segment s2 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		assertEquals(s1, s2);
	}

	@Test
	public void equals_diffTitles(){
		Segment s1 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		Segment s2 = new Segment("Segment 2", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		assertNotEquals(s1, s2);
	}

	@Test
	public void equals_diffAuth(){
		Segment s1 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		Segment s2 = new Segment("Segment 1", "Test Author1", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		assertNotEquals(s1, s2);
	}	

	@Test
	public void equals_diffContent(){
		Segment s1 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content1</tag>", new String[]{"tag1", "tag2"});
		Segment s2 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		assertNotEquals(s1, s2);
	}	

	@Test
	public void equals_diffTags(){
		Segment s1 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		Segment s2 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag3", "tag2"});
		assertNotEquals(s1, s2);
	}	

	@Test
	public void equals_diffSegId(){
		Segment s1 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		s1.setSegmentId(1);
		Segment s2 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		s2.setSegmentId(2);
		assertNotEquals(s1, s2);
	}	

	@Test
	public void equals_diffStoryId(){
		Segment s1 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		s1.setStoryId(1);
		Segment s2 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		s2.setStoryId(2);
		assertNotEquals(s1, s2);
	}	

	@Test
	public void equals_diffChild(){
		Segment s1 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		Segment child = new Segment("Segment 2", "Author", "some content", new String[]{"tag"});
		s1.addChild(child);
		Segment s2 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag3", "tag2"});
		assertNotEquals(s1, s2);
	}	

	@Test 
	public void equals_diffTypes(){
		Segment s1 = new Segment("Segment 1", "Test Author", "<tag>Some Test Content</tag>", new String[]{"tag1", "tag2"});
		Integer i = new Integer(1);
		assertNotEquals(s1, i);
	}
}