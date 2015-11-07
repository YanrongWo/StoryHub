package models;

import java.lang.StringBuffer;
import java.util.ArrayList;
import play.db.ebean.*;
import javax.persistence.*;

@Entity
public class Segment extends Model{
	@Id
	private int id;

	public static Finder<Integer, Segment> find = new Finder<Integer, Segment>(Integer.class, Segment.class);

	private String title;
	private String author;
	private StringBuffer content;  
	private ArrayList<String> tags;
	private Segment parentSeg;
	private ArrayList<Segment> childSegs;
	
	public Segment(Segment parentSeg, String title, String author, StringBuffer content, int id, ArrayList<String> tags) {
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
    
    public StringBuffer getContent() {
        return this.content;
    }
    
    public int getSegmentId() {
        return this.id;
    }
    
    public ArrayList<String> getTags() {
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
    
    public static Segment findById(int id) {
		return find.ref(id);
	}
}