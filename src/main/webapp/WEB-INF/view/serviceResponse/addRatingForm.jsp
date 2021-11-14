<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Service Request Page</title>
<jsp:include page="../fragmentPages/header.jsp" /></head>
<body>
	<div id="headerSection">
		<jsp:include page="../fragmentPages/navbar.jsp">
			<jsp:param name="imageIcon" value="️⭐" />
		</jsp:include>
	</div>
	<!--Header Ends================================================ -->

	<div class="clearfix1" id="carouselSection"></div>
	
		<div class="container">
		<h2 class="text-center">Rating</h2>
		<form:form action="../addRating" method="POST"
			modelAttribute="serviceResponse">
			<table>
				<tr>
					<td><h4 style="color: orangered">Id:</h4></td>
					<td><form:input path="id" disabled="true" /></td>
				</tr>
				
				<tr>
					<td><h4 style="color: orangered">Request Name:</h4></td>
					<td><form:input path="serviceRequest.requestName" disabled="true" /></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Request Description:</h4></td>
					<td><form:input path="serviceRequest.description" disabled="true" /></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Rating:</h4></td>
					<td><form:input path="rating" required="required" type="number" min="1"	max="5" /></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Comment:</h4></td>
					<td><form:input path="comment" /></td>
				</tr>
				<tr>
					<td><input type="submit" value="Submit"></td>
				</tr>
			</table>
		</form:form>
	</div>
	<!-- Footer -->
	<jsp:include page="../fragmentPages/footer.jsp" />
	<jsp:include page="../fragmentPages/javascript.jsp" />
	
</body>
</html>