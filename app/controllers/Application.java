package controllers;
import play.*;
import play.mvc.*;
import models.*;
import play.data.DynamicForm;
import play.data.Form;
import views.html.*;
import java.sql.SQLException;


public class Application extends Controller {

    AppController myAppController = new AppController();

    public Result index() {

        //Story s = new Story();
        //Ebean.save(s);

        return ok(index.render("ASDFASFASDF World."));
    }

    /* Make controller object and set form.get("name") */
    public Result facebookName() {
    	DynamicForm form = Form.form().bindFromRequest();
    	if (form.data().size() != 0)
    	{
    		myAppController.setCurrentUser(form.get("name"));
    		return ok("Got name!");
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
            String content = form.get("content");
            String tagsRaw = form.get("tags");
            String[] tags = tagsRaw.replaceAll("#", "").split(" ");
            Segment seg = new Segment(null, title, myAppController.getCurrentUser(),
                content, 0, tags);
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
            Segment seg = new Segment(null, title, myAppController.getCurrentUser(),
                content, 0, tags);
            //add segment to story

            return ok("Submitted");
        }
    }
}
