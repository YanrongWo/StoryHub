package models;
import java.util.*;
import javax.persistence.*;
import java.io.*;

public class Story implements Serializable{
	private int id;

	private Segment root;
	private int nextSegId;

	public Story(Segment aRoot, int aId){
		this.root = aRoot;
		this.id = aId;
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

	public Segment findSegId(int segId) {
		return this.recursiveSearchSegId(segId, this.root);
	}

	// public ArrayList<Segment> traverse(int segId){
	// 	ArrayList<Segment> allSegments = new ArrayList<Segment>();
	// 	int currentId = this.id;
	// 	Segment currentSeg = this.root;
	// 	while(currentId != segId){
	// 		allSegments.add(currentSeg);
	// 		currentId = this.nextSegId;
	// 	}
	// 	return allSegments;
	// }

	// Returns list of segment ids of leaf nodes that are matching the tag
	public ArrayList<Integer> findTags(String searchWord){
		ArrayList<Integer> segIds = new ArrayList<Integer>();
		recurseSearchSegTag(segIds, this.root, searchWord);
		return segIds;
	}

	private void recurseSearchSegTag(ArrayList<Integer> segIds, Segment seg, String searchWord) {
		for (String tag : seg.getTags()){
			if(tag.equals(searchWord)){
				segIds.add(seg.getSegmentId());
				break;
			}
		}
		if (seg.isLeafNode()) {
			return;
		} else {
			ArrayList<Segment> children = seg.getChildSegs();
			for (int i = 0; i < children.size(); i++) {         
			    recurseSearchSegTag(segIds, children.get(i), searchWord);
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
		Segment segToFork = this.findSegId(segId);
		return addSegment(seg, segToFork.getParentSeg().getSegmentId());
	}

	// Adds Segment seg as a child to Segment with segId
	public boolean addSegment(Segment seg, int segId){
		Segment segToFork = this.findSegId(segId);
		if(segToFork != null){
			seg.setParentSeg(segToFork);
			return segToFork.addChild(seg);
		}
		return false;
	}
}