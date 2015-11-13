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

	public Segment getRoot(){
		return root;
	}
	
	public ArrayList<Segment> traverse(int segId){
		ArrayList<Segment> allSegments = new ArrayList<Segment>();
		int currentId = this.id;
		Segment currentSeg = this.root;
		while(currentId != segId){
			allSegments.add(currentSeg);
			currentId = this.nextSegId;
			//currentSeg = Segment.findById(nextSegId);
		}
		return allSegments;
	}

	// Returns list of segment ids of leaf nodes that are matching the tag
	public ArrayList<Integer> findTags(String searchWord){
		ArrayList<Integer> leafSegIds = new ArrayList<Integer>();
		Segment currentSeg = this.root;
		while(currentSeg != null){
			if(currentSeg.isLeafNode()){
				for (String tag : currentSeg.getTags()){
					if(tag.equals(searchWord)){
						leafSegIds.add(currentSeg.getSegmentId());
						break;
					}
				}
			}
			//currentSeg = Segment.findById(nextSegId);
		}
		return leafSegIds;
	}

	public int setStoryId(int aId){
		this.id = aId;
		return this.id;
	}

	public int getStoryId(){
		return this.id;
	}

	// Adds Segment seg as a child to Segment with segId
	public boolean fork(Segment seg, int segId){
		int currentId = this.id;
		Segment segToFork = null;
		//Segment segToFork = Segment.findById(segId);
		if(segToFork != null){
			return segToFork.addChild(seg);
		}
		return false;
	}
}