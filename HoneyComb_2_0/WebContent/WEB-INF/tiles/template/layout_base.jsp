<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html>
<head>
<title><tiles:getAsString name="title" /></title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<link href="/HoneyComb_2_0/resources/css/home.css" rel="stylesheet"
	type="text/css" />
</head>
<body>

	<div id="page_container">

		<div id="page_header">

			<tiles:insertAttribute name="header" />

		</div>
		
	<div id ="page_center_contents_more">
	
		<div id="page_sidebar_more">

			<tiles:insertAttribute name="menu" />

		</div>

		<div id="page_content_more">

			<tiles:insertAttribute name="body" />
			
		</div>
	</div>
	
	
		<div id="page_footer">

			<tiles:insertAttribute name="footer" />

		</div>

	</div>

</body>
</html>


