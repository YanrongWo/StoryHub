
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/kelurulu/Documents/Columbia Classes/Fall 2015/Software Engineering/activator-1.3.6-minimal/firstapp/conf/routes
// @DATE:Tue Oct 20 11:27:10 EDT 2015


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
