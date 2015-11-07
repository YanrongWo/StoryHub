package controllers;
import play.*;
import play.mvc.*;
import models.*;
import play.data.DynamicForm;
import play.data.Form;
import views.html.*;


public class Application extends Controller {

    public Result index() {
        return ok(index.render("ASDFASFASDF World."));
    }

    public Result facebookName() {
    	DynamicForm form = Form.form().bindFromRequest();
    	if (form.data().size() != 0)
    	{
    		System.out.println(form.get("name"));
    		return ok("Got name!");
    	}
    	return badRequest();
    }
}
