<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Service Request Page</title>
<jsp:include page="../fragmentPages/header.jsp" /></head>
<body>
	<div id="headerSection">
		<jsp:include page="../fragmentPages/navbar.jsp">
			<jsp:param name="imageIcon" value="️🙌" />
		</jsp:include>
	</div>
	<!--Header Ends================================================ -->

	<div class="clearfix1" id="carouselSection"></div>
	
	<div class="container">
		<h3 class="text-center">${message}</h3>
	</div>
	
	<!-- Footer -->
	<jsp:include page="../fragmentPages/footer.jsp" />
	<jsp:include page="../fragmentPages/javascript.jsp" />
	
</body>
</html>