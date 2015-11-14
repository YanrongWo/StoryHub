package models;

public class StorySeg{
    
    private int storyInt;
    private int segInt;
    
    public StorySeg(int storyInt, int segInt) {
        this.storyInt = storyInt;
        this.segInt = segInt;
    }
    
    public int getStoryInt() {
        return this.storyInt;
    }
    
    public int getSegInt() {
        return this.segInt;
    }
    
    @Override public String toString(){
        return "StoryInt:"+this.storyInt+" SegInt"+this.segInt;
    }
}