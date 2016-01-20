<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form action = "upload" method = "POST" enctype = "multipart/form-data" modelAttribute ="model">
<div align="right">

Today: ${today}
</div>
<table>
	<tr>
<!-- 		<td>Your Video:<form:input path ="videoTitle"/></td> -->
		<td>YourVideo</td>
		<td><input type = "text"  name = "videoTitle"  value = ""/></td>
		<td><input type = "file" name = "videoFile" value = "Browse..."/></td>
	</tr>
	<tr>
		<td><input type = "submit" name = "upload" value = "Upload"/> </td>
	</tr>
	<tr></tr>
	<tr>	
		<td>Uploaded video:</td>
		<td>${originalFileName}</td>
	</tr>
	<tr>
		<td>Name:</td>
		<td>${videoTitle} </td>
	</tr>
	<tr>
		<td>Size:</td>
		<td>${videoSize}</td>
	</tr>
 </table>
</form:form>
</body>
</html>