package models;

import java.util.ArrayList;
import javax.persistence.*;
import java.io.*;

public class Segment implements Serializable{

    private int id = 0; //default

    private String title;
    private String author;
    private String content;  
    private String[] tags;
    private Segment parentSeg;
    private ArrayList<Segment> childSegs;
    private static final long serialVersionUID = 1L;
    
    public Segment(Segment parentSeg, String title, String author, String content, String[] tags) {
        this.parentSeg = parentSeg;
        this.title = title;
        this.author = author;
        this.content = content;
        this.tags = tags;
        this.childSegs = new ArrayList<Segment>();
        
    }
    
    public ArrayList<Integer> getParentSegIds(){
        ArrayList<Integer> parentSegIds = new ArrayList<Integer>();
        return 0;
    }

    public boolean addChild(Segment child) {
        return childSegs.add(child);
    }

    public void setParentSeg(Segment seg) {
        this.parentSeg = seg;
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

    public String displayContent(){
        if(this.content.length() > 251){
            return this.content.substring(0, 250) + "...";
        }
        return this.content;
    }
    
    public int getSegmentId() {
        return this.id;
    }
    
    public String[] getTags() {
        return this.tags;
    }

    public boolean hasTag(String aTag){
        for(String tag : this.tags){
            if(tag.equals(aTag)){
                return true;
            }
        }
        return false;
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

        // Returns Segment with aId
    public Segment getSegment(int aId){
        if(this.getSegmentId() == aId){
            return this;
        } else {
            for (Segment child : this.getChildSegs()){
                return child.getSegment(aId);
            }
            return null;
        }
    }

    public void setSegmentId(int segId) {
        this.id = segId;
    }
}