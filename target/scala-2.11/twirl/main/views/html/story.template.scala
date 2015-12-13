
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object story_Scope0 {
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

class story extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template4[Integer,List[Integer],Boolean,Boolean,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(myStory: Integer, mySegs: List[Integer], loggedin: Boolean, isClosed: Boolean):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.81*/("""


"""),_display_(/*4.2*/main("")/*4.10*/{_display_(Seq[Any](format.raw/*4.11*/("""
	"""),format.raw/*5.2*/("""<link rel="stylesheet" media="screen" href=""""),_display_(/*5.47*/routes/*5.53*/.Assets.versioned("stylesheets/story.css")),format.raw/*5.95*/("""">
""")))}/*6.2*/ {_display_(Seq[Any](format.raw/*6.4*/("""
	"""),format.raw/*7.2*/("""<div id="fb-root"></div>
	<script>
	function doFacebookStuff(d, s, id)"""),format.raw/*9.36*/("""{"""),format.raw/*9.37*/("""
		 """),format.raw/*10.4*/("""var js, fjs = d.getElementsByTagName(s)[0];
		  js = d.createElement(s); js.id = id;
		  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.5";
		  fjs.parentNode.insertBefore(js, fjs);
	"""),format.raw/*14.2*/("""}"""),format.raw/*14.3*/("""
	"""),format.raw/*15.2*/("""(function(d, s, id) """),format.raw/*15.22*/("""{"""),format.raw/*15.23*/("""
	  """),format.raw/*16.4*/("""var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.5";
	  fjs.parentNode.insertBefore(js, fjs);
	"""),format.raw/*21.2*/("""}"""),format.raw/*21.3*/("""(document, 'script', 'facebook-jssdk'));
	</script>
	<div id="all_segments">
		
	</div>
	<div id="shareButtons">
		<div id="twitterShare">
			<a class="twitter-share-button"
			  href="https://twitter.com/intent/tweet?text=This story is awesome"
			  data-size="large">
			Tweet</a>
		</div>
		<div id='fb-button' class="fb-share-button" data-href="https://www.google.com" data-layout="button"></div>
		<form id="pdfForm" action="/ExportToPdf" method="Post" style="display: inline-block;">
			<input id="pdf-content" type="hidden" name="content" value="">
			<input type="submit" class="btn btn-primary export" onclick="exportToPdf()" value="Export To PDF">
		</form>	
		<form id="txtForm" action="/ExportToTxt" method="Post" style="display: inline-block;">
			<input id="content" type="hidden" name="content" value="">
		  	<input type="submit" class="btn btn-primary export" onclick="exportToTxt();" value="Export To Txt" style="margin-left: 10px;">
		</form>
	</div>
	<script>
		var storyId = """),_display_(/*44.18*/{myStory}),format.raw/*44.27*/(""";
		var global_segmentId;
		var global_story = []; //["""),format.raw/*46.29*/("""{"""),format.raw/*46.30*/(""""title": "...", "author": "...",..."""),format.raw/*46.65*/("""}"""),format.raw/*46.66*/(""", """),format.raw/*46.68*/("""{"""),format.raw/*46.69*/("""}"""),format.raw/*46.70*/("""]
		window.twttr = (function(d, s, id) """),format.raw/*47.38*/("""{"""),format.raw/*47.39*/("""
		  """),format.raw/*48.5*/("""var js, fjs = d.getElementsByTagName(s)[0],
		    t = window.twttr || """),format.raw/*49.27*/("""{"""),format.raw/*49.28*/("""}"""),format.raw/*49.29*/(""";
		  if (d.getElementById(id)) return t;
		  js = d.createElement(s);
		  js.id = id;
		  js.src = "https://platform.twitter.com/widgets.js";
		  fjs.parentNode.insertBefore(js, fjs);
		 
		  t._e = [];
		  t.ready = function(f) """),format.raw/*57.27*/("""{"""),format.raw/*57.28*/("""
		    """),format.raw/*58.7*/("""t._e.push(f);
		  """),format.raw/*59.5*/("""}"""),format.raw/*59.6*/(""";
		 
		  return t;
		"""),format.raw/*62.3*/("""}"""),format.raw/*62.4*/("""(document, "script", "twitter-wjs"));

		/**
		* Adds html of segments on page to form with id pdfForm
		*/
		function exportToPdf()"""),format.raw/*67.25*/("""{"""),format.raw/*67.26*/("""
			"""),format.raw/*68.4*/("""var content = '';
			for (var i = 0; i < global_story.length; i++)"""),format.raw/*69.49*/("""{"""),format.raw/*69.50*/("""
				"""),format.raw/*70.5*/("""content +=  "<h2>" + global_story[i].title + "</h2>\n";
				content += "<h3>" + global_story[i].author + "</h3>\n";
				content += "<h4> Tags: ";
				for (var j = 0; j < global_story[i].tags; j++)"""),format.raw/*73.51*/("""{"""),format.raw/*73.52*/("""
					"""),format.raw/*74.6*/("""content += global_story[i].tags[j] + " ";
				"""),format.raw/*75.5*/("""}"""),format.raw/*75.6*/("""
				"""),format.raw/*76.5*/("""content += "</h4>\n";
				content += global_story[i].HtmlContent + "<br/><br/>";
			"""),format.raw/*78.4*/("""}"""),format.raw/*78.5*/("""
			"""),format.raw/*79.4*/("""$("#pdf-content").attr("value", content);
		"""),format.raw/*80.3*/("""}"""),format.raw/*80.4*/("""


		"""),format.raw/*83.3*/("""function exportToTxt()"""),format.raw/*83.25*/("""{"""),format.raw/*83.26*/("""
			"""),format.raw/*84.4*/("""var content = '';
			for (var i = 0; i < global_story.length; i++)"""),format.raw/*85.49*/("""{"""),format.raw/*85.50*/("""
				"""),format.raw/*86.5*/("""content +=  "Title: " + global_story[i].title + "\n";
				content += "Author: " + global_story[i].author + "\n";
				content += "Tags: ";
				for (var j = 0; j < global_story[i].tags; j++)"""),format.raw/*89.51*/("""{"""),format.raw/*89.52*/("""
					"""),format.raw/*90.6*/("""content += global_story[i].tags[j] + " ";
				"""),format.raw/*91.5*/("""}"""),format.raw/*91.6*/("""
				"""),format.raw/*92.5*/("""content += "\n";
				content += "Content: " + global_story[i].content + "\n\n";
			"""),format.raw/*94.4*/("""}"""),format.raw/*94.5*/("""
			"""),format.raw/*95.4*/("""$("#content").attr("value", content);
		"""),format.raw/*96.3*/("""}"""),format.raw/*96.4*/("""

		"""),format.raw/*98.3*/("""/**
		* Adds segments html to story page by posting AJAX request to back end
		* param Segment id
		* param Parent id
		*/
		function addSegment(segmentId, parentId)"""),format.raw/*103.43*/("""{"""),format.raw/*103.44*/("""
			"""),format.raw/*104.4*/("""//Enable all buttons to view child segments
			var buttonLevel = ".btn" + parentId;
			$(buttonLevel).prop('disabled', false);
			var buttonForSegId = "#" + segmentId;
			//Check if button exists for segmentId
			//There won't be one for the root
			if ($(buttonForSegId).length > 0) """),format.raw/*110.38*/("""{"""),format.raw/*110.39*/("""
				"""),format.raw/*111.5*/("""$(buttonForSegId).prop('disabled', true);
			"""),format.raw/*112.4*/("""}"""),format.raw/*112.5*/("""
			"""),format.raw/*113.4*/("""//Check if segment of same level exists.
			var segmentLevel = ".segment" + parentId;
			if($(segmentLevel).length)"""),format.raw/*115.30*/("""{"""),format.raw/*115.31*/("""
				"""),format.raw/*116.5*/("""//If so, remove all children below
				$(segmentLevel).nextAll().remove();
				//And remove same level segment
				$(segmentLevel).remove();
			"""),format.raw/*120.4*/("""}"""),format.raw/*120.5*/("""
			"""),format.raw/*121.4*/("""//Remove segments that are at or below level
			while(global_story.length > parentId + 1)"""),format.raw/*122.45*/("""{"""),format.raw/*122.46*/("""
				"""),format.raw/*123.5*/("""global_story.pop();
			"""),format.raw/*124.4*/("""}"""),format.raw/*124.5*/("""

			"""),format.raw/*126.4*/("""$.ajax("""),format.raw/*126.11*/("""{"""),format.raw/*126.12*/("""
	          	"""),format.raw/*127.13*/("""type: 'POST',
	          	url: '/AddSegment',
	          	data: 
	          	"""),format.raw/*130.13*/("""{"""),format.raw/*130.14*/("""
	              	"""),format.raw/*131.17*/("""storyId: storyId,
	              	segmentId: segmentId
	          	"""),format.raw/*133.13*/("""}"""),format.raw/*133.14*/(""",
	          	success: function(JSONstory) 
	          	"""),format.raw/*135.13*/("""{"""),format.raw/*135.14*/("""       
	              	"""),format.raw/*136.17*/("""var story = JSON.parse(JSONstory);
	              	addJSONcontent(story, segmentId);
	              	addStoryGlobal(story);
	              	index += 1;
	              	if (index < segments.length)"""),format.raw/*140.45*/("""{"""),format.raw/*140.46*/("""
	              		"""),format.raw/*141.18*/("""addSegment(segments[index], segments[index - 1]);
	              	"""),format.raw/*142.17*/("""}"""),format.raw/*142.18*/("""
	              	"""),format.raw/*143.17*/("""global_segmentId = segmentId;
	              	var share_url = window.location.href.substring(0, window.location.href.length-1)+global_segmentId;
	              	var text = "This story is awesome";
	              	  $("#twitterShare").html('&nbsp;'); 
					  $("#twitterShare").html('<a href="https://twitter.com/share" class="twitter-share-button" data-url="' + share_url +'" data-size="large" data-text="' + text + '" data-count="none">Tweet</a>');
					twttr.widgets.load();
	              	var url = window.location.href;
	              	var arr = url.split("/");
	              	$(".fb-share-button").attr('data-href', share_url);
	              	$(".fb-share-button span iframe").attr("src", "https://www.facebook.com/v2.2/plugins/share_button.php?app_id=1521346481512275&channel=http%3A%2F%2Fstatic.ak.facebook.com%2Fconnect%2Fxd_arbiter%2FTlA_zCeMkxl.js%3Fversion%3D41%23cb%3Df6d84cf78%26domain%3D209.2.226.178%26origin%3Dhttp%253A%252F%252F209.2.226.178%253A9000%252Ff10891c338%26relation%3Dparent.parent&container_width=0&href=" + encodeURIComponent(share_url) + "&layout=button&locale=en_US&sdk=joey");
	          	"""),format.raw/*153.13*/("""}"""),format.raw/*153.14*/("""
	      	"""),format.raw/*154.9*/("""}"""),format.raw/*154.10*/(""");
		"""),format.raw/*155.3*/("""}"""),format.raw/*155.4*/("""

		"""),format.raw/*157.3*/("""function addStoryGlobal(storyJSON)"""),format.raw/*157.37*/("""{"""),format.raw/*157.38*/("""
			"""),format.raw/*158.4*/("""story = """),format.raw/*158.12*/("""{"""),format.raw/*158.13*/("""}"""),format.raw/*158.14*/(""";
			story["title"] = storyJSON["title"];
			story["author"] = storyJSON["author"];
			story["tags"] = storyJSON["tags"];
			story["HtmlContent"] = storyJSON["content"].replace(/\~\|\+\#/g, "\"");
			story["content"] = $(story["content"]).text();

			global_story.push(story);
		"""),format.raw/*166.3*/("""}"""),format.raw/*166.4*/("""

	   """),format.raw/*168.5*/("""/**
		* changes JSON returned by back end to html and appends to page
		* param storyJSON string
		* param segmentId of the bottom most node of story
		*/
		function addJSONcontent(storyJSON, segmentId)"""),format.raw/*173.48*/("""{"""),format.raw/*173.49*/("""
			"""),format.raw/*174.4*/("""// NewFork URL: /story/:storyId/:segmentId/NewFork
			var newForkURL = "/Story/" + """),_display_(/*175.34*/myStory),format.raw/*175.41*/(""" """),format.raw/*175.42*/("""+ "/" + segmentId + "/NewSegment";
			var story = '<div class="story segment' + storyJSON["parentSegId"] + '">';
			story += '<table> \
						<col style="width:90%"><col style="width:10%"> \
						<tr> \
							<td> \
								<div class="title">' + storyJSON["title"] + '</div> \
							</td>';
			"""),_display_(/*183.5*/if(loggedin && !isClosed)/*183.30*/{_display_(Seq[Any](format.raw/*183.31*/("""
				"""),format.raw/*184.5*/("""story += '<td> \
							<button type="button" class="fork btn btn-primary" onclick="location.href=\'' + newForkURL + '\';">+ Segment\
							</button> \
						</td>';
			""")))}),format.raw/*188.5*/("""
			"""),_display_(/*189.5*/if(isClosed)/*189.17*/{_display_(Seq[Any](format.raw/*189.18*/("""
				"""),format.raw/*190.5*/("""story += '<td> \
							<span style="border: red 2px solid; padding: 5px; border-radius: 5px; color: red;font-weight: bold;"> Closed Story </span> \
						</td>';
			""")))}),format.raw/*193.5*/("""
			"""),format.raw/*194.4*/("""story += '</tr> \
					<tr> \
						<td> \
							<div class="author">' + storyJSON["author"] + '</div> \
						</td> \
					</tr> \
					<tr> \
						<td> \
							<ul class="tags">';
			//add html for each tag
			for (var i = 0; i < storyJSON["tags"].length; i++)"""),format.raw/*204.54*/("""{"""),format.raw/*204.55*/("""

				"""),format.raw/*206.5*/("""if(storyJSON["tags"][i].length!=0)"""),format.raw/*206.39*/("""{"""),format.raw/*206.40*/("""
					"""),format.raw/*207.6*/("""story += '<li><a href="/SearchTags/'+storyJSON["tags"][i]+'" class="tag">' + storyJSON["tags"][i] + '</a></li>';
				"""),format.raw/*208.5*/("""}"""),format.raw/*208.6*/("""
			"""),format.raw/*209.4*/("""}"""),format.raw/*209.5*/("""
			"""),format.raw/*210.4*/("""story += '</ul> \
					</td> \
				</tr> \
				<tr> \
					<td> \
						<div class="content">' + storyJSON["content"].replace(/\~\|\+\#/g, "\"") + '</div> \
					</td> \
				</tr> \
				<tr> \
					<td>';
				// add button html for children nodes
				story += '<div class="children">';
				for (var i = 0; i < storyJSON["childrenid"].length; i++)"""),format.raw/*222.61*/("""{"""),format.raw/*222.62*/("""
					"""),format.raw/*223.6*/("""story += '<button type="button" id="' +  storyJSON["childrenid"][i] + '" class= "childbtn btn btn-primary btn' + segmentId + '" onclick="addSegment(' + storyJSON["childrenid"][i] + ',' + segmentId + ' );">' + storyJSON["childrentitle"][i] + '</button>';
				"""),format.raw/*224.5*/("""}"""),format.raw/*224.6*/("""
				"""),format.raw/*225.5*/("""story += '</div>';
			
			story += '</td> \
				</tr> \
			</table> \
		</div>';
			$('#all_segments').append(story);
			$("body").animate("""),format.raw/*232.22*/("""{"""),format.raw/*232.23*/(""" """),format.raw/*232.24*/("""scrollTop: $(document).height() """),format.raw/*232.56*/("""}"""),format.raw/*232.57*/(""", "slow");
		"""),format.raw/*233.3*/("""}"""),format.raw/*233.4*/("""

		"""),format.raw/*235.3*/("""var segments = """),_display_(/*235.19*/mySegs),format.raw/*235.25*/(""";
		var index  = 0;
		//starts adding segments to html of page
		$(document).ready(function() """),format.raw/*238.32*/("""{"""),format.raw/*238.33*/("""
			"""),format.raw/*239.4*/("""index = 0;
			addSegment(segments[0], -1);
		"""),format.raw/*241.3*/("""}"""),format.raw/*241.4*/(""");
	</script>
""")))}))
      }
    }
  }

  def render(myStory:Integer,mySegs:List[Integer],loggedin:Boolean,isClosed:Boolean): play.twirl.api.HtmlFormat.Appendable = apply(myStory,mySegs,loggedin,isClosed)

  def f:((Integer,List[Integer],Boolean,Boolean) => play.twirl.api.HtmlFormat.Appendable) = (myStory,mySegs,loggedin,isClosed) => apply(myStory,mySegs,loggedin,isClosed)

  def ref: this.type = this

}


}

/**/
object story extends story_Scope0.story
              /*
                  -- GENERATED --
                  DATE: Sat Dec 12 20:51:14 EST 2015
                  SOURCE: C:/Users/katie/Documents/GitHub/storyhub/app/views/story.scala.html
                  HASH: 80b3a816bcc1c128bc753ae7cb8832701fd03631
                  MATRIX: 776->1|950->80|979->84|995->92|1033->93|1061->95|1132->140|1146->146|1208->188|1229->192|1267->194|1295->196|1392->266|1420->267|1451->271|1679->472|1707->473|1736->475|1784->495|1813->496|1844->500|2106->735|2134->736|3158->1733|3188->1742|3270->1796|3299->1797|3362->1832|3391->1833|3421->1835|3450->1836|3479->1837|3546->1876|3575->1877|3607->1882|3705->1952|3734->1953|3763->1954|4021->2184|4050->2185|4084->2192|4129->2210|4157->2211|4206->2233|4234->2234|4394->2366|4423->2367|4454->2371|4548->2437|4577->2438|4609->2443|4833->2639|4862->2640|4895->2646|4968->2692|4996->2693|5028->2698|5139->2782|5167->2783|5198->2787|5269->2831|5297->2832|5329->2837|5379->2859|5408->2860|5439->2864|5533->2930|5562->2931|5594->2936|5810->3124|5839->3125|5872->3131|5945->3177|5973->3178|6005->3183|6115->3266|6143->3267|6174->3271|6241->3311|6269->3312|6300->3316|6494->3481|6524->3482|6556->3486|6869->3770|6899->3771|6932->3776|7005->3821|7034->3822|7066->3826|7210->3941|7240->3942|7273->3947|7445->4091|7474->4092|7506->4096|7624->4185|7654->4186|7687->4191|7738->4214|7767->4215|7800->4220|7836->4227|7866->4228|7908->4241|8014->4318|8044->4319|8090->4336|8186->4403|8216->4404|8301->4460|8331->4461|8384->4485|8609->4681|8639->4682|8686->4700|8781->4766|8811->4767|8857->4784|10011->5909|10041->5910|10078->5919|10108->5920|10141->5925|10170->5926|10202->5930|10265->5964|10295->5965|10327->5969|10364->5977|10394->5978|10424->5979|10731->6258|10760->6259|10794->6265|11025->6467|11055->6468|11087->6472|11199->6556|11228->6563|11258->6564|11584->6863|11619->6888|11659->6889|11692->6894|11894->7065|11926->7070|11948->7082|11988->7083|12021->7088|12219->7255|12251->7259|12545->7524|12575->7525|12609->7531|12672->7565|12702->7566|12736->7572|12881->7689|12910->7690|12942->7694|12971->7695|13003->7699|13378->8045|13408->8046|13442->8052|13728->8310|13757->8311|13790->8316|13958->8455|13988->8456|14018->8457|14079->8489|14109->8490|14150->8503|14179->8504|14211->8508|14255->8524|14283->8530|14406->8624|14436->8625|14468->8629|14541->8674|14570->8675
                  LINES: 27->1|32->1|35->4|35->4|35->4|36->5|36->5|36->5|36->5|37->6|37->6|38->7|40->9|40->9|41->10|45->14|45->14|46->15|46->15|46->15|47->16|52->21|52->21|75->44|75->44|77->46|77->46|77->46|77->46|77->46|77->46|77->46|78->47|78->47|79->48|80->49|80->49|80->49|88->57|88->57|89->58|90->59|90->59|93->62|93->62|98->67|98->67|99->68|100->69|100->69|101->70|104->73|104->73|105->74|106->75|106->75|107->76|109->78|109->78|110->79|111->80|111->80|114->83|114->83|114->83|115->84|116->85|116->85|117->86|120->89|120->89|121->90|122->91|122->91|123->92|125->94|125->94|126->95|127->96|127->96|129->98|134->103|134->103|135->104|141->110|141->110|142->111|143->112|143->112|144->113|146->115|146->115|147->116|151->120|151->120|152->121|153->122|153->122|154->123|155->124|155->124|157->126|157->126|157->126|158->127|161->130|161->130|162->131|164->133|164->133|166->135|166->135|167->136|171->140|171->140|172->141|173->142|173->142|174->143|184->153|184->153|185->154|185->154|186->155|186->155|188->157|188->157|188->157|189->158|189->158|189->158|189->158|197->166|197->166|199->168|204->173|204->173|205->174|206->175|206->175|206->175|214->183|214->183|214->183|215->184|219->188|220->189|220->189|220->189|221->190|224->193|225->194|235->204|235->204|237->206|237->206|237->206|238->207|239->208|239->208|240->209|240->209|241->210|253->222|253->222|254->223|255->224|255->224|256->225|263->232|263->232|263->232|263->232|263->232|264->233|264->233|266->235|266->235|266->235|269->238|269->238|270->239|272->241|272->241
                  -- GENERATED --
              */
          