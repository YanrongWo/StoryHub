
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/katie/Documents/GitHub/storyhub/conf/routes
// @DATE:Sat Dec 12 19:55:58 EST 2015


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
