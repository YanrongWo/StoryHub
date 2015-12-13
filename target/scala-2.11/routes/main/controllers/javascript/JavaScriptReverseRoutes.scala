
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/katie/Documents/GitHub/storyhub/conf/routes
// @DATE:Sat Dec 12 19:55:58 EST 2015

import play.api.routing.JavaScriptReverseRoute
import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:7
package controllers.javascript {
  import ReverseRouteContext.empty

  // @LINE:12
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:12
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[Asset]].javascriptUnbind + """)("file", file)})
        }
      """
    )
  
  }

  // @LINE:7
  class ReverseApplication(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:44
    def getStoriesByTitles: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.getStoriesByTitles",
      """
        function(query) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "SearchTitles/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("query", encodeURIComponent(query))})
        }
      """
    )
  
    // @LINE:48
    def error: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.error",
      """
        function(error) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "Error/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("error", encodeURIComponent(error))})
        }
      """
    )
  
    // @LINE:27
    def newForkSubmit: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.newForkSubmit",
      """
        function(storyId,segmentId) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "Story/" + (""" + implicitly[PathBindable[Integer]].javascriptUnbind + """)("storyId", storyId) + "/" + (""" + implicitly[PathBindable[Integer]].javascriptUnbind + """)("segmentId", segmentId) + "/NewSegment"})
        }
      """
    )
  
    // @LINE:31
    def story: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.story",
      """
        function(id,segid) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "Story/" + (""" + implicitly[PathBindable[Integer]].javascriptUnbind + """)("id", id) + "/" + (""" + implicitly[PathBindable[Integer]].javascriptUnbind + """)("segid", segid)})
        }
      """
    )
  
    // @LINE:35
    def getSegmentInfo: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.getSegmentInfo",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "AddSegment"})
        }
      """
    )
  
    // @LINE:17
    def noFacebookName: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.noFacebookName",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "NoFacebookName"})
        }
      """
    )
  
    // @LINE:52
    def txt: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.txt",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "ExportToTxt"})
        }
      """
    )
  
    // @LINE:21
    def newStory: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.newStory",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "NewStory"})
        }
      """
    )
  
    // @LINE:16
    def facebookName: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.facebookName",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "FacebookName"})
        }
      """
    )
  
    // @LINE:26
    def newFork: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.newFork",
      """
        function(storyId,segmentId) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "Story/" + (""" + implicitly[PathBindable[Integer]].javascriptUnbind + """)("storyId", storyId) + "/" + (""" + implicitly[PathBindable[Integer]].javascriptUnbind + """)("segmentId", segmentId) + "/NewSegment"})
        }
      """
    )
  
    // @LINE:22
    def newStorySubmit: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.newStorySubmit",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "NewStory"})
        }
      """
    )
  
    // @LINE:56
    def toPdf: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.toPdf",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "ExportToPdf"})
        }
      """
    )
  
    // @LINE:39
    def getStoriesByTags: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.getStoriesByTags",
      """
        function(query) {
        
          if (true) {
            return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "SearchTags/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("query", encodeURIComponent(query))})
          }
        
        }
      """
    )
  
    // @LINE:7
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
    // @LINE:8
    def offset: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.offset",
      """
        function(offset) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + (""" + implicitly[PathBindable[Integer]].javascriptUnbind + """)("offset", offset)})
        }
      """
    )
  
  }


}