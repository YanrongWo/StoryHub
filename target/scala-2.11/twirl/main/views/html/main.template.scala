
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object main_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._

class main extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template3[String,Html,Html,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String)(links: Html)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.45*/("""

"""),format.raw/*3.1*/("""<!DOCTYPE html>

<html lang="en">
    <head>
        <title>"""),_display_(/*7.17*/title),format.raw/*7.22*/("""</title>
        <link rel="stylesheet" media="screen" href=""""),_display_(/*8.54*/routes/*8.60*/.Assets.versioned("stylesheets/main.css")),format.raw/*8.101*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(/*9.59*/routes/*9.65*/.Assets.versioned("images/favicon.ico")),format.raw/*9.104*/("""">
        <script src=""""),_display_(/*10.23*/routes/*10.29*/.Assets.versioned("javascripts/hello.js")),format.raw/*10.70*/("""" type="text/javascript"></script>
          
         <!--JQUERY-->       
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">

		<!-- Optional theme -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" integrity="sha384-aUGj/X2zp5rLCbBxumKTCw2Z50WgIr1vs/PFN4praOTvYXWlVyh2UtNUU0KAUhAX" crossorigin="anonymous">

        <!-- Font for Logo -->
        <link href='https://fonts.googleapis.com/css?family=Kalam:700' rel='stylesheet' type='text/css'>

		<!-- Latest compiled and minified JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
        """),_display_(/*25.10*/links),format.raw/*25.15*/("""
    """),format.raw/*26.5*/("""</head>
    <body>
    	"""),_display_(/*28.7*/menubar()),format.raw/*28.16*/("""
        """),format.raw/*29.9*/("""<div id="maincontent">
        """),_display_(/*30.10*/content),format.raw/*30.17*/("""
    """),format.raw/*31.5*/("""</div>
        <footer class="footer">
          <div class="container">
            <p class="text-muted">Produced by Java the Hutt. Yo Mama!</p>
          </div>
        </footer>
    </body>
</html>
"""))
      }
    }
  }

  def render(title:String,links:Html,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title)(links)(content)

  def f:((String) => (Html) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title) => (links) => (content) => apply(title)(links)(content)

  def ref: this.type = this

}


}

/**/
object main extends main_Scope0.main
              /*
                  -- GENERATED --
                  DATE: Sun Nov 15 18:26:46 EST 2015
                  SOURCE: C:/Users/katie/Documents/GitHub/storyhub/app/views/main.scala.html
                  HASH: 9632a3f18baa7ecae699b25d29971af47e1159b5
                  MATRIX: 753->1|891->44|919->46|1006->107|1031->112|1119->174|1133->180|1195->221|1282->282|1296->288|1356->327|1408->352|1423->358|1485->399|2638->1525|2664->1530|2696->1535|2747->1560|2777->1569|2813->1578|2872->1610|2900->1617|2932->1622
                  LINES: 27->1|32->1|34->3|38->7|38->7|39->8|39->8|39->8|40->9|40->9|40->9|41->10|41->10|41->10|56->25|56->25|57->26|59->28|59->28|60->29|61->30|61->30|62->31
                  -- GENERATED --
              */
          