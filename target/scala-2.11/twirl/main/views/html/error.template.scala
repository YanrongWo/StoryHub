
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object error_Scope0 {
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

class error extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(errorMessage: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.24*/("""

"""),_display_(/*3.2*/main("Error")/*3.15*/{_display_(Seq[Any](format.raw/*3.16*/(""" """)))}/*3.18*/{_display_(Seq[Any](format.raw/*3.19*/("""

	"""),format.raw/*5.2*/("""<div class="container">
		<div class="error-message">
			"""),_display_(/*7.5*/errorMessage),format.raw/*7.17*/("""
		"""),format.raw/*8.3*/("""</div>
	</div>
""")))}),format.raw/*10.2*/("""
"""))
      }
    }
  }

  def render(errorMessage:String): play.twirl.api.HtmlFormat.Appendable = apply(errorMessage)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (errorMessage) => apply(errorMessage)

  def ref: this.type = this

}


}

/**/
object error extends error_Scope0.error
              /*
                  -- GENERATED --
                  DATE: Sun Nov 15 02:54:55 EST 2015
                  SOURCE: C:/Users/katie/Documents/GitHub/storyhub/app/views/error.scala.html
                  HASH: 0f7d6c756b51a7243b1c0c05995f23ad380209e3
                  MATRIX: 745->1|862->23|892->28|913->41|951->42|971->44|1009->45|1040->50|1125->110|1157->122|1187->126|1235->144
                  LINES: 27->1|32->1|34->3|34->3|34->3|34->3|34->3|36->5|38->7|38->7|39->8|41->10
                  -- GENERATED --
              */
          