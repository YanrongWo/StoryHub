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

public class Application extends Controller {

    AppController myAppController = new AppController();

    public Result index() throws SQLException, IOException, ClassNotFoundException{

        myAppController.loadAll();

        //Get all stories
        ArrayList<Story> storyList = myAppController.getFrontPageStories();
        //ArrayList<Story> storyList = new ArrayList<Story>();

        //For each story, add to storyList 
        //public Segment(Segment parentSeg, String title, String author, String content, int id, String[] tags)
        Segment test1 = new Segment(null, "Title 1", "Author 1", "Content 1", new String[] {"a", "b"});
        Segment test2 = new Segment(null, "Title 2", "Author 2", "This is 250 characters. Story content is great. Read more about this riveting story. You'll never guess what happens because I don't know what will happen. You can determine the ending. Or will there not be an ending? Who knows... Java Play is the worst. Would not recommend", new String[] {"x", "y"});
        //Story s1 = new Story(test1, 1);
        //myAppController.createStory(test1);
        //myAppController.createStory(test2);
        // storyList.add(s1);
        // Story s2 = new Story(test2, 2);
        // storyList.add(s2);   

        return ok(index.render("Homepage", storyList));
    }

    public Result search(){
        //Get all stories
        ArrayList<Story> storyList = new ArrayList<Story>();
        //For each story, add to storyList 
        //public Segment(Segment parentSeg, String title, String author, String content, int id, String[] tags)
        Segment test1 = new Segment(null, "Title 1", "Author 1", "Content 1", new String[] {"a", "b"});
        Segment test2 = new Segment(null, "Title 2", "Author 2", "Content 2", new String[] {"x", "y"});
        Story s1 = new Story(test1, 1);
        storyList.add(s1);
        Story s2 = new Story(test2, 2);
        storyList.add(s2);
        
        return ok(index.render("Results", storyList));
    }

    /* Make controller object and set form.get("name") */
    public Result facebookName() {
    	DynamicForm form = Form.form().bindFromRequest();
    	if (form.data().size() != 0)
    	{
            System.out.println(form.get("name"));
            session("name", form.get("name"));
    		return ok("Sucess");
    	}
    	return badRequest();
    }

    public Result newStory(){
    	return ok(newStory.render("New Story"));
    }

    /* create a new story from form data */
    public Result newStorySubmit() throws SQLException{
        DynamicForm form = Form.form().bindFromRequest();
        if (form.data().size() == 0) {
            return badRequest("Form Error");
        } else {
            String title = form.get("title");
            System.out.println(title);
            String content = form.get("content");
            System.out.println(content);
            String tagsRaw = form.get("tags");
            String[] tags = tagsRaw.replaceAll("#", "").split(" ");
            System.out.println(tags);
            System.out.println(session("name"));
            Segment seg = new Segment(null, title, session("name"),
                content, tags);
            Story myStory = myAppController.createStory(seg);
            return ok("Submitted");
        }
    }

    public Result newFork(){
        return  ok(newStory.render("New Fork"));
    }

    /* create a new fork from form data */
    public Result newForkSubmit(){
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
            return ok("Submitted");
        }
    }

    public Result story(int id){
        System.out.println(id);
        return ok(story.render(id));
    }

    public Result getSegmentInfo(){
        DynamicForm form = Form.form().bindFromRequest();
        if (form.data().size() == 0) {
            return badRequest("Form Error");
        } else {
            // String result = "{\"hi\": \"ho\"}";
            // return ok(result);

            String result = "{";
            Integer storyId = Integer.parseInt(form.get("storyId"));
            Integer segmentId = Integer.parseInt(form.get("segmentId"));
            try{
                Story myStory = myAppController.getStory(Integer.valueOf(storyId));
                Segment mySegment = myStory.getRoot().getSegment((int) segmentId);

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
