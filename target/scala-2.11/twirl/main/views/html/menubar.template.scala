
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object menubar_Scope0 {
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

class menubar extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.1*/("""<script>(function(d, s, id) """),format.raw/*1.29*/("""{"""),format.raw/*1.30*/("""
	 """),format.raw/*2.3*/("""var js, fjs = d.getElementsByTagName(s)[0];
		 if (d.getElementById(id)) 
		 	return;
		 js = d.createElement(s); js.id = id;
		 js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.5";
		 fjs.parentNode.insertBefore(js, fjs);
	"""),format.raw/*8.2*/("""}"""),format.raw/*8.3*/("""(document, 'script', 'facebook-jssdk'));
</script>

<nav class="navbar navbar-default">
  	<div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
        <a class="navbar-left homepage" href="/">StoryHub</a>
	    	<form name = "searchform" class="navbar-form navbar-left" role="search" method="POST" action="">
			  <div class="form-group">
          <a href="#" title="Search" data-toggle="popover" data-trigger="focus" data-content="This field is required! Cannot be whitespace!" data-placement="bottom"><input id="search-text" type="text" onclick="this.select()" class="form-control" placeholder="Search e.g. Paris tbt" required></a>
			    
			  </div>
			  <button type="submit" onclick="returnSearchByTag(event)" class="btn btn-default" >Search Tags</button>
        <button type="submit" onclick="returnSearchByTitle(event)" class="btn btn-default" >Search Title</button>
      <button id="NewStoryButton" type="button" class="btn btn-primary" onclick="new1Story()" style="margin-left: 20px; display: none;">
        <span class="glyphicon glyphicon-star" aria-hidden="true"></span> New Story
      </button>
			</form>
		</div>
		<div class="fb-login-button" data-max-rows="1" data-size="medium" data-show-faces="false" data-auto-logout-link="true" scope="public_profile,email" onlogin="checkLoginState();" style="float:right"></div>
		<div id="status" style="float:right">
		</div>
	</div>
</nav>
<div id="fb-root"></div>




<script>
  //Returns search results when search button is clicked
  function returnSearchByTag(event)"""),format.raw/*40.36*/("""{"""),format.raw/*40.37*/("""
    """),format.raw/*41.5*/("""search = $('#search-text').val();
    if (!search || search.trim() == "")"""),format.raw/*42.40*/("""{"""),format.raw/*42.41*/("""
      """),format.raw/*43.7*/("""$('[data-toggle="popover"]').popover('show');   
      event.preventDefault();
      setTimeout(function()"""),format.raw/*45.28*/("""{"""),format.raw/*45.29*/(""" """),format.raw/*45.30*/("""$('[data-toggle="popover"]').popover('destroy'); """),format.raw/*45.79*/("""}"""),format.raw/*45.80*/(""", 2000);
    """),format.raw/*46.5*/("""}"""),format.raw/*46.6*/("""

    """),format.raw/*48.5*/("""searchQueries = search.split(" ");
    var searchURL = "/SearchTags/";
    for(var i = 0 ;i <searchQueries.length; i++)"""),format.raw/*50.49*/("""{"""),format.raw/*50.50*/("""
      """),format.raw/*51.7*/("""//Remove white space from searches 
      searchQueries[i] = searchQueries[i].replace(/^\s+|\s+$/g, "")

      //Add queries to URL
      if(i == searchQueries.length -1)"""),format.raw/*55.39*/("""{"""),format.raw/*55.40*/("""
        """),format.raw/*56.9*/("""searchURL += searchQueries[i];
      """),format.raw/*57.7*/("""}"""),format.raw/*57.8*/(""" """),format.raw/*57.9*/("""else"""),format.raw/*57.13*/("""{"""),format.raw/*57.14*/(""" """),format.raw/*57.15*/("""searchURL += searchQueries[i]+"+"; """),format.raw/*57.50*/("""}"""),format.raw/*57.51*/("""
    """),format.raw/*58.5*/("""}"""),format.raw/*58.6*/("""
    """),format.raw/*59.5*/("""document.searchform.action = searchURL;
  """),format.raw/*60.3*/("""}"""),format.raw/*60.4*/("""

  """),format.raw/*62.3*/("""function returnSearchByTitle(event)"""),format.raw/*62.38*/("""{"""),format.raw/*62.39*/("""
    """),format.raw/*63.5*/("""search = $('#search-text').val();
    if (!search || search.trim() == "")"""),format.raw/*64.40*/("""{"""),format.raw/*64.41*/("""
      """),format.raw/*65.7*/("""$('[data-toggle="popover"]').popover('show');   
      event.preventDefault();
      setTimeout(function()"""),format.raw/*67.28*/("""{"""),format.raw/*67.29*/(""" """),format.raw/*67.30*/("""$('[data-toggle="popover"]').popover('destroy'); """),format.raw/*67.79*/("""}"""),format.raw/*67.80*/(""", 2000);
    """),format.raw/*68.5*/("""}"""),format.raw/*68.6*/("""
    """),format.raw/*69.5*/("""searchQueries = search.split(" ");
    var searchURL = "/SearchTitles/";
    for(var i = 0 ;i <searchQueries.length; i++)"""),format.raw/*71.49*/("""{"""),format.raw/*71.50*/("""
      """),format.raw/*72.7*/("""//Signal white space in url
      searchQueries[i] = searchQueries[i].replace(/^\s+|\s+$/g, "");
      //Add queries to URL
      if(i == searchQueries.length-1)"""),format.raw/*75.38*/("""{"""),format.raw/*75.39*/("""
        """),format.raw/*76.9*/("""searchURL += searchQueries[i];
      """),format.raw/*77.7*/("""}"""),format.raw/*77.8*/(""" """),format.raw/*77.9*/("""else"""),format.raw/*77.13*/("""{"""),format.raw/*77.14*/(""" """),format.raw/*77.15*/("""searchURL += searchQueries[i]+"+"; """),format.raw/*77.50*/("""}"""),format.raw/*77.51*/("""
    """),format.raw/*78.5*/("""}"""),format.raw/*78.6*/("""
    """),format.raw/*79.5*/("""document.searchform.action = searchURL;
  """),format.raw/*80.3*/("""}"""),format.raw/*80.4*/("""

  """),format.raw/*82.3*/("""function new1Story() """),format.raw/*82.24*/("""{"""),format.raw/*82.25*/("""
    """),format.raw/*83.5*/("""window.location = "/NewStory";
"""),format.raw/*84.1*/("""}"""),format.raw/*84.2*/("""
  """),format.raw/*85.3*/("""// This is called with the results from from FB.getLoginStatus().
  function statusChangeCallback(response) """),format.raw/*86.43*/("""{"""),format.raw/*86.44*/("""
    """),format.raw/*87.5*/("""// The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') """),format.raw/*91.42*/("""{"""),format.raw/*91.43*/("""
      """),format.raw/*92.7*/("""// Logged into your app and Facebook.
      testAPI();
      $('#NewStoryButton').css("display", "inline");
    """),format.raw/*95.5*/("""}"""),format.raw/*95.6*/(""" """),format.raw/*95.7*/("""else """),format.raw/*95.12*/("""{"""),format.raw/*95.13*/("""
      """),format.raw/*96.7*/("""// The person is logged into Facebook, but not your app.
      document.getElementById('status').innerHTML = 'Please log ' +
        'into this app to make contributions.';
        $('#NewStoryButton').css("display", "none");
        $.ajax("""),format.raw/*100.16*/("""{"""),format.raw/*100.17*/("""
          """),format.raw/*101.11*/("""method: "POST",
          url: "/NoFacebookName",
          success: function(data)"""),format.raw/*103.34*/("""{"""),format.raw/*103.35*/("""
            """),format.raw/*104.13*/("""if (data == "changed")"""),format.raw/*104.35*/("""{"""),format.raw/*104.36*/("""
              """),format.raw/*105.15*/("""window.location.reload(true);
            """),format.raw/*106.13*/("""}"""),format.raw/*106.14*/("""
          """),format.raw/*107.11*/("""}"""),format.raw/*107.12*/(""" 
        """),format.raw/*108.9*/("""}"""),format.raw/*108.10*/(""");
    """),format.raw/*109.5*/("""}"""),format.raw/*109.6*/(""" 
  """),format.raw/*110.3*/("""}"""),format.raw/*110.4*/("""

  """),format.raw/*112.3*/("""// This function is called when someone finishes with the Login
  // Button.  See the onlogin handler attached to it in the sample
  // code below.
  function checkLoginState() """),format.raw/*115.30*/("""{"""),format.raw/*115.31*/("""
    """),format.raw/*116.5*/("""FB.getLoginStatus(function(response) """),format.raw/*116.42*/("""{"""),format.raw/*116.43*/("""
      """),format.raw/*117.7*/("""statusChangeCallback(response);
    """),format.raw/*118.5*/("""}"""),format.raw/*118.6*/(""");
  """),format.raw/*119.3*/("""}"""),format.raw/*119.4*/("""

  """),format.raw/*121.3*/("""window.fbAsyncInit = function() """),format.raw/*121.35*/("""{"""),format.raw/*121.36*/("""
  """),format.raw/*122.3*/("""FB.init("""),format.raw/*122.11*/("""{"""),format.raw/*122.12*/("""
    """),format.raw/*123.5*/("""appId      : '1521346481512275',
    cookie     : true,  // enable cookies to allow the server to access 
                        // the session
    xfbml      : true,  // parse social plugins on this page
    version    : 'v2.2' // use version 2.2
  """),format.raw/*128.3*/("""}"""),format.raw/*128.4*/(""");

  // Now that we've initialized the JavaScript SDK, we call 
  // FB.getLoginStatus().  This function gets the state of the
  // person visiting this page and can return one of three states to
  // the callback you provide.  They can be:
  //
  // 1. Logged into your app ('connected')
  // 2. Logged into Facebook, but not your app ('not_authorized')
  // 3. Not logged into Facebook and can't tell if they are logged into
  //    your app or not.
  //
  // These three cases are handled in the callback function.

  FB.getLoginStatus(function(response) """),format.raw/*142.40*/("""{"""),format.raw/*142.41*/("""
    """),format.raw/*143.5*/("""statusChangeCallback(response);
  """),format.raw/*144.3*/("""}"""),format.raw/*144.4*/(""");

  """),format.raw/*146.3*/("""}"""),format.raw/*146.4*/(""";

  // Load the SDK asynchronously
  (function(d, s, id) """),format.raw/*149.23*/("""{"""),format.raw/*149.24*/("""
    """),format.raw/*150.5*/("""var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  """),format.raw/*155.3*/("""}"""),format.raw/*155.4*/("""(document, 'script', 'facebook-jssdk'));

  // Here we run a very simple test of the Graph API after login is
  // successful.  See statusChangeCallback() for when this call is made.
  function testAPI() """),format.raw/*159.22*/("""{"""),format.raw/*159.23*/("""
    """),format.raw/*160.5*/("""FB.api('/me', function(response) """),format.raw/*160.38*/("""{"""),format.raw/*160.39*/("""
      """),format.raw/*161.7*/("""document.getElementById('status').innerHTML =
        'Thanks for logging in, ' + response.name + '!';
      $.ajax("""),format.raw/*163.14*/("""{"""),format.raw/*163.15*/("""
        """),format.raw/*164.9*/("""method: "POST",
        url: "/FacebookName",
        data: """),format.raw/*166.15*/("""{"""),format.raw/*166.16*/(""" """),format.raw/*166.17*/("""name: response.name """),format.raw/*166.37*/("""}"""),format.raw/*166.38*/(""",
        success: function(data)"""),format.raw/*167.32*/("""{"""),format.raw/*167.33*/("""
          """),format.raw/*168.11*/("""if (data == "changed")"""),format.raw/*168.33*/("""{"""),format.raw/*168.34*/("""
            """),format.raw/*169.13*/("""window.location.reload(true);
          """),format.raw/*170.11*/("""}"""),format.raw/*170.12*/("""
        """),format.raw/*171.9*/("""}"""),format.raw/*171.10*/("""  
      """),format.raw/*172.7*/("""}"""),format.raw/*172.8*/(""");
    """),format.raw/*173.5*/("""}"""),format.raw/*173.6*/(""");
  """),format.raw/*174.3*/("""}"""),format.raw/*174.4*/("""
"""),format.raw/*175.1*/("""</script>"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


}

/**/
object menubar extends menubar_Scope0.menubar
              /*
                  -- GENERATED --
                  DATE: Sun Nov 15 18:52:17 EST 2015
                  SOURCE: C:/Users/katie/Documents/GitHub/storyhub/app/views/menubar.scala.html
                  HASH: a322e17d0f4690420711115733fe5dfcfc2ff3c8
                  MATRIX: 831->0|886->28|914->29|943->32|1209->272|1236->273|2877->1886|2906->1887|2938->1892|3039->1965|3068->1966|3102->1973|3236->2079|3265->2080|3294->2081|3371->2130|3400->2131|3440->2144|3468->2145|3501->2151|3648->2270|3677->2271|3711->2278|3909->2448|3938->2449|3974->2458|4038->2495|4066->2496|4094->2497|4126->2501|4155->2502|4184->2503|4247->2538|4276->2539|4308->2544|4336->2545|4368->2550|4437->2592|4465->2593|4496->2597|4559->2632|4588->2633|4620->2638|4721->2711|4750->2712|4784->2719|4918->2825|4947->2826|4976->2827|5053->2876|5082->2877|5122->2890|5150->2891|5182->2896|5331->3017|5360->3018|5394->3025|5583->3186|5612->3187|5648->3196|5712->3233|5740->3234|5768->3235|5800->3239|5829->3240|5858->3241|5921->3276|5950->3277|5982->3282|6010->3283|6042->3288|6111->3330|6139->3331|6170->3335|6219->3356|6248->3357|6280->3362|6338->3393|6366->3394|6396->3397|6532->3505|6561->3506|6593->3511|6893->3783|6922->3784|6956->3791|7095->3903|7123->3904|7151->3905|7184->3910|7213->3911|7247->3918|7517->4159|7547->4160|7587->4171|7699->4254|7729->4255|7771->4268|7822->4290|7852->4291|7896->4306|7967->4348|7997->4349|8037->4360|8067->4361|8105->4371|8135->4372|8170->4379|8199->4380|8231->4384|8260->4385|8292->4389|8498->4566|8528->4567|8561->4572|8627->4609|8657->4610|8692->4617|8756->4653|8785->4654|8818->4659|8847->4660|8879->4664|8940->4696|8970->4697|9001->4700|9038->4708|9068->4709|9101->4714|9380->4965|9409->4966|9997->5525|10027->5526|10060->5531|10122->5565|10151->5566|10185->5572|10214->5573|10301->5631|10331->5632|10364->5637|10611->5856|10640->5857|10873->6061|10903->6062|10936->6067|10998->6100|11028->6101|11063->6108|11208->6224|11238->6225|11275->6234|11364->6294|11394->6295|11424->6296|11473->6316|11503->6317|11565->6350|11595->6351|11635->6362|11686->6384|11716->6385|11758->6398|11827->6438|11857->6439|11894->6448|11924->6449|11961->6458|11990->6459|12025->6466|12054->6467|12087->6472|12116->6473|12145->6474
                  LINES: 32->1|32->1|32->1|33->2|39->8|39->8|71->40|71->40|72->41|73->42|73->42|74->43|76->45|76->45|76->45|76->45|76->45|77->46|77->46|79->48|81->50|81->50|82->51|86->55|86->55|87->56|88->57|88->57|88->57|88->57|88->57|88->57|88->57|88->57|89->58|89->58|90->59|91->60|91->60|93->62|93->62|93->62|94->63|95->64|95->64|96->65|98->67|98->67|98->67|98->67|98->67|99->68|99->68|100->69|102->71|102->71|103->72|106->75|106->75|107->76|108->77|108->77|108->77|108->77|108->77|108->77|108->77|108->77|109->78|109->78|110->79|111->80|111->80|113->82|113->82|113->82|114->83|115->84|115->84|116->85|117->86|117->86|118->87|122->91|122->91|123->92|126->95|126->95|126->95|126->95|126->95|127->96|131->100|131->100|132->101|134->103|134->103|135->104|135->104|135->104|136->105|137->106|137->106|138->107|138->107|139->108|139->108|140->109|140->109|141->110|141->110|143->112|146->115|146->115|147->116|147->116|147->116|148->117|149->118|149->118|150->119|150->119|152->121|152->121|152->121|153->122|153->122|153->122|154->123|159->128|159->128|173->142|173->142|174->143|175->144|175->144|177->146|177->146|180->149|180->149|181->150|186->155|186->155|190->159|190->159|191->160|191->160|191->160|192->161|194->163|194->163|195->164|197->166|197->166|197->166|197->166|197->166|198->167|198->167|199->168|199->168|199->168|200->169|201->170|201->170|202->171|202->171|203->172|203->172|204->173|204->173|205->174|205->174|206->175
                  -- GENERATED --
              */
          