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
		<div class="container">
			<h1 class="brand text-center">👇</h1>
			<div class="navbar">
				<div class="nav-collapse">
					<ul>
						<a href="/">MAINPAGE</a>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="clearfix1" id="carouselSection"></div>

	<div class="container">
		<h1 class="text-center">User Registration</h1>

		<form:form action="create" method="POST" modelAttribute="user">
			<table>
				<tr>
					<td><h4 style="color: orangered">User Name :</h4></td>
					<td><form:input path="userName" /></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">First Name :</h4></td>
					<td><form:input path="firstName" /></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Last Name :</h4></td>
					<td><form:input path="lastName" /></td>
				</tr>
				<!-- <tr>
					<td><h4 style="color: orangered">D.O.B :</h4></td>
					<td><input type="date" name="number"></td>
				</tr> -->
				<tr>
					<td><h4 style="color: orangered">Email ID :</h4></td>
					<td><form:input path="emailId" /></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Contact no :</h4></td>
					<td><form:input path="mobileNumber"
							onblur="ismobile(this.value)" /></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Interested Categories :</h4></td>
					<td><form:checkboxes path="interestedCategories"
							items="${categories}" /></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Password :</h4></td>
					<td><form:input type="password" path="password" /></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Confirm Password :</h4></td>
					<td><form:input type="password" path="confirmPassword" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="submit"></td>
				</tr>
			</table>
		</form:form>
	</div>
	<!-- Wrapper end -->

	<!-- Footer -->
	<jsp:include page="../fragmentPages/footer.jsp" />

	<jsp:include page="../fragmentPages/javascript.jsp" />

	<script>
	
  $(document).ready(function(){
	<%
		if (request.getAttribute("message") != null) {
			out.print("alert(\""+request.getAttribute("message")+"\")");
		}
		%>
		});
	</script>

</body>
</html>