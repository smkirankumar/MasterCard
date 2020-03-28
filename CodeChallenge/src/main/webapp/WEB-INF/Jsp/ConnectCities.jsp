<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css"	rel="stylesheet"> 
<meta charset="ISO-8859-1">
<title>Master Card Code Challenge</title>
</head>
<body>
	<h2>Route Finder</h2>
	<div class="container">
		<form method="post">
			<fieldset class="form-group">
				<label>Enter the place of origin : </label> 
				<input type= "text" name = "origin" class= "form-control" required="required">
				<label>Enter the place of destination : </label> 
				<input type= "text" name = "destination" class= "form-control" required="required">
			</fieldset>
			<div>
				<button type="submit" class= "btn btn-success">Find route</button>
			</div>
			<div>
				<font> <h4>${Message} <h4> </font>
			</div>			
		</form>
	</div>	
</body>
</html>