
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object newStory_Scope0 {
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

class newStory extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template2[String,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String, postForm: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.35*/("""

"""),_display_(/*3.2*/main(title)/*3.13*/{_display_(Seq[Any](format.raw/*3.14*/("""
	"""),format.raw/*4.2*/("""<link rel="stylesheet" href="//cdn.quilljs.com/latest/quill.snow.css" />
	<script src="//cdn.quilljs.com/latest/quill.min.js"></script>
	<link rel="stylesheet" media="screen" href=""""),_display_(/*6.47*/routes/*6.53*/.Assets.versioned("stylesheets/textEditor.css")),format.raw/*6.100*/("""">
	<link rel="stylesheet" media="screen" href=""""),_display_(/*7.47*/routes/*7.53*/.Assets.versioned("stylesheets/newStory.css")),format.raw/*7.98*/("""">
""")))}/*8.2*/ {_display_(Seq[Any](format.raw/*8.4*/("""

	"""),format.raw/*10.2*/("""<div id="allcontent">
		<p class="title">
			"""),_display_(/*12.5*/title),format.raw/*12.10*/("""
		"""),format.raw/*13.3*/("""</p>
		<form method="POST" action="" id="newitemform" name=""""),_display_(/*14.57*/postForm),format.raw/*14.65*/("""" role="form" >
			<div class="form-group">
				<input type="hidden" name="content" value="" id="content">
			</div>
			<div class="form-group">
				<label for="title">Title </label><br/>
				<input id ="story-title" onclick=this.select() type="text" name="title" class="form-control"/> <br/>
			</div>
			<div class="form-group">
				<label for="quill"> Story Content </label><br/>
				<div id="quillcontainer">
					<!-- Tool bar for the text editor -->
					<div id="toolbar">
					  <span class="ql-format-group">
						<select title="Font" class="ql-font">
							<option value="sans-serif" selected="">Sans Serif</option>
							<option value="serif">Serif</option>
							<option value="monospace">Monospace</option>
						</select>
						<select title="Size" class="ql-size">
							<option value="10px">Small</option>
							<option value="13px" selected="">Normal</option>
							<option value="18px">Large</option>
							<option value="32px">Huge</option>
						</select>
						</span>
							<span class="ql-format-group">
							<span title="Bold" class="ql-format-button ql-bold"></span>
							<span class="ql-format-separator"></span>
							<span title="Italic" class="ql-format-button ql-italic"></span>
							<span class="ql-format-separator"></span>
							<span title="Underline" class="ql-format-button ql-underline"></span>
							<span class="ql-format-separator"></span>
							<span title="Strikethrough" class="ql-format-button ql-strike"></span>
						</span>
						<span class="ql-format-group">
						<select title="Text Color" class="ql-color">
							<option value="rgb(0, 0, 0)" label="rgb(0, 0, 0)" selected=""></option>
							<option value="rgb(230, 0, 0)" label="rgb(230, 0, 0)"></option>
							<option value="rgb(255, 153, 0)" label="rgb(255, 153, 0)"></option>
							<option value="rgb(255, 255, 0)" label="rgb(255, 255, 0)"></option>
							<option value="rgb(0, 138, 0)" label="rgb(0, 138, 0)"></option>
							<option value="rgb(0, 102, 204)" label="rgb(0, 102, 204)"></option>
							<option value="rgb(153, 51, 255)" label="rgb(153, 51, 255)"></option>
							<option value="rgb(255, 255, 255)" label="rgb(255, 255, 255)"></option>
							<option value="rgb(250, 204, 204)" label="rgb(250, 204, 204)"></option>
							<option value="rgb(255, 235, 204)" label="rgb(255, 235, 204)"></option>
							<option value="rgb(255, 255, 204)" label="rgb(255, 255, 204)"></option>
							<option value="rgb(204, 232, 204)" label="rgb(204, 232, 204)"></option>
							<option value="rgb(204, 224, 245)" label="rgb(204, 224, 245)"></option>
							<option value="rgb(235, 214, 255)" label="rgb(235, 214, 255)"></option>
							<option value="rgb(187, 187, 187)" label="rgb(187, 187, 187)"></option>
							<option value="rgb(240, 102, 102)" label="rgb(240, 102, 102)"></option>
							<option value="rgb(255, 194, 102)" label="rgb(255, 194, 102)"></option>
							<option value="rgb(255, 255, 102)" label="rgb(255, 255, 102)"></option>
							<option value="rgb(102, 185, 102)" label="rgb(102, 185, 102)"></option>
							<option value="rgb(102, 163, 224)" label="rgb(102, 163, 224)"></option>
							<option value="rgb(194, 133, 255)" label="rgb(194, 133, 255)"></option>
							<option value="rgb(136, 136, 136)" label="rgb(136, 136, 136)"></option>
							<option value="rgb(161, 0, 0)" label="rgb(161, 0, 0)"></option>
							<option value="rgb(178, 107, 0)" label="rgb(178, 107, 0)"></option>
							<option value="rgb(178, 178, 0)" label="rgb(178, 178, 0)"></option>
							<option value="rgb(0, 97, 0)" label="rgb(0, 97, 0)"></option>
							<option value="rgb(0, 71, 178)" label="rgb(0, 71, 178)"></option>
							<option value="rgb(107, 36, 178)" label="rgb(107, 36, 178)"></option>
							<option value="rgb(68, 68, 68)" label="rgb(68, 68, 68)"></option>
							<option value="rgb(92, 0, 0)" label="rgb(92, 0, 0)"></option>
							<option value="rgb(102, 61, 0)" label="rgb(102, 61, 0)"></option>
							<option value="rgb(102, 102, 0)" label="rgb(102, 102, 0)"></option>
							<option value="rgb(0, 55, 0)" label="rgb(0, 55, 0)"></option>
							<option value="rgb(0, 41, 102)" label="rgb(0, 41, 102)"></option>
							<option value="rgb(61, 20, 102)" label="rgb(61, 20, 102)"></option>
						</select>
						<span class="ql-format-separator"></span>
						<select title="Background Color" class="ql-background">
							<option value="rgb(0, 0, 0)" label="rgb(0, 0, 0)"></option>
							<option value="rgb(230, 0, 0)" label="rgb(230, 0, 0)"></option>
							<option value="rgb(255, 153, 0)" label="rgb(255, 153, 0)"></option>
							<option value="rgb(255, 255, 0)" label="rgb(255, 255, 0)"></option>
							<option value="rgb(0, 138, 0)" label="rgb(0, 138, 0)"></option>
							<option value="rgb(0, 102, 204)" label="rgb(0, 102, 204)"></option>
							<option value="rgb(153, 51, 255)" label="rgb(153, 51, 255)"></option>
							<option value="rgb(255, 255, 255)" label="rgb(255, 255, 255)" selected=""></option>
							<option value="rgb(250, 204, 204)" label="rgb(250, 204, 204)"></option>
							<option value="rgb(255, 235, 204)" label="rgb(255, 235, 204)"></option>
							<option value="rgb(255, 255, 204)" label="rgb(255, 255, 204)"></option>
							<option value="rgb(204, 232, 204)" label="rgb(204, 232, 204)"></option>
							<option value="rgb(204, 224, 245)" label="rgb(204, 224, 245)"></option>
							<option value="rgb(235, 214, 255)" label="rgb(235, 214, 255)"></option>
							<option value="rgb(187, 187, 187)" label="rgb(187, 187, 187)"></option>
							<option value="rgb(240, 102, 102)" label="rgb(240, 102, 102)"></option>
							<option value="rgb(255, 194, 102)" label="rgb(255, 194, 102)"></option>
							<option value="rgb(255, 255, 102)" label="rgb(255, 255, 102)"></option>
							<option value="rgb(102, 185, 102)" label="rgb(102, 185, 102)"></option>
							<option value="rgb(102, 163, 224)" label="rgb(102, 163, 224)"></option>
							<option value="rgb(194, 133, 255)" label="rgb(194, 133, 255)"></option>
							<option value="rgb(136, 136, 136)" label="rgb(136, 136, 136)"></option>
							<option value="rgb(161, 0, 0)" label="rgb(161, 0, 0)"></option>
							<option value="rgb(178, 107, 0)" label="rgb(178, 107, 0)"></option>
							<option value="rgb(178, 178, 0)" label="rgb(178, 178, 0)"></option>
							<option value="rgb(0, 97, 0)" label="rgb(0, 97, 0)"></option>
							<option value="rgb(0, 71, 178)" label="rgb(0, 71, 178)"></option>
							<option value="rgb(107, 36, 178)" label="rgb(107, 36, 178)"></option>
							<option value="rgb(68, 68, 68)" label="rgb(68, 68, 68)"></option>
							<option value="rgb(92, 0, 0)" label="rgb(92, 0, 0)"></option>
							<option value="rgb(102, 61, 0)" label="rgb(102, 61, 0)"></option>
							<option value="rgb(102, 102, 0)" label="rgb(102, 102, 0)"></option>
							<option value="rgb(0, 55, 0)" label="rgb(0, 55, 0)"></option>
							<option value="rgb(0, 41, 102)" label="rgb(0, 41, 102)"></option>
							<option value="rgb(61, 20, 102)" label="rgb(61, 20, 102)"></option>
						</select>
						</span>
							<span class="ql-format-group">
							<span title="List" class="ql-format-button ql-list"></span>
							<span class="ql-format-separator"></span>
							<span title="Bullet" class="ql-format-button ql-bullet"></span>
							<span class="ql-format-separator"></span>
							<select title="Text Alignment" class="ql-align">
							<option value="left" label="Left" selected=""></option>
							<option value="center" label="Center"></option>
							<option value="right" label="Right"></option>
							<option value="justify" label="Justify"></option>
						</select>
						</span>
						<span class="ql-format-group">
						<span title="Link" class="ql-format-button ql-link"></span>
						</span>
					</div>
					<!-- Actual Text Editor Box -->
					<div id="editor">
					  <div></div>
					</div>
				</div>
			</div>
			<!-- Quill library for Text Editor-->
			<script src="http://cdn.quilljs.com/latest/quill.js"></script>
			<div class="form-group">
				<label for="tags">Tags </label><br/>
				<input type="text" placeholder="e.g. #newyork #hippo #awkwardanimals" onclick=this.select() id="story-tags" name="tags" class="form-control"/> <br/>
			</div>
			<div id="errors"></div>
			<br/>
			<button onclick="goBack()" class="btn btn-default"> Back </button>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>

		<script>
			/* Back button onclick to go back to the last page */
			function goBack() """),format.raw/*162.22*/("""{"""),format.raw/*162.23*/("""
				"""),format.raw/*163.5*/("""event.preventDefault();
    			window.history.back();
			"""),format.raw/*165.4*/("""}"""),format.raw/*165.5*/("""

			"""),format.raw/*167.4*/("""// Text editor settings
			var fullEditor = new Quill('#editor', """),format.raw/*168.42*/("""{"""),format.raw/*168.43*/("""
			  """),format.raw/*169.6*/("""modules: """),format.raw/*169.15*/("""{"""),format.raw/*169.16*/("""
			    """),format.raw/*170.8*/("""'toolbar': """),format.raw/*170.19*/("""{"""),format.raw/*170.20*/(""" """),format.raw/*170.21*/("""container: '#toolbar' """),format.raw/*170.43*/("""}"""),format.raw/*170.44*/(""",
			    'link-tooltip': true
			  """),format.raw/*172.6*/("""}"""),format.raw/*172.7*/(""",
			  theme: 'snow'
			"""),format.raw/*174.4*/("""}"""),format.raw/*174.5*/(""");

			/* Makes sure the new story form submission is valid */
			function validateForm()
			"""),format.raw/*178.4*/("""{"""),format.raw/*178.5*/("""
				"""),format.raw/*179.5*/("""//Checks that content isn't just blanks/spaces/new lines
				var content = fullEditor.getHTML();
				var checkContent = content.replace(/<div>/g,"").replace(/<\/div>/g,"").replace(/<br>/g,"").replace(/<\/br>/g,"").replace(/&nbsp;/g,"").replace(/^\s+|\s+$/g, "");
				if (checkContent.length == 0)
				"""),format.raw/*183.5*/("""{"""),format.raw/*183.6*/("""
					"""),format.raw/*184.6*/("""document.getElementById("errors").innerHTML = "Please enter in story content.";
					return false;
				"""),format.raw/*186.5*/("""}"""),format.raw/*186.6*/("""
				"""),format.raw/*187.5*/("""//Checks that tile isn't just blanks/spaces/newlines
				var mytitle = $("#story-title").val();
				var checkTitle = mytitle.replace(/^\s+|\s+$/g, "");
				if(checkTitle.length == 0)
				"""),format.raw/*191.5*/("""{"""),format.raw/*191.6*/("""
					"""),format.raw/*192.6*/("""document.getElementById("errors").innerHTML = "Please enter in a title.";
					return false;
				"""),format.raw/*194.5*/("""}"""),format.raw/*194.6*/("""

				"""),format.raw/*196.5*/("""document.getElementById("content").value = content;
				return true;
			"""),format.raw/*198.4*/("""}"""),format.raw/*198.5*/("""

			"""),format.raw/*200.4*/("""/* On form submission check the inputs,
			 * attempts to get the new segment id and displays the new page
			 */
			$("#newitemform").submit(function(event)"""),format.raw/*203.44*/("""{"""),format.raw/*203.45*/("""
				"""),format.raw/*204.5*/("""event.preventDefault();
				var $form = $(this);
				url = window.location.href;
				// Make sure form inputs are valid
				var valid = validateForm();
				if(valid == true)"""),format.raw/*209.22*/("""{"""),format.raw/*209.23*/("""
					"""),format.raw/*210.6*/("""var posting = $.post(url,"""),format.raw/*210.31*/("""{"""),format.raw/*210.32*/("""
						"""),format.raw/*211.7*/("""title: $("#story-title").val(),
						content : document.getElementById("content").value , 
						tags: $("#story-tags").val() 
					"""),format.raw/*214.6*/("""}"""),format.raw/*214.7*/(""");
					// Make sure nothing when run making the new segment
					posting.done(function(segInfoString)"""),format.raw/*216.42*/("""{"""),format.raw/*216.43*/("""
						"""),format.raw/*217.7*/("""if (segInfoString.startsWith("not found")) """),format.raw/*217.50*/("""{"""),format.raw/*217.51*/("""
							"""),format.raw/*218.8*/("""window.location = "/Error/NewStoryError";
						"""),format.raw/*219.7*/("""}"""),format.raw/*219.8*/(""" """),format.raw/*219.9*/("""else if(segInfoString.startsWith("story closed"))"""),format.raw/*219.58*/("""{"""),format.raw/*219.59*/("""
							"""),format.raw/*220.8*/("""window.location = "/Error/StoryClosed";
						"""),format.raw/*221.7*/("""}"""),format.raw/*221.8*/("""else """),format.raw/*221.13*/("""{"""),format.raw/*221.14*/("""
							"""),format.raw/*222.8*/("""var segInfo = segInfoString.split(",");
							var storyId = segInfo[0];
							var segmentId = segInfo[1];
							window.location = "/Story/"+storyId.toString()+"/"+segmentId.toString();
						"""),format.raw/*226.7*/("""}"""),format.raw/*226.8*/("""
					"""),format.raw/*227.6*/("""}"""),format.raw/*227.7*/(""");
				"""),format.raw/*228.5*/("""}"""),format.raw/*228.6*/("""
			"""),format.raw/*229.4*/("""}"""),format.raw/*229.5*/(""");

		</script>
	</div>
""")))}))
      }
    }
  }

  def render(title:String,postForm:String): play.twirl.api.HtmlFormat.Appendable = apply(title,postForm)

  def f:((String,String) => play.twirl.api.HtmlFormat.Appendable) = (title,postForm) => apply(title,postForm)

  def ref: this.type = this

}


}

/**/
object newStory extends newStory_Scope0.newStory
              /*
                  -- GENERATED --
                  DATE: Sat Dec 12 13:08:54 EST 2015
                  SOURCE: C:/Users/katie/Documents/GitHub/storyhub/app/views/newStory.scala.html
                  HASH: 5bed77154795042682cc536d19b8d108bb81a824
                  MATRIX: 758->1|886->34|914->37|933->48|971->49|999->51|1207->233|1221->239|1289->286|1364->335|1378->341|1443->386|1464->390|1502->392|1532->395|1604->441|1630->446|1660->449|1748->510|1777->518|10289->9001|10319->9002|10352->9007|10437->9064|10466->9065|10499->9070|10593->9135|10623->9136|10657->9142|10695->9151|10725->9152|10761->9160|10801->9171|10831->9172|10861->9173|10912->9195|10942->9196|11005->9231|11034->9232|11086->9256|11115->9257|11236->9350|11265->9351|11298->9356|11627->9657|11656->9658|11690->9664|11821->9767|11850->9768|11883->9773|12098->9960|12127->9961|12161->9967|12286->10064|12315->10065|12349->10071|12449->10143|12478->10144|12511->10149|12697->10306|12727->10307|12760->10312|12962->10485|12992->10486|13026->10492|13080->10517|13110->10518|13145->10525|13306->10658|13335->10659|13466->10761|13496->10762|13531->10769|13603->10812|13633->10813|13669->10821|13745->10869|13774->10870|13803->10871|13881->10920|13911->10921|13947->10929|14021->10975|14050->10976|14084->10981|14114->10982|14150->10990|14372->11184|14401->11185|14435->11191|14464->11192|14499->11199|14528->11200|14560->11204|14589->11205
                  LINES: 27->1|32->1|34->3|34->3|34->3|35->4|37->6|37->6|37->6|38->7|38->7|38->7|39->8|39->8|41->10|43->12|43->12|44->13|45->14|45->14|193->162|193->162|194->163|196->165|196->165|198->167|199->168|199->168|200->169|200->169|200->169|201->170|201->170|201->170|201->170|201->170|201->170|203->172|203->172|205->174|205->174|209->178|209->178|210->179|214->183|214->183|215->184|217->186|217->186|218->187|222->191|222->191|223->192|225->194|225->194|227->196|229->198|229->198|231->200|234->203|234->203|235->204|240->209|240->209|241->210|241->210|241->210|242->211|245->214|245->214|247->216|247->216|248->217|248->217|248->217|249->218|250->219|250->219|250->219|250->219|250->219|251->220|252->221|252->221|252->221|252->221|253->222|257->226|257->226|258->227|258->227|259->228|259->228|260->229|260->229
                  -- GENERATED --
              */
          