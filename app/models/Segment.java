package models;

import java.lang.StringBuffer;
import java.util.ArrayList;

public class Segment{

	private String title;
	private String author;
	private StringBuffer content;  
	private int id;
	private ArrayList<String> tags;
	private Segment parentSeg;
	private ArrayList<Segment> childSegs;
	
	public Segment(Segment parent) {
		this.parentSeg = parent;
	}
	
	public boolean addChild(Segment child) {
		return childSegs.add(child);
	}
	
}