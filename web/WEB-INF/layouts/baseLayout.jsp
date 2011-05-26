<%@page trimDirectiveWhitespaces="true" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="/struts-jquery-tags" prefix="sj" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://htmlcompressor.googlecode.com/taglib/compressor" prefix="compress" %>

<compress:html enabled="true"
               compressJavaScript="true"
               jsCompressor="closure"
               removeComments="true"
               compressCss="true"
               removeIntertagSpaces="true"
               >

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title><tiles:insertAttribute name="mainTitle" ignore="true" /> - <tiles:insertAttribute name="title" ignore="true" /></title>

        <!-- jQuery headers -->
        <sj:head jqueryui="true" jquerytheme="ui-darkness" />

        <!-- Display Tag Style -->
        <link rel="stylesheet" type="text/css" href="<s:url value="/resources/displaytag.css"/>" />


        <style type="text/css">
            .ui-widget { font-size: 10pt; }
            #content {
                margin: 0 auto;
                width: 80%;
            }
            label {clear:both;display:block;}
            #messages {margin: 0 0 10px 0;}
        </style>
    </head>
    <body>
        <div id="wrapper">
            <div id="content">
                <tiles:insertAttribute name="body" />
            </div>
        </div>
    </body>
</html>
</compress:html>
