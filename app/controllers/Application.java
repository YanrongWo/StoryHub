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
        if(myAppController.getStories().size()==0) {
            myAppController.loadAll();
        }

        //Get all stories
        ArrayList<Story> storyList = myAppController.getFrontPageStories(0);
        int interval = myAppController.getMax();
        System.out.println("interval");
        System.out.println(interval);  

        return ok(index.render("Homepage", storyList, 0, myAppController.getStories().size(), interval));
    }

    public Result offset(int i) throws SQLException, IOException, ClassNotFoundException{
        if(myAppController.getStories().size()==0) {
            myAppController.loadAll();
        }
        if(i>myAppController.getStories().size()) {
            String message = "Your offset is larger than the size of the stories library";
            return badRequest(main.render("Page Not Found", Html.apply(""), Html.apply(message)));
        }
        ArrayList<Story> storyList = myAppController.getFrontPageStories(i);   
        return ok(index.render("Homepage", storyList, i, myAppController.getStories().size(), myAppController.getMax()));
    }

    // public Result search(){
    //     //Get all stories
    //     ArrayList<Story> storyList = new ArrayList<Story>();
    //     //For each story, add to storyList 
    //     //public Segment(Segment parentSeg, String title, String author, String content, int id, String[] tags)
    //     Segment test1 = new Segment(null, "Title 1", "Author 1", "Content 1", new String[] {"a", "b"});
    //     Segment test2 = new Segment(null, "Title 2", "Author 2", "Content 2", new String[] {"x", "y"});
    //     Story s1 = new Story(test1, 1);
    //     storyList.add(s1);
    //     Story s2 = new Story(test2, 2);
    //     storyList.add(s2);
        
    //     return ok(.render("Results", storyList));
    // }

    /* Make controller object and set form.get("name") */
    public Result facebookName() {
    	DynamicForm form = Form.form().bindFromRequest();
    	if (form.data().size() != 0)
    	{
            if (session("name") == null){ 
                System.out.println(form.get("name"));
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
    public Result newStorySubmit() throws SQLException{
        DynamicForm form = Form.form().bindFromRequest();
        if (form.data().size() == 0) {
            return badRequest("Form Error");
        } else {
            String title = form.get("title").replaceAll("\"", "\'");
            System.out.println(title);
            String content = form.get("content").replaceAll("\"", "\'");
            ArrayList<Story> allStories = myAppController.getStories();
            for (int i = 0; i < allStories.size(); i++){
                if (allStories.get(i).getRoot().getContent().equals(content)){
                    int id = allStories.get(i).getStoryId();
                    String message = " <a href=\"/Story/" + id 
                        + "/0\"> Error! A story with the same content has already been made! </a>";
                    return badRequest(main.render("Page Not Found", Html.apply(""), Html.apply(message)));
                }
            }
            System.out.println(content);
            String tagsRaw = form.get("tags").replaceAll("\"", "\'");
            String[] tags = tagsRaw.replaceAll("#", "").split(" ");
            Set<String> setTags = new HashSet<String>(Arrays.asList(tags));
            String[] uniqueTags = setTags.toArray(new String[setTags.size()]);
            System.out.println(uniqueTags);
            System.out.println(session("name"));
            Segment seg = new Segment(null, title, session("name"),
                content, uniqueTags);
            Story myStory = myAppController.createStory(seg);

            boolean loggedIn = (session("name") != null);
            int storyId = myStory.getStoryId();
            ArrayList<Integer> segsToParent = myStory.findSegById(0).getParentSegIds();
            return ok(story.render(storyId, segsToParent, loggedIn));
        }
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
                System.out.println("myStory" + myStory);
                boolean added = myAppController.fork(myStory, seg, segmentId);
                ArrayList<Integer> segs = new ArrayList<Integer>();
                segs.add(segmentId);
                if(added){
                    boolean loggedIn = (session("name") != null);
                    ArrayList<Integer> segsToParent = myStory.findSegById(segmentId).getParentSegIds();
                    return ok(story.render(storyId, segsToParent, loggedIn));
                }
            }
            return notFound(main.render("Page Not Found", Html.apply(""), Html.apply("Page Not Found.")));
        }
    }

    public Result story(int id, int segid)throws SQLException, IOException, ClassNotFoundException{
        boolean loggedIn = (session("name") != null);
        Story myStory = myAppController.getStory(id);
        if (myStory != null){
            Segment mySeg = myStory.findSegById(segid);
            ArrayList<Integer> segsToParent = mySeg.getParentSegIds();
            return ok(story.render(id, segsToParent, loggedIn));
        }
        return notFound(main.render("Page Not Found", Html.apply(""), Html.apply("Page Not Found.")));
    }

    //Returns all stories with tags
    public Result getTaggedStories(String query){
        System.out.println("Function is called!!!");
        System.out.println("Query:"+query);


        ArrayList<Segment> tagged = myAppController.find(query.trim());

        String searchString = "Search results for tag \""+query+"\"";     
        return ok(search.render(searchString,tagged));
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
                result += "\"content\": \"" + mySegment.getContent() + "\",";
                result += "\"parentSegId\": " + parentSegId + ",";
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
            catch (IOException e){
                return badRequest("IOException");
            }
            catch(SQLException e){
                return badRequest("SQLException");
            }
            catch(ClassNotFoundException e){
                return badRequest("ClassNotFoundException");
            }
            catch(Exception e){
                return badRequest("Exception");
            }
            

        }
    }
}
