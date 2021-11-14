<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">
<head>
<title>User</title>
<jsp:include page="../fragmentPages/header.jsp" />
</head>
<body>
	<div id="headerSection">
		<jsp:include page="../fragmentPages/navbar.jsp" >
			<jsp:param name="imageIcon" value="️🙏" />
		</jsp:include>
	</div>
	<!--Header Ends================================================ -->

	<div class="clearfix1" id="carouselSection"></div>

	<div class="container">

		<h2 class="text-center">
			Service Responses for Request Name <b>${requestName}</b>
		</h2>

		<jsp:include page="../fragmentPages/serviceResponseTable.jsp" />
	</div>
	<!-- Wrapper end -->

	<!-- Footer -->
	<jsp:include page="../fragmentPages/footer.jsp" />

	<jsp:include page="../fragmentPages/javascript.jsp" />

</body>
</html>