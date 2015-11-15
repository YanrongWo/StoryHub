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

    /* Handles GET requests from / - home page 
     * Displays the home page stories with no offset
     */
    public Result index() throws SQLException, IOException, ClassNotFoundException{
        //Get all stories with offset 0
        ArrayList<Story> storyList = myAppController.getFrontPageStories(0);
        int interval = myAppController.getMax();
        int storySize = myAppController.getStoryListSize();  
        return ok(index.render("Homepage", storyList, 0, storySize, interval));
    }

    /* Handles GET requests from /offset - home page with offset 
     * Displays the home page stories with offset
     * @param i - offset of the home page
     */
    public Result offset(int i) throws SQLException, IOException, ClassNotFoundException{
        int storySize = myAppController.getStoryListSize();
        // Invalid offset
        if(i > storySize) {
            String message = "Your offset is larger than the size of the stories library: " + Integer.toString(storySize);
            return notFound(views.html.error.render("Page Not Found"));
        }
        //Get all stories with offset i
        ArrayList<Story> storyList = myAppController.getFrontPageStories(i);   
        return ok(index.render("Homepage", storyList, i, storySize, myAppController.getMax()));
    }

    /* Handles POST requests from /FacebookName - facebook login 
     * Tells the front end if the user's name has been changed
     */
    public Result facebookName() {
        //Check for input
    	DynamicForm form = Form.form().bindFromRequest();
    	if (form.data().size() != 0)
    	{
            //If there wasn't a name in session, set one
            if (session("name") == null){ 
                session("name", form.get("name"));
                //Return that name was changed
                return ok("changed");
            }
    		return ok("");
    	}
    	return badRequest();
    }

    /* Handles POST requests from /NoFacebookName - facebook logout 
     * Tells the front end if the user's name has been changed
     */
    public Result noFacebookName() {
        //Clear the session name if there is one
        if (session("name") != null)
        {
            session().clear();
            //Return that it was cleared
            return ok("changed");
        }
        return ok("");
    }

    /* Handles GET requests from /NewStory - new story page
     * Renders the new story page if there is a log in
     */
    public Result newStory(){
        //Check if logged in
        if (session("name") != null)
        {
            return ok(newStory.render("New Story", "newStory"));
        }
        return badRequest(error.render("You must be logged in to add a story"));
    }

    /* Handles POST requests from /NewStory - new story submission 
     * Makes sure user is checked in, the new story isn't a repeat,
     * and cleans up input for storage
     * Returns the new story id and the root id to the front end
     */
    public Result newStorySubmit() throws SQLException, IOException, ClassNotFoundException{
        //Check logged in
        if (session("name") != null){
            DynamicForm form = Form.form().bindFromRequest();
            if (form.data().size() == 0) {
                return badRequest("Form Error");
            } else {
                String title = form.get("title").replaceAll("\"", "\'");
                String content = form.get("content").replaceAll("\"", "\'");
                //Checks that the story doesn't have the same content as 
                //another root
                myAppController.loadAll();
                ArrayList<Story> allStories = myAppController.getStories();
                for (int i = 0; i < allStories.size(); i++){
                    if (allStories.get(i).getRoot().getContent().equals(content)){
                        int id = allStories.get(i).getStoryId();
                        String message = " <a href=\"/Story/" + id 
                            + "/0\"> Error! A story with the same content has already been made! </a>";
                        return ok("not found");
                    }
                }
                //Parse and make sure the tags aren't repeats
                String tagsRaw = form.get("tags").replaceAll("\"", "\'");
                String[] tags = tagsRaw.replaceAll("#", "").split(" ");
                Set<String> setTags = new HashSet<String>(Arrays.asList(tags));
                setTags.remove("");
                setTags.remove(" ");
                String[] uniqueTags = setTags.toArray(new String[setTags.size()]);

                //Create root and then the story
                Segment seg = new Segment(null, title, session("name"),
                    content, uniqueTags);
                Story myStory = myAppController.createStory(seg);

                //Create result with story id, root id
                String result = Integer.toString(myStory.getStoryId()) + "," + Integer.toString(0);
                return ok(result);
            }
        }
        return badRequest(error.render("You must be logged in to add a story"));
    }

    public Result error(String err) {
        return notFound(views.html.error.render("Error! A story with the same content has already been made!"));
    }

    public Result newFork(int storyId, int segmentId){
            if (session("name") != null)
            {
                return  ok(newStory.render("New Segment", "newFork"));
            }
            return badRequest(error.render("You must be logged in to add a segment"));
        }

    /* create a new fork from form data */
    // Adds segment to story with storyId, parent will be segment with segmentId
    public Result newForkSubmit(int storyId, int segmentId) throws SQLException, IOException, ClassNotFoundException{
        if (session("name") != null) {
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
        return badRequest(error.render("You must be logged in to add a segment"));
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

    //Returns all segments with specified tags
    public Result getStoriesByTags(String query) throws SQLException, IOException, ClassNotFoundException{
        myAppController.loadAll();
        System.out.println("Function is called!!!");
        System.out.println("Query:"+query);

        String[] queries = query.split("\\+");

        ArrayList <Segment> tagged = myAppController.findByTag(queries[0].trim());
        for(int i = 0; i < queries.length;i++){
            if(queries[i].substring(0,1)=="#"){
                queries[i]=queries[i].substring(1,queries[i].length());
            }
            System.out.println("Query is "+queries[i]);
            // Get interesection of all searches
            tagged.retainAll(myAppController.findByTag(queries[i].trim()));
            for(int p = 0 ; p< tagged.size();p ++){
                System.out.println("Segment is "+tagged.get(p).getStoryId()+","+tagged.get(p).getSegmentId());
            }
            System.out.println("Tagged Segments Length:"+tagged.size());
            queries[i] = "\""+queries[i]+"\"";
            System.out.println("New Query");
        }

        String searchString = "Search results for tag(s) "+ String.join(",",queries);     
        return ok(search.render(searchString,tagged));
    }

    public Result getStoriesByTitles(String query) throws SQLException,IOException,ClassNotFoundException{
        myAppController.loadAll();

        String[] queries = query.split("\\+");

        ArrayList <Segment> titles = myAppController.findByTitle(queries[0].trim());
        
        for(int i = 0; i < queries.length; i++){
            // Get intersection of all searches
            titles.retainAll(myAppController.findByTitle(queries[i].trim()));
            queries[i] = "\""+queries[i]+"\"";
        }

        String searchString = "Search results for title(s) "+String.join(",",queries);
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
