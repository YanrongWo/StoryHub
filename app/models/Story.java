package models;
import java.util.*;
import javax.persistence.*;
import java.io.*;

/**
 * Story represents one story, with Segments in a tree structure.
 * @author      Java the Hutt
 * @version     0.1
 */
public class Story implements Serializable{

	//story id
	private int id;

	private Segment root;
	private int nextSegId = 0;
	private boolean closed = false;

	//serialVersionUID used for serialization
	private static final long serialVersionUID = 1L;

	public Story(Segment aRoot, int aId){
		this.id = aId;
		aRoot.setStoryId(this.id);
		aRoot.setSegmentId(this.nextSegId);
		this.root = aRoot;
		this.nextSegId++;
	}

   /**
	* Helper function to recursively search for segment matching segId
	* @param Segment id used to search
	* @param Segment to search with
	* @return Segment found that matched segId
	*/
	private Segment recursiveSearchSegId(int segId, Segment seg){
		if (seg.getSegmentId()==segId)
		    return seg;
		ArrayList<Segment> children = seg.getChildSegs(); 
		Segment result = null;
		for (int i = 0; result == null && i < children.size(); i++) {         
		    result = recursiveSearchSegId(segId, children.get(i));
		}
		return result;
	}

   /**
	* Function to search for segment matching segId.
	* Uses helper recursiveSearchSegId function
	* @param Segment id used to search
	* @return Segment found that matched segId else null
	*/
	public Segment findSegById(int segId) {
		return this.recursiveSearchSegId(segId, this.root);
	}

   /**
	* Function to return an arraylist of segments with the searched tag
	* Uses helper recursiveSearchSegTag
	* @param String tag to search
	* @return ArrayList of Segment that match the tag
	*/
	public ArrayList<Segment> findTags(String searchWord){
		ArrayList<Segment> segments = new ArrayList<Segment>();
		if (searchWord!=null || searchWord.length()>0)
			recurseSearchSegTag(segments, this.root, searchWord);
		return segments;
	}

   /**
	* Helper function to recursively search for segment matching tag
	* @param ArrayList of segments that have already been matched with tag
	* @param Segment to search with
	* @param tag used to search
	*/
	private void recurseSearchSegTag(ArrayList<Segment> segments, Segment seg, String searchWord) {
		for (String tag : seg.getTags()){
			if(tag.toLowerCase().equals(searchWord.toLowerCase())){
				segments.add(seg);
				break;
			}
		}
		if (seg.isLeafNode()) {
			return;
		} else {
			ArrayList<Segment> children = seg.getChildSegs();
			for (int i = 0; i < children.size(); i++) {         
			    recurseSearchSegTag(segments, children.get(i), searchWord);
			}
		}
	}

   /**
	* Function to return an arraylist of segments with the searched title keyword
	* Uses helper recursiveSearchSegTitles
	* @param String keyword to search in title
	* @return ArrayList of Segment that match the title keyword
	*/
	public ArrayList <Segment> findTitles(String searchWord){
		ArrayList<Segment> segments = new ArrayList<Segment>();
		if(searchWord.length()>0)
			recurseSearchSegTitle(segments,this.root,searchWord);
		return segments;
	}


   /**
	* Helper function to recursively search for segment matching title keyword
	* @param ArrayList of segments that have already been matched with title keyword
	* @param Segment to search with
	* @param title keyword used to search
	*/
	private void recurseSearchSegTitle(ArrayList<Segment> segments, Segment seg, String searchWord){
		if(seg.getTitle().toLowerCase().contains(searchWord.toLowerCase())){
			segments.add(seg);
		}

		if(seg.isLeafNode()){
			return;
		} else {
			ArrayList<Segment> children = seg.getChildSegs();
			for ( int i = 0; i < children.size();i++){
				recurseSearchSegTitle(segments, children.get(i),searchWord);
			}
		}
	}

	//accessors and mutators

	public int setStoryId(int aId){
		this.id = aId;
		return this.id;
	}

	public int getStoryId(){
		return this.id;
	}

	public Segment getRoot(){
		return this.root;
	}

	public boolean isClosed(){
		return this.closed;
	}

	public boolean setClosed(){
		this.closed = true;
		return this.closed;
	}

   /**
	* Adds a segment seg as a child of the segment with segId
	* @param Segment to be added
	* @param int segId of the segment to be added to
	* @return true if add child was successful
	*/
	public boolean addSegment(Segment seg, int segId){
		Segment segToFork = this.findSegById(segId);
		if(segToFork != null){
			seg.setStoryId(this.id);
			seg.setParentSeg(segToFork);
			seg.setSegmentId(this.nextSegId);
			this.nextSegId++;
			return segToFork.addChild(seg);
		}
		return false;
	}
}