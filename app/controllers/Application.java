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
import java.sql.Connection;
import java.util.concurrent.locks.*;
import util.pdf.PDF;
import play.api.libs.iteratee.Enumerator;

public class Application extends Controller {
    private AppController myAppController;
    private Lock storylock = new ReentrantLock();
    private Lock segmentlock = new ReentrantLock();

    public Application() {
        this.myAppController = new AppController(play.db.DB.getConnection());
    }

    public AppController getMyAppController() {
        return myAppController;
    }

    public Application(Connection conn) {
        this.myAppController = new AppController(conn);
    }

    /* Handles GET requests from / - home page 
     * Displays the home page stories with no offset
     */
    public Result index() {
        //Get all stories with offset 0
        try{
            ArrayList<Story> storyList = myAppController.getFrontPageStories(0);
            int interval = myAppController.getMax();
            int storySize = myAppController.getStoryListSize();  
            return ok(index.render("Homepage", storyList, 0, storySize, interval));
        } catch ( SQLException|IOException|ClassNotFoundException e){
            return internalServerError(views.html.error.render("Something went awfully wrong...please contact the website administrator."));
        }
    }

    /* Handles GET requests from /offset - home page with offset 
     * Displays the home page stories with offset
     * @param i - offset of the home page
     */
    public Result offset(int i) {
        try{
            int storySize = myAppController.getStoryListSize();
            // Invalid offset
            if(i > storySize) {
                String message = "Your offset is larger than the size of the stories library: " + Integer.toString(storySize);
                return notFound(views.html.error.render("Page Not Found"));
            }
            //Get all stories with offset i
            ArrayList<Story> storyList = myAppController.getFrontPageStories(i);   
            return ok(index.render("Homepage", storyList, i, storySize, myAppController.getMax()));
        } catch ( SQLException|IOException|ClassNotFoundException e){
            return internalServerError(views.html.error.render("Something went awfully wrong...please contact the website administrator."));
        }
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
    public Result newStorySubmit(){
        //Check logged in
        if (session("name") != null){
            DynamicForm form = Form.form().bindFromRequest();
            if (form.data().size() == 0) {
                return badRequest("Form Error");
            } else {
                String title = form.get("title").replaceAll("\"", "\'");
                String content = form.get("content").replaceAll("\"", "\'");
                try{
                    // Set a lock so only one person can create a story at a time
                    Boolean acquireLock = storylock.tryLock();
                    while(acquireLock == false) {
                        acquireLock = storylock.tryLock();
                    }
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
                    Segment seg = new Segment(title, session("name"),
                        content, uniqueTags);
                    Story myStory = myAppController.createStory(seg);

                    //Create result with story id, root id
                    String result = Integer.toString(myStory.getStoryId()) + "," + Integer.toString(0);
                    return ok(result);
                } catch ( SQLException|IOException|ClassNotFoundException e){
                    return internalServerError(views.html.error.render("Something went awfully wrong...please contact the website administrator."));
                } finally {
                    storylock.unlock();
                }

            }
        }
        return badRequest(error.render("You must be logged in to add a story"));   
    }

    /* Handles GET request from: /Error/ERROR 
        @param error - error message
        Displays an error page if user tries to submit a duplicate story with the same title and contents */
        public Result error(String err) {
            if(err.equals("NewStoryError")){
                return notFound(views.html.error.render("Error! A story with the same content has already been made!"));
            }
            if(err.equals("StoryClosed")){
                return notFound(views.html.error.render("Error! This story has been closed. Please contibute to another story"));
            }
            return internalServerError(views.html.error.render("Something went awfully wrong...please contact the website administrator."));
        }

    /* Handles GET request from: /Story/STORYID/SEGMENTID/NewSegment
        @param storyId - story ID that you want to add a new segment too
        @param segmentId - the segment that you are branching off to add a new segment to
        Displays a page to add a new segment */
        public Result newFork(int storyId, int segmentId){
            try{
                
                Story myStory = myAppController.getStory(storyId);
                if (session("name") != null)
                {
                    if(!myStory.isClosed()){
                        return  ok(newStory.render("New Segment", "newFork"));
                    } else{
                        return error("StoryClosed");
                    }
                    
                }
                
                return badRequest(error.render("You must be logged in to add a segment"));
            } catch ( SQLException|IOException|ClassNotFoundException e){
            return internalServerError(views.html.error.render("Something went awfully wrong...please contact the website administrator."));
        }
        }

    /* Handles POST request from: /Story/STORYID/SEGMENTID/NewSegment 
        @param storyId - story ID that you want to add a new segment too
        @param segmentId - the segment that you are branching off to add a new segment to
        Returns the page of the new segment created */
    public Result newForkSubmit(int storyId, int segmentId) {

        if (session("name") != null) {
            DynamicForm form = Form.form().bindFromRequest();
            if (form.data().size() == 0) {
                return badRequest("Form Error");
            } else {
                Story myStory = null;
                Story origStory = null;
                try {
                    String title = form.get("title");
                    String content = form.get("content");
                    String tagsRaw = form.get("tags");
                    String[] tags = tagsRaw.replaceAll("#", "").split(" ");
                    Set<String> setTags = new HashSet<String>(Arrays.asList(tags));
                    setTags.remove("");
                    setTags.remove(" ");
                    String[] uniqueTags = setTags.toArray(new String[setTags.size()]);
                    Segment seg = new Segment(title, session("name"), content, uniqueTags);
                    //Set lock so only one person can create a story at a time
                    Boolean acquireLock = segmentlock.tryLock();
                    while(acquireLock == false) {
                        acquireLock = segmentlock.tryLock();
                    }
                    //add segment to story
                    myStory = myAppController.getStory(storyId);
                    origStory = myAppController.getStory(storyId);
                    if(myStory != null){
                        boolean added = myAppController.fork(myStory, seg, segmentId);
                        if(added){
                            String result = Integer.toString(myStory.getStoryId())+","+Integer.toString(seg.getSegmentId());
                            return(ok(result));
                        }
                    }
                    return notFound(views.html.error.render("Page Not Found"));
                }
                catch (SQLException e){
                    if(origStory != null){
                        boolean test = origStory.setClosed();
                        try {
                            myAppController.storeStory(origStory);
                        }
                        catch(Exception x){
                            return badRequest(views.html.error.render("Something went wrong! :("));
                        }
                    }
                    return ok("story closed");
                }
                catch(Exception e){
                    return badRequest(views.html.error.render("Something went wrong! :("));
                } finally {
                    segmentlock.unlock();
                }
            }
            }
            return badRequest(error.render("You must be logged in to add a segment"));
        }

    /*  Handles GET request from: /Story/STORYID/SEGMENTID
        @ param id - story ID of the story the segment is in
        @ param segid - segment ID of the segment of interest
        Returns a page listing the story at the specified segment id  */
        public Result story(int id, int segid){
            try{
                boolean loggedIn = (session("name") != null);
                Story myStory = myAppController.getStory(id);
                if (myStory != null){
                    Segment mySeg = myStory.findSegById(segid);
                    if (mySeg == null){
                        return notFound(views.html.error.render("Page Not Found"));
                    }
                    ArrayList<Integer> segsToParent = mySeg.getParentSegIds();
                    return ok(story.render(id, segsToParent, loggedIn, myStory.isClosed()));
                }
                return notFound(views.html.error.render("Page Not Found"));
            } catch ( SQLException|IOException|ClassNotFoundException e){
            return internalServerError(views.html.error.render("Something went awfully wrong...please contact the website administrator."));
        }
        }

    /*  Handles POST/GET request from: /SearchTags/*
        @param query - String of all queries in format "query1+query2+query3+query4...." 
        Returns a search page listing all segments whose tags that include all the query tags  */
        public Result getStoriesByTags(String query){
            if(query.length()>0) {
                try{
                    myAppController.loadAll();

                    String[] queries = query.split("\\+");

                    ArrayList <Segment> tagged = myAppController.findByTag(queries[0].trim());
                    for(int i = 0; i < queries.length;i++){
                        if(queries[i].substring(0,1)=="#"){
                            queries[i]=queries[i].substring(1,queries[i].length());
                        }
                    // Get interesection of all searches
                        tagged.retainAll(myAppController.findByTag(queries[i].trim()));
                        for(int p = 0 ; p< tagged.size();p ++){
                        }
                        queries[i] = "\""+queries[i]+"\"";
                    }    

                    String searchString = "Search results for tags "+String.join(",",queries);
                    return ok(search.render(searchString,tagged));
                } catch ( SQLException|IOException|ClassNotFoundException e){
                    return internalServerError(views.html.error.render("Something went awfully wrong...please contact the website administrator."));
                }
            } else {
                return badRequest(error.render("Your query cannot be empty"));
            }
        }

    /*  Handles POST request from: /SearchTitles/*
        @param query - String of all queries in format "query1+query2+query3+query4...." 
        Returns a search page listing all segments whose title contains all the query titles*/
        public Result getStoriesByTitles(String query){
            if(query.length()>0) {
                try{
                    myAppController.loadAll();

                    String[] queries = query.split("\\+");

                    ArrayList <Segment> titles = myAppController.findByTitle(queries[0].trim());

                    for(int i = 0; i < queries.length; i++){
                    // Get intersection of all searches
                        titles.retainAll(myAppController.findByTitle(queries[i].trim()));
                        queries[i] = "\""+queries[i]+"\"";
                    }
                    String searchString = "Search results for titles containing "+String.join(",",queries);
                    return ok(search.render(searchString,titles));
                } catch ( SQLException|IOException|ClassNotFoundException e){
                    return internalServerError(views.html.error.render("Something went awfully wrong...please contact the website administrator."));
                }
            } else {
                return badRequest(error.render("Your query cannot be empty"));
            }
        }


    /*  Handles POST request from /AddSegment 
    Returns a JSON formatted Segment to HTML page(that page then parses) */
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
                if (myStory == null){
                    return notFound(views.html.error.render("Page Not Found"));
                }
                Segment mySegment = myStory.findSegById((int) segmentId);
                if (mySegment == null){
                    return notFound(views.html.error.render("Page Not Found"));
                }
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
                result += "\"content\": \"" + mySegment.getContent().replace("\"", "~|+#").replace("\\", "\\\\").replace("\t", "") + "\",";
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
                return ok(result);

            }
            catch(Exception e){
                return badRequest(views.html.error.render("Something went wrong! :("));
            }
        }
    }

    /*  Handles POST request from /ExportToPdf 
    Returns content from the form as a PDF file */
    public Result toPdf() {
         DynamicForm form = Form.form().bindFromRequest();
        if (form.data().size() == 0) {
            return badRequest("Form Error");
        } else if (form.get("content") == null){
            return badRequest(views.html.error.render("Missing content for PDF file"));
        } else {
            response().setContentType("application/x-download");
            response().setHeader("Content-Disposition", "attachment;filename=story.pdf");
            
            Html storyContents = new Html(form.get("content"));
            byte[] storyPDF = PDF.toBytes(storyContents);
            return ok(storyPDF);
        }
    }

    /*  Handles POST request from /ExportToTxt 
    Returns content from the form as a txt file */
    public Result txt(){
        DynamicForm form = Form.form().bindFromRequest();
        if (form.data().size() == 0) {
            return badRequest("Form Error");
        } 
        else if (form.get("content") == null){
            return badRequest(views.html.error.render("Missing content for txt file"));
        }   
        else{
            response().setContentType("application/x-download");  
            response().setHeader("Content-disposition","attachment; filename=story.txt");
            return ok(form.get("content"));
        }
    }
}
