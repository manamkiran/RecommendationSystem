<!DOCTYPE html>
<html lang="en">
<head>
<title><%=request.getSession().getAttribute("userName")%>'s Home</title>
<jsp:include page="../fragmentPages/header.jsp" />
</head>
<body>
	<div id="headerSection">
		<jsp:include page="../fragmentPages/navbar.jsp">
			<jsp:param name="imageIcon" value="️🏠" />
		</jsp:include>
	</div>
	<!--Header Ends================================================ -->

	<div class="clearfix1" id="carouselSection"></div>

	<div class="container">
		<h2 class="text-center">
			Welcome
			<%=request.getSession().getAttribute("userName")%>!
		</h2>
		<h3 class="text-center">Recommended Service Requests</h3>
		<jsp:include page="../fragmentPages/serviceRequestTable.jsp" />
	</div>
	<!-- Footer -->
	<jsp:include page="../fragmentPages/footer.jsp" />

	<jsp:include page="../fragmentPages/javascript.jsp" />

</body>
</html>