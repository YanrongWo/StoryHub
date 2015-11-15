package controllers;
import play.*;
import play.mvc.*;
import models.*;
import play.data.DynamicForm;
import play.data.Form;
import views.html.*;
import java.sql.SQLException;
import java.util.*;
import java.io.IOException;
import play.twirl.api.Html;

public class Application extends Controller {

    AppController myAppController = new AppController();

    public Result index() throws SQLException, IOException, ClassNotFoundException{
        // if(myAppController.getStories().size()==0) {
        //     myAppController.loadAll();
        // }

        //Get all stories
        ArrayList<Story> storyList = myAppController.getFrontPageStories(0);
        int interval = myAppController.getMax();
        System.out.println("interval");
        System.out.println(interval);
        int storySize = myAppController.getStoryListSize();  

        return ok(index.render("Homepage", storyList, 0, storySize, interval));
    }

    public Result offset(int i) throws SQLException, IOException, ClassNotFoundException{
        // if(myAppController.getStories().size()==0) {
        //     myAppController.loadAll();
        // }
        int storySize = myAppController.getStoryListSize();
        if(i>storySize) {
            String message = "Your offset is larger than the size of the stories library: " + Integer.toString(storySize);
            return notFound(views.html.error.render("Page Not Found"));
        }
        ArrayList<Story> storyList = myAppController.getFrontPageStories(i);   
        return ok(index.render("Homepage", storyList, i, storySize, myAppController.getMax()));
    }

    public Result facebookName() {
    	DynamicForm form = Form.form().bindFromRequest();
    	if (form.data().size() != 0)
    	{
            if (session("name") == null){ 
                session("name", form.get("name"));
                return ok("changed");
            }
    		return ok("");
    	}
    	return badRequest();
    }

    public Result noFacebookName() {
        if (session("name") != null)
        {
            session().clear();
            return ok("changed");
        }
        return ok("");
    }

    public Result newStory(){
    	return ok(newStory.render("New Story", "newStory"));
    }

    /* create a new story from form data */
    public Result newStorySubmit() throws SQLException, IOException, ClassNotFoundException{
        DynamicForm form = Form.form().bindFromRequest();
        if (form.data().size() == 0) {
            return badRequest("Form Error");
        } else {
            String title = form.get("title").replaceAll("\"", "\'");
            System.out.println(title);
            String content = form.get("content").replaceAll("\"", "\'");
            myAppController.loadAll();
            ArrayList<Story> allStories = myAppController.getStories();
            for (int i = 0; i < allStories.size(); i++){
                if (allStories.get(i).getRoot().getContent().equals(content)){
                    int id = allStories.get(i).getStoryId();
                    String message = " <a href=\"/Story/" + id 
                        + "/0\"> Error! A story with the same content has already been made! </a>";
                    //return notFound(views.html.error.render("Page Not Found"));
                    return ok("not found");
                }
            }
            System.out.println(content);
            String tagsRaw = form.get("tags").replaceAll("\"", "\'");
            String[] tags = tagsRaw.replaceAll("#", "").split(" ");
            Set<String> setTags = new HashSet<String>(Arrays.asList(tags));
            setTags.remove("");
            setTags.remove(" ");
            String[] uniqueTags = setTags.toArray(new String[setTags.size()]);
            System.out.println(uniqueTags);
            System.out.println(session("name"));
            Segment seg = new Segment(null, title, session("name"),
                content, uniqueTags);
            Story myStory = myAppController.createStory(seg);

            String result = Integer.toString(myStory.getStoryId())+","+Integer.toString(0);
            return ok(result);
        }
    }

    public Result error(String err) {
        return notFound(views.html.error.render("Error! A story with the same content has already been made!"));
    }

    public Result newFork(int storyId, int segmentId){
        return  ok(newStory.render("New Segment", "newFork"));
    }

    /* create a new fork from form data */
    // Adds segment to story with storyId, parent will be segment with segmentId
    public Result newForkSubmit(int storyId, int segmentId) throws SQLException, IOException, ClassNotFoundException{
        DynamicForm form = Form.form().bindFromRequest();
        if (form.data().size() == 0) {
            return badRequest("Form Error");
        } else {
            String title = form.get("title");
            String content = form.get("content");
            String tagsRaw = form.get("tags");
            String[] tags = tagsRaw.replaceAll("#", "").split(" ");
            Segment seg = new Segment(null, title, session("name"), content, tags);
            //add segment to story
            Story myStory = myAppController.getStory(storyId);
            if(myStory != null){
                boolean added = myAppController.fork(myStory, seg, segmentId);
                if(added){
                    String result = Integer.toString(myStory.getStoryId())+","+Integer.toString(seg.getSegmentId());
                    return(ok(result));
                }
            }
            return notFound(views.html.error.render("Page Not Found"));
        }
    }

    public Result story(int id, int segid)throws SQLException, IOException, ClassNotFoundException{
        boolean loggedIn = (session("name") != null);
        Story myStory = myAppController.getStory(id);
        if (myStory != null){
            Segment mySeg = myStory.findSegById(segid);
            if (mySeg == null){
                return notFound(views.html.error.render("Page Not Found"));
            }
            ArrayList<Integer> segsToParent = mySeg.getParentSegIds();
            return ok(story.render(id, segsToParent, loggedIn));
        }
        return notFound(views.html.error.render("Page Not Found"));
    }

    //Returns all stories with tags
    public Result getStoriesByTags(String query) throws SQLException, IOException, ClassNotFoundException{
        myAppController.loadAll();
        System.out.println("Function is called!!!");
        System.out.println("Query:"+query);


        ArrayList<Segment> tagged = myAppController.findByTag(query.trim());

        String searchString = "Search results for tag \""+query+"\"";     
        return ok(search.render(searchString,tagged));
    }

    public Result getStoriesByTitles(String query) throws SQLException,IOException,ClassNotFoundException{
        myAppController.loadAll();

        ArrayList<Segment> titles = myAppController.findByTitle(query.trim());

        String searchString = "Search results for title \""+query+"\"";
        return ok(search.render(searchString,titles));
    }

    public Result getSegmentInfo(){
        DynamicForm form = Form.form().bindFromRequest();
        if (form.data().size() == 0) {
            return badRequest("Form Error");
        } else {

            String result = "{";
            Integer storyId = Integer.parseInt(form.get("storyId"));
            Integer segmentId = Integer.parseInt(form.get("segmentId"));
            try{
                Story myStory = myAppController.getStory(Integer.valueOf(storyId));
                Segment mySegment = myStory.findSegById((int) segmentId);
                int parentSegId = -1;
                if(mySegment.getParentSeg() != null){
                    parentSegId = mySegment.getParentSeg().getSegmentId();
                }

                result += "\"title\": \"" + mySegment.getTitle() + "\",";
                result += "\"author\": \"" + mySegment.getAuthor() + "\",";
                result += "\"tags\": [";
                String[] tags = mySegment.getTags();
                for (int i = 0; i < tags.length; i++){
                    result += "\"" + tags[i] + "\",";
                }
                if (tags.length>0) {
                    result = result.substring(0, result.length() - 1);
                }
                result += "],";
                result += "\"content\": \"" + mySegment.getContent().replace("\"", "~|+#") + "\",";
                result += "\"parentSegId\": \"" + parentSegId + "\",";
                ArrayList<Segment> children = mySegment.getChildSegs();
                String childrenId = "\"childrenid\":[";
                String childrenTitle = "\"childrentitle\":[";
                for(int i = 0; i < children.size(); i++){
                    Segment child = children.get(i);
                    childrenId += "\"" + child.getSegmentId() + "\",";
                    childrenTitle += "\"" + child.getTitle() + "\",";
                }
                if(children.size()>0) {
                    childrenId = childrenId.substring(0, childrenId.length() - 1);
                    childrenTitle = childrenTitle.substring(0, childrenTitle.length() - 1);
                }
                childrenId += "],";
                childrenTitle += "]";
                result += childrenId + childrenTitle + "}";
                System.out.println(result);

                return ok(result);

            }
            catch(Exception e){
                return badRequest(views.html.error.render("Something went wrong! :("));
            }
            

        }
    }
}
