package models;
import java.util.*;
import javax.persistence.*;
import java.io.*;

public class Story implements Serializable{
	private int id;

	private Segment root;
	private int nextSegId = 0;
	private static final long serialVersionUID = 1L;

	public Story(Segment aRoot, int aId){
		this.id = aId;
		
		aRoot.setStoryId(this.id);
		aRoot.setSegmentId(this.nextSegId);
		this.root = aRoot;
		this.nextSegId++;
	}

	//returns the segment with segId
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

	public Segment findSegById(int segId) {
		return this.recursiveSearchSegId(segId, this.root);
	}

	public boolean hasTag(String aTag){
		return recurseHasTag(aTag, this.root);
	}

	public boolean recurseHasTag(String aTag, Segment seg) {
		if (seg.hasTag(aTag)) {
			return true;
		} else {
			ArrayList<Segment> children = seg.getChildSegs();
			for (int i = 0; i < children.size(); i++) {         
		    	recurseHasTag(aTag, children.get(i));
			}
		}
		return false;
	}

	// Returns list of segment ids of leaf nodes that are matching the tag
	public ArrayList<Segment> findTags(String searchWord){
		ArrayList<Segment> segments = new ArrayList<Segment>();
		recurseSearchSegTag(segments, this.root, searchWord);
		return segments;
	}

	private void recurseSearchSegTag(ArrayList<Segment> segments, Segment seg, String searchWord) {
		for (String tag : seg.getTags()){
			if(tag.equals(searchWord)){
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

	//returns arraylist of parents of the given segment seg
	public ArrayList<Segment> getParents(Segment seg) {
		ArrayList<Segment> parents = new ArrayList<Segment>();
		recursiveParents(parents, seg);
		return parents;
	}

	private void recursiveParents(ArrayList<Segment> parents, Segment seg)
	{
		Segment parent = seg.getParentSeg();
		if (parent != null) {
			parents.add(parent);
			recursiveParents(parents, parent);
		} else {
			return;
		}
	}

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

	public boolean fork(Segment seg, int segId) {
		Segment segToFork = this.findSegById(segId);
		return addSegment(seg, segToFork.getParentSeg().getSegmentId());
	}

	// Adds Segment seg as a child to Segment with segId
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