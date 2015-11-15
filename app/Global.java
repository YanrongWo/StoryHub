import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;
import play.twirl.api.Html;

import static play.mvc.Results.*;

public class Global extends GlobalSettings {

    @Override
    public Promise<Result> onHandlerNotFound(RequestHeader request) {
        return Promise.<Result>pure(notFound(views.html.error.render("Page Not Found")));
    }
}