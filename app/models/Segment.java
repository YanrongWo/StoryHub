package models;

import java.util.ArrayList;
import javax.persistence.*;

public class Segment{

	private int id;

	private String title;
	private String author;
	private String content;  
	private String[] tags;
	private Segment parentSeg;
	private ArrayList<Segment> childSegs;
	
	public Segment(Segment parentSeg, String title, String author, String content, int id, String[] tags) {
		this.parentSeg = parentSeg;
        this.title = title;
        this.author = author;
        this.content = content;
        this.id = id;
        this.tags = tags;
        this.childSegs = new ArrayList<Segment>();
        
	}
	
	public boolean addChild(Segment child) {
		return childSegs.add(child);
	}
	
    public Segment getParent() {
        return this.parentSeg;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public int getSegmentId() {
        return this.id;
    }
    
    public String[] getTags() {
        return this.tags;
    }
    
    public Segment getParentSeg() {
        return this.parentSeg;
    }
    
    public ArrayList<Segment> getChildSegs() {
        return this.childSegs;
    }

    public boolean isLeafNode(){
    	return this.childSegs.isEmpty();
    }
    
}