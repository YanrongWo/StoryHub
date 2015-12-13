
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/katie/Documents/GitHub/storyhub/conf/routes
// @DATE:Sat Dec 12 19:55:58 EST 2015

import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:7
package controllers {

  // @LINE:12
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:12
    def versioned(file:Asset): Call = {
      implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:7
  class ReverseApplication(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:44
    def getStoriesByTitles(query:String): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "SearchTitles/" + implicitly[PathBindable[String]].unbind("query", dynamicString(query)))
    }
  
    // @LINE:48
    def error(error:String): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "Error/" + implicitly[PathBindable[String]].unbind("error", dynamicString(error)))
    }
  
    // @LINE:27
    def newForkSubmit(storyId:Integer, segmentId:Integer): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "Story/" + implicitly[PathBindable[Integer]].unbind("storyId", storyId) + "/" + implicitly[PathBindable[Integer]].unbind("segmentId", segmentId) + "/NewSegment")
    }
  
    // @LINE:31
    def story(id:Integer, segid:Integer): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "Story/" + implicitly[PathBindable[Integer]].unbind("id", id) + "/" + implicitly[PathBindable[Integer]].unbind("segid", segid))
    }
  
    // @LINE:35
    def getSegmentInfo(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "AddSegment")
    }
  
    // @LINE:17
    def noFacebookName(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "NoFacebookName")
    }
  
    // @LINE:52
    def txt(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "ExportToTxt")
    }
  
    // @LINE:21
    def newStory(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "NewStory")
    }
  
    // @LINE:16
    def facebookName(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "FacebookName")
    }
  
    // @LINE:26
    def newFork(storyId:Integer, segmentId:Integer): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "Story/" + implicitly[PathBindable[Integer]].unbind("storyId", storyId) + "/" + implicitly[PathBindable[Integer]].unbind("segmentId", segmentId) + "/NewSegment")
    }
  
    // @LINE:22
    def newStorySubmit(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "NewStory")
    }
  
    // @LINE:56
    def toPdf(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "ExportToPdf")
    }
  
    // @LINE:39
    def getStoriesByTags(query:String): Call = {
    
      (query: @unchecked) match {
      
        // @LINE:39
        case (query)  =>
          import ReverseRouteContext.empty
          Call("GET", _prefix + { _defaultPrefix } + "SearchTags/" + implicitly[PathBindable[String]].unbind("query", dynamicString(query)))
      
      }
    
    }
  
    // @LINE:7
    def index(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix)
    }
  
    // @LINE:8
    def offset(offset:Integer): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + implicitly[PathBindable[Integer]].unbind("offset", offset))
    }
  
  }


}