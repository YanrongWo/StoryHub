
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object search_Scope0 {
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

class search extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template2[String,List[Segment],play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(searchQuery: String, taggedSegments: List[Segment]):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.54*/("""

"""),_display_(/*3.2*/main("Searching Storyhub...")/*3.31*/{_display_(Seq[Any](format.raw/*3.32*/(""" """)))}/*3.34*/{_display_(Seq[Any](format.raw/*3.35*/("""
	"""),format.raw/*4.2*/("""<div class="container">
		<p class="title" id='query'>
			"""),_display_(/*6.5*/searchQuery),format.raw/*6.16*/("""
		"""),format.raw/*7.3*/("""</p>
		<div id="all_stories">
			"""),_display_(/*9.5*/if(taggedSegments.length == 0)/*9.35*/ {_display_(Seq[Any](format.raw/*9.37*/(""" 
				"""),format.raw/*10.5*/("""<p>No search results.</p>
			""")))}),format.raw/*11.5*/("""
			"""),_display_(/*12.5*/for(segment <- taggedSegments) yield /*12.35*/{_display_(Seq[Any](format.raw/*12.36*/("""
				"""),format.raw/*13.5*/("""<div class="story">
					<table>
						<col style="width:90%"><col style="width:10%">
						<tr>
							<td>
								<a class="title" href="/Story/"""),_display_(/*18.40*/segment/*18.47*/.getStoryId()),format.raw/*18.60*/("""/"""),_display_(/*18.62*/segment/*18.69*/.getSegmentId()),format.raw/*18.84*/(""""> """),_display_(/*18.88*/segment/*18.95*/.getTitle()),format.raw/*18.106*/("""</a>
							</td>
						</tr>
						<tr>
							<td>
								<div class="author"> """),_display_(/*23.31*/segment/*23.38*/.getAuthor()),format.raw/*23.50*/(""" """),format.raw/*23.51*/("""</dv>
							</td>
						</tr>
						<tr> 
							<td>
								<ul class="tags">
								"""),_display_(/*29.10*/for(tag <- segment.getTags()) yield /*29.39*/{_display_(Seq[Any](format.raw/*29.40*/("""
									"""),format.raw/*30.10*/("""<li><a href="/SearchTags/"""),_display_(/*30.36*/tag),format.raw/*30.39*/("""" class="tag">"""),_display_(/*30.54*/tag),format.raw/*30.57*/("""</a></li>
								""")))}),format.raw/*31.10*/("""
								"""),format.raw/*32.9*/("""</ul>
							</td>
						</tr>
						<tr>
							<td>
								<div class="content"> """),_display_(/*37.32*/Html(segment.displayContent())),format.raw/*37.62*/("""</div>
							</td>
						</tr>
					</table>
				</div>
			""")))}),format.raw/*42.5*/("""
		"""),format.raw/*43.3*/("""</div>
	</div>
""")))}))
      }
    }
  }

  def render(searchQuery:String,taggedSegments:List[Segment]): play.twirl.api.HtmlFormat.Appendable = apply(searchQuery,taggedSegments)

  def f:((String,List[Segment]) => play.twirl.api.HtmlFormat.Appendable) = (searchQuery,taggedSegments) => apply(searchQuery,taggedSegments)

  def ref: this.type = this

}


}

/**/
object search extends search_Scope0.search
              /*
                  -- GENERATED --
                  DATE: Sun Nov 15 16:20:28 EST 2015
                  SOURCE: C:/Users/katie/Documents/GitHub/storyhub/app/views/search.scala.html
                  HASH: a95dc9bf92905670e372c05cd77c73c47ce5dc49
                  MATRIX: 761->1|908->53|936->56|973->85|1011->86|1031->88|1069->89|1097->91|1181->150|1212->161|1241->164|1300->198|1338->228|1377->230|1410->236|1470->266|1501->271|1547->301|1586->302|1618->307|1793->455|1809->462|1843->475|1872->477|1888->484|1924->499|1955->503|1971->510|2004->521|2114->604|2130->611|2163->623|2192->624|2309->714|2354->743|2393->744|2431->754|2484->780|2508->783|2550->798|2574->801|2624->820|2660->829|2772->914|2823->944|2914->1005|2944->1008
                  LINES: 27->1|32->1|34->3|34->3|34->3|34->3|34->3|35->4|37->6|37->6|38->7|40->9|40->9|40->9|41->10|42->11|43->12|43->12|43->12|44->13|49->18|49->18|49->18|49->18|49->18|49->18|49->18|49->18|49->18|54->23|54->23|54->23|54->23|60->29|60->29|60->29|61->30|61->30|61->30|61->30|61->30|62->31|63->32|68->37|68->37|73->42|74->43
                  -- GENERATED --
              */
          