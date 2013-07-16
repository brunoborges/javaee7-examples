/* SiteCatalyst code version: H.14. Copyright Omniture, Inc. More info available at http://www.omniture.com */

/************************** CONFIG SECTION ****************************************/
/* Specify the Report Suite(s) */
var s_account="sunjnetdev";
var sun_dynamicAccountSelection=true;
var sun_dynamicAccountList="sunglobal,sunjnet=java.net;sunjnetdev=."
/* Specify the Report Suite ID */
var s_siteid="jnet-other:";
if(window.location.hostname == "www.java.net") {var s_siteid="jnet-editorial:";}
if(window.location.hostname == "java.net") {var s_siteid="jnet-editorial:";}
if(window.location.hostname == "glassfish.java.net") {var s_siteid="jnet-glassfish:";}
if(window.location.hostname == "today.java.net") {var s_siteid="jnet-editorial:";}
if(window.location.hostname == "community.java.net") {var s_siteid="jnet-community:";}
if(window.location.hostname == "weblogs.java.net") {var s_siteid="jnet-weblogs:";}
if(window.location.hostname == "forums.java.net") {var s_siteid="jnet-forums:";}
if(window.location.hostname == "wiki.java.net") {var s_siteid="jnet-wiki:";}
if(window.location.hostname == "partners.java.net") {var s_siteid="jnet-partners:";}
if(window.location.hostname == "sun.java.net") {var s_siteid="jnet-partners:";}
if(window.location.hostname == "download.java.net") {var s_siteid="jnet-downloads:";}
if(typeof s_siteid=='undefined') {var s_siteid="suncom-error:";}
/* Remote Omniture JS call  */
var sun_ssl=(window.location.protocol.toLowerCase().indexOf("https")!=-1);
	if(sun_ssl == true) { var fullURL = "https://www.java.net/images/metrics_group1.js"; }
		else { var fullURL= "http://www.java.net/images/metrics_group1.js"; }
document.write("<sc" + "ript type=\"text/javascript\" src=\""+fullURL+"\"></sc" + "ript>");
/************************** END CONFIG SECTION **************************************/