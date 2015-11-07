package models;

import java.lang.StringBuffer;
import java.util.ArrayList;

public class AppController{

    private int max; //max stories on front page
    private ArrayList<Story> stories;
    private int storyIndex;
    private String currentUser;
    
	
    public AppController() {
        this.max = 10;
        //this.stornew ArrayList<Story>();
        this.currentUser = null;
        this.storyIndex = 0;
        
	}
    
    public Story createStory(Segment seg) {
        //new Story(Segment )
        Story newOne = new Story(seg, 0);
        //add null to table and gets last inserted id
        int sId = 0;
        newOne.setStoryId(sId);
        stories.add(newOne);
        //save the story to database
        storeStory(newOne, sId);
        return newOne;
    }
    
    public boolean fork(Story sto, Segment seg, int segmentId) {
        if (sto.fork(seg, segmentId)) {
            storeStory(sto, sto.getStoryId());
            return true;
        }
        return false;
        
    }
    
    public ArrayList<StorySeg> find(String search) {
        ArrayList<StorySeg> results = new ArrayList<StorySeg>();
        for(int i=0; i<stories.size(); i++) {
            ArrayList<Integer> hits = stories.get(i).findTags(search);
            for (int j=0; j<hits.size(); j++) {
                results.add(new StorySeg(stories.get(i).getStoryId(), hits.get(j)));
            }
        }
        return results;
    }
    
    public Story getStory(int storyId) {
        //do table query
        return null;
    }
    
    public boolean storeStory(Story sto, int storyId) {
        //store story into table
        return false;
    }
    
    public void setCurrentUser(String user) {
        this.currentUser = user;
    }

    public String getCurrentUser()
    {
        return this.currentUser;
    }
}