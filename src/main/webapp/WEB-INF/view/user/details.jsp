<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>User Registration Page</title>
<jsp:include page="../fragmentPages/header.jsp" />
</head>
<body data-spy="scroll" data-target=".navbar">
	<div id="headerSection">
		<jsp:include page="../fragmentPages/navbar.jsp">
			<jsp:param name="imageIcon" value="️👏" />
		</jsp:include>
	</div>

	<div class="clearfix1" id="carouselSection"></div>

	<div class="container">
		<h1 class="text-center">User Details</h1>
		<table class="table table-bordered">
			<tr>
				<td><h4 style="color: orangered">User Name :</h4></td>
				<td>${user.userName}</td>
			</tr>
			<tr>
				<td><h4 style="color: orangered">Mobile Number :</h4></td>
				<td>${user.mobileNumber}</td>
			</tr>
			<tr>
				<td><h4 style="color: orangered">Email Id :</h4></td>
				<td>${user.emailId}</td>
			</tr>
		</table>
	</div>
	<!-- Wrapper end -->

	<!-- Footer -->
	<jsp:include page="../fragmentPages/footer.jsp" />

	<jsp:include page="../fragmentPages/javascript.jsp" />

</body>
</html>