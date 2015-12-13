
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object index_Scope0 {
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

class index extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template5[String,List[Story],Integer,Integer,Integer,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String, storyList: List[Story], offset: Integer, totalStories: Integer, interval: Integer):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.100*/("""

"""),_display_(/*3.2*/main("Welcome to StoryHub")/*3.29*/{_display_(Seq[Any](format.raw/*3.30*/(""" """)))}/*3.32*/{_display_(Seq[Any](format.raw/*3.33*/("""

	"""),format.raw/*5.2*/("""<div class="container">
		<p class="title">
			"""),_display_(/*7.5*/title),format.raw/*7.10*/("""
		"""),format.raw/*8.3*/("""</p>
		<div id="all_stories">
			"""),_display_(/*10.5*/for(story <- storyList) yield /*10.28*/{_display_(Seq[Any](format.raw/*10.29*/("""
				"""),format.raw/*11.5*/("""<div class="story">
					<table>
						<col style="width:90%"><col style="width:10%">
						<tr>
							<td>
								<a class="title" href="/Story/"""),_display_(/*16.40*/story/*16.45*/.getStoryId()),format.raw/*16.58*/("""/0"> """),_display_(/*16.64*/story/*16.69*/.getRoot().getTitle()),format.raw/*16.90*/("""</a>
							</td>
						</tr>
						<tr>
							<td>
								<div class="author"> """),_display_(/*21.31*/story/*21.36*/.getRoot().getAuthor()),format.raw/*21.58*/(""" """),format.raw/*21.59*/("""</dv>
							</td>
						</tr>
						<tr> 
							<td>
								<ul class="tags">
									"""),_display_(/*27.11*/for(tag <- story.getRoot().getTags()) yield /*27.48*/{_display_(Seq[Any](format.raw/*27.49*/("""
										"""),format.raw/*28.11*/("""<li><a href="/SearchTags/"""),_display_(/*28.37*/tag),format.raw/*28.40*/("""" class="tag">"""),_display_(/*28.55*/tag),format.raw/*28.58*/("""</a></li>		
									""")))}),format.raw/*29.11*/("""
								"""),format.raw/*30.9*/("""</ul>
							</td>
						</tr>
						<tr>
							<td>
								<div class="content"> """),_display_(/*35.32*/Html(story.getRoot().displayContent())),format.raw/*35.70*/("""</div>
							</td>
						</tr>
					</table>
				</div>
			""")))}),format.raw/*40.5*/("""
		"""),format.raw/*41.3*/("""<div id="pageButton">
		"""),_display_(/*42.4*/if(offset - interval >= 0)/*42.30*/ {_display_(Seq[Any](format.raw/*42.32*/("""		
			"""),format.raw/*43.4*/("""<button id="PrevButton" style="float: left;" type="button" class="btn btn-primary" onclick="prevPage("""),_display_(/*43.106*/offset),format.raw/*43.112*/(""", """),_display_(/*43.115*/interval),format.raw/*43.123*/(""")">
		    	<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Previous Page
		  	</button>
		""")))}),format.raw/*46.4*/("""
		"""),_display_(/*47.4*/if(offset + interval < totalStories+1)/*47.42*/ {_display_(Seq[Any](format.raw/*47.44*/("""
	      	"""),format.raw/*48.9*/("""<button id="NextButton" style="float: right;" type="button" class="btn btn-primary" onclick="nextPage("""),_display_(/*48.112*/offset),format.raw/*48.118*/(""", """),_display_(/*48.121*/totalStories),format.raw/*48.133*/(""", """),_display_(/*48.136*/interval),format.raw/*48.144*/(""")">
	        	<span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span> Next Page
	      	</button>
	    """)))}),format.raw/*51.7*/("""
		"""),format.raw/*52.3*/("""<script>
			
			/* Redirects the window for the previous page button */
			function prevPage(i, interval) """),format.raw/*55.35*/("""{"""),format.raw/*55.36*/("""
				"""),format.raw/*56.5*/("""var newIndex = i - interval;
				window.location = "/" + newIndex.toString();
			"""),format.raw/*58.4*/("""}"""),format.raw/*58.5*/("""

			"""),format.raw/*60.4*/("""/* Redirects the window for the next page button */
			function nextPage(i, totalStories, interval) """),format.raw/*61.49*/("""{"""),format.raw/*61.50*/("""
				"""),format.raw/*62.5*/("""var newIndex = i + interval;
				window.location = "/" + newIndex.toString();
			"""),format.raw/*64.4*/("""}"""),format.raw/*64.5*/("""

		"""),format.raw/*66.3*/("""</script>
  		</div>
		</div>
	</div>
""")))}),format.raw/*70.2*/("""
"""))
      }
    }
  }

  def render(title:String,storyList:List[Story],offset:Integer,totalStories:Integer,interval:Integer): play.twirl.api.HtmlFormat.Appendable = apply(title,storyList,offset,totalStories,interval)

  def f:((String,List[Story],Integer,Integer,Integer) => play.twirl.api.HtmlFormat.Appendable) = (title,storyList,offset,totalStories,interval) => apply(title,storyList,offset,totalStories,interval)

  def ref: this.type = this

}


}

/**/
object index extends index_Scope0.index
              /*
                  -- GENERATED --
                  DATE: Sun Nov 15 18:52:17 EST 2015
                  SOURCE: C:/Users/katie/Documents/GitHub/storyhub/app/views/index.scala.html
                  HASH: 6f14a9a674730d179b408ec977a7d981449d3ea4
                  MATRIX: 781->1|975->99|1003->102|1038->129|1076->130|1096->132|1134->133|1163->136|1236->184|1261->189|1290->192|1350->226|1389->249|1428->250|1460->255|1635->403|1649->408|1683->421|1716->427|1730->432|1772->453|1882->536|1896->541|1939->563|1968->564|2086->655|2139->692|2178->693|2217->704|2270->730|2294->733|2336->748|2360->751|2413->773|2449->782|2561->867|2620->905|2711->966|2741->969|2792->994|2827->1020|2867->1022|2900->1028|3030->1130|3058->1136|3089->1139|3119->1147|3264->1262|3294->1266|3341->1304|3381->1306|3417->1315|3548->1418|3576->1424|3607->1427|3641->1439|3672->1442|3702->1450|3853->1571|3883->1574|4017->1680|4046->1681|4078->1686|4186->1767|4214->1768|4246->1773|4374->1873|4403->1874|4435->1879|4543->1960|4571->1961|4602->1965|4671->2004
                  LINES: 27->1|32->1|34->3|34->3|34->3|34->3|34->3|36->5|38->7|38->7|39->8|41->10|41->10|41->10|42->11|47->16|47->16|47->16|47->16|47->16|47->16|52->21|52->21|52->21|52->21|58->27|58->27|58->27|59->28|59->28|59->28|59->28|59->28|60->29|61->30|66->35|66->35|71->40|72->41|73->42|73->42|73->42|74->43|74->43|74->43|74->43|74->43|77->46|78->47|78->47|78->47|79->48|79->48|79->48|79->48|79->48|79->48|79->48|82->51|83->52|86->55|86->55|87->56|89->58|89->58|91->60|92->61|92->61|93->62|95->64|95->64|97->66|101->70
                  -- GENERATED --
              */
          