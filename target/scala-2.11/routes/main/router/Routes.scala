
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/katie/Documents/GitHub/storyhub/conf/routes
// @DATE:Sat Dec 12 19:55:58 EST 2015

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:7
  Application_1: controllers.Application,
  // @LINE:12
  Assets_0: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:7
    Application_1: controllers.Application,
    // @LINE:12
    Assets_0: controllers.Assets
  ) = this(errorHandler, Application_1, Assets_0, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, Application_1, Assets_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.Application.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """$offset<[0-9]+>""", """controllers.Application.offset(offset:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """FacebookName""", """controllers.Application.facebookName()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """NoFacebookName""", """controllers.Application.noFacebookName()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """NewStory""", """controllers.Application.newStory()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """NewStory""", """controllers.Application.newStorySubmit()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """Story/$storyId<[^/]+>/$segmentId<[^/]+>/NewSegment""", """controllers.Application.newFork(storyId:Integer, segmentId:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """Story/$storyId<[^/]+>/$segmentId<[^/]+>/NewSegment""", """controllers.Application.newForkSubmit(storyId:Integer, segmentId:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """Story/$id<[0-9]+>/$segid<[0-9]+>""", """controllers.Application.story(id:Integer, segid:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """AddSegment""", """controllers.Application.getSegmentInfo()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """SearchTags/$query<[^/]+>""", """controllers.Application.getStoriesByTags(query:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """SearchTags/$query<[^/]+>""", """controllers.Application.getStoriesByTags(query:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """SearchTitles/$query<[^/]+>""", """controllers.Application.getStoriesByTitles(query:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """Error/$error<[^/]+>""", """controllers.Application.error(error:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ExportToTxt""", """controllers.Application.txt()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """ExportToPdf""", """controllers.Application.toPdf()"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:7
  private[this] lazy val controllers_Application_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_Application_index0_invoker = createInvoker(
    Application_1.index(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "index",
      Nil,
      "GET",
      """""",
      this.prefix + """"""
    )
  )

  // @LINE:8
  private[this] lazy val controllers_Application_offset1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("offset", """[0-9]+""",false)))
  )
  private[this] lazy val controllers_Application_offset1_invoker = createInvoker(
    Application_1.offset(fakeValue[Integer]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "offset",
      Seq(classOf[Integer]),
      "GET",
      """""",
      this.prefix + """$offset<[0-9]+>"""
    )
  )

  // @LINE:12
  private[this] lazy val controllers_Assets_versioned2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned2_invoker = createInvoker(
    Assets_0.versioned(fakeValue[String], fakeValue[Asset]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      """""",
      this.prefix + """assets/$file<.+>"""
    )
  )

  // @LINE:16
  private[this] lazy val controllers_Application_facebookName3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("FacebookName")))
  )
  private[this] lazy val controllers_Application_facebookName3_invoker = createInvoker(
    Application_1.facebookName(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "facebookName",
      Nil,
      "POST",
      """""",
      this.prefix + """FacebookName"""
    )
  )

  // @LINE:17
  private[this] lazy val controllers_Application_noFacebookName4_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("NoFacebookName")))
  )
  private[this] lazy val controllers_Application_noFacebookName4_invoker = createInvoker(
    Application_1.noFacebookName(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "noFacebookName",
      Nil,
      "POST",
      """""",
      this.prefix + """NoFacebookName"""
    )
  )

  // @LINE:21
  private[this] lazy val controllers_Application_newStory5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("NewStory")))
  )
  private[this] lazy val controllers_Application_newStory5_invoker = createInvoker(
    Application_1.newStory(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "newStory",
      Nil,
      "GET",
      """""",
      this.prefix + """NewStory"""
    )
  )

  // @LINE:22
  private[this] lazy val controllers_Application_newStorySubmit6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("NewStory")))
  )
  private[this] lazy val controllers_Application_newStorySubmit6_invoker = createInvoker(
    Application_1.newStorySubmit(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "newStorySubmit",
      Nil,
      "POST",
      """""",
      this.prefix + """NewStory"""
    )
  )

  // @LINE:26
  private[this] lazy val controllers_Application_newFork7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("Story/"), DynamicPart("storyId", """[^/]+""",true), StaticPart("/"), DynamicPart("segmentId", """[^/]+""",true), StaticPart("/NewSegment")))
  )
  private[this] lazy val controllers_Application_newFork7_invoker = createInvoker(
    Application_1.newFork(fakeValue[Integer], fakeValue[Integer]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "newFork",
      Seq(classOf[Integer], classOf[Integer]),
      "GET",
      """""",
      this.prefix + """Story/$storyId<[^/]+>/$segmentId<[^/]+>/NewSegment"""
    )
  )

  // @LINE:27
  private[this] lazy val controllers_Application_newForkSubmit8_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("Story/"), DynamicPart("storyId", """[^/]+""",true), StaticPart("/"), DynamicPart("segmentId", """[^/]+""",true), StaticPart("/NewSegment")))
  )
  private[this] lazy val controllers_Application_newForkSubmit8_invoker = createInvoker(
    Application_1.newForkSubmit(fakeValue[Integer], fakeValue[Integer]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "newForkSubmit",
      Seq(classOf[Integer], classOf[Integer]),
      "POST",
      """""",
      this.prefix + """Story/$storyId<[^/]+>/$segmentId<[^/]+>/NewSegment"""
    )
  )

  // @LINE:31
  private[this] lazy val controllers_Application_story9_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("Story/"), DynamicPart("id", """[0-9]+""",false), StaticPart("/"), DynamicPart("segid", """[0-9]+""",false)))
  )
  private[this] lazy val controllers_Application_story9_invoker = createInvoker(
    Application_1.story(fakeValue[Integer], fakeValue[Integer]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "story",
      Seq(classOf[Integer], classOf[Integer]),
      "GET",
      """""",
      this.prefix + """Story/$id<[0-9]+>/$segid<[0-9]+>"""
    )
  )

  // @LINE:35
  private[this] lazy val controllers_Application_getSegmentInfo10_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("AddSegment")))
  )
  private[this] lazy val controllers_Application_getSegmentInfo10_invoker = createInvoker(
    Application_1.getSegmentInfo(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getSegmentInfo",
      Nil,
      "POST",
      """""",
      this.prefix + """AddSegment"""
    )
  )

  // @LINE:39
  private[this] lazy val controllers_Application_getStoriesByTags11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("SearchTags/"), DynamicPart("query", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_getStoriesByTags11_invoker = createInvoker(
    Application_1.getStoriesByTags(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getStoriesByTags",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """SearchTags/$query<[^/]+>"""
    )
  )

  // @LINE:40
  private[this] lazy val controllers_Application_getStoriesByTags12_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("SearchTags/"), DynamicPart("query", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_getStoriesByTags12_invoker = createInvoker(
    Application_1.getStoriesByTags(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getStoriesByTags",
      Seq(classOf[String]),
      "POST",
      """""",
      this.prefix + """SearchTags/$query<[^/]+>"""
    )
  )

  // @LINE:44
  private[this] lazy val controllers_Application_getStoriesByTitles13_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("SearchTitles/"), DynamicPart("query", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_getStoriesByTitles13_invoker = createInvoker(
    Application_1.getStoriesByTitles(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getStoriesByTitles",
      Seq(classOf[String]),
      "POST",
      """""",
      this.prefix + """SearchTitles/$query<[^/]+>"""
    )
  )

  // @LINE:48
  private[this] lazy val controllers_Application_error14_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("Error/"), DynamicPart("error", """[^/]+""",true)))
  )
  private[this] lazy val controllers_Application_error14_invoker = createInvoker(
    Application_1.error(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "error",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """Error/$error<[^/]+>"""
    )
  )

  // @LINE:52
  private[this] lazy val controllers_Application_txt15_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ExportToTxt")))
  )
  private[this] lazy val controllers_Application_txt15_invoker = createInvoker(
    Application_1.txt(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "txt",
      Nil,
      "POST",
      """""",
      this.prefix + """ExportToTxt"""
    )
  )

  // @LINE:56
  private[this] lazy val controllers_Application_toPdf16_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("ExportToPdf")))
  )
  private[this] lazy val controllers_Application_toPdf16_invoker = createInvoker(
    Application_1.toPdf(),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "toPdf",
      Nil,
      "POST",
      """""",
      this.prefix + """ExportToPdf"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:7
    case controllers_Application_index0_route(params) =>
      call { 
        controllers_Application_index0_invoker.call(Application_1.index())
      }
  
    // @LINE:8
    case controllers_Application_offset1_route(params) =>
      call(params.fromPath[Integer]("offset", None)) { (offset) =>
        controllers_Application_offset1_invoker.call(Application_1.offset(offset))
      }
  
    // @LINE:12
    case controllers_Assets_versioned2_route(params) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned2_invoker.call(Assets_0.versioned(path, file))
      }
  
    // @LINE:16
    case controllers_Application_facebookName3_route(params) =>
      call { 
        controllers_Application_facebookName3_invoker.call(Application_1.facebookName())
      }
  
    // @LINE:17
    case controllers_Application_noFacebookName4_route(params) =>
      call { 
        controllers_Application_noFacebookName4_invoker.call(Application_1.noFacebookName())
      }
  
    // @LINE:21
    case controllers_Application_newStory5_route(params) =>
      call { 
        controllers_Application_newStory5_invoker.call(Application_1.newStory())
      }
  
    // @LINE:22
    case controllers_Application_newStorySubmit6_route(params) =>
      call { 
        controllers_Application_newStorySubmit6_invoker.call(Application_1.newStorySubmit())
      }
  
    // @LINE:26
    case controllers_Application_newFork7_route(params) =>
      call(params.fromPath[Integer]("storyId", None), params.fromPath[Integer]("segmentId", None)) { (storyId, segmentId) =>
        controllers_Application_newFork7_invoker.call(Application_1.newFork(storyId, segmentId))
      }
  
    // @LINE:27
    case controllers_Application_newForkSubmit8_route(params) =>
      call(params.fromPath[Integer]("storyId", None), params.fromPath[Integer]("segmentId", None)) { (storyId, segmentId) =>
        controllers_Application_newForkSubmit8_invoker.call(Application_1.newForkSubmit(storyId, segmentId))
      }
  
    // @LINE:31
    case controllers_Application_story9_route(params) =>
      call(params.fromPath[Integer]("id", None), params.fromPath[Integer]("segid", None)) { (id, segid) =>
        controllers_Application_story9_invoker.call(Application_1.story(id, segid))
      }
  
    // @LINE:35
    case controllers_Application_getSegmentInfo10_route(params) =>
      call { 
        controllers_Application_getSegmentInfo10_invoker.call(Application_1.getSegmentInfo())
      }
  
    // @LINE:39
    case controllers_Application_getStoriesByTags11_route(params) =>
      call(params.fromPath[String]("query", None)) { (query) =>
        controllers_Application_getStoriesByTags11_invoker.call(Application_1.getStoriesByTags(query))
      }
  
    // @LINE:40
    case controllers_Application_getStoriesByTags12_route(params) =>
      call(params.fromPath[String]("query", None)) { (query) =>
        controllers_Application_getStoriesByTags12_invoker.call(Application_1.getStoriesByTags(query))
      }
  
    // @LINE:44
    case controllers_Application_getStoriesByTitles13_route(params) =>
      call(params.fromPath[String]("query", None)) { (query) =>
        controllers_Application_getStoriesByTitles13_invoker.call(Application_1.getStoriesByTitles(query))
      }
  
    // @LINE:48
    case controllers_Application_error14_route(params) =>
      call(params.fromPath[String]("error", None)) { (error) =>
        controllers_Application_error14_invoker.call(Application_1.error(error))
      }
  
    // @LINE:52
    case controllers_Application_txt15_route(params) =>
      call { 
        controllers_Application_txt15_invoker.call(Application_1.txt())
      }
  
    // @LINE:56
    case controllers_Application_toPdf16_route(params) =>
      call { 
        controllers_Application_toPdf16_invoker.call(Application_1.toPdf())
      }
  }
}