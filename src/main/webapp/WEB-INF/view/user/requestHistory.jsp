<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">
<head>
<title>User</title>
<link
	href="${pageContext.request.contextPath}/themes/css/jquery.dataTables.min.css"
	type="text/css" rel="stylesheet" />
<jsp:include page="../fragmentPages/header.jsp" />
</head>
<body>
	<div id="headerSection">
		<jsp:include page="../fragmentPages/navbar.jsp">
			<jsp:param name="imageIcon" value="️🦸‍♀️" />
		</jsp:include>
	</div>
	<!--Header Ends================================================ -->

	<div class="clearfix1" id="carouselSection"></div>

	<div class="container">

		<h2 class="text-center">Your Request Service History</h2>

		<jsp:include page="../fragmentPages/serviceRequestTable.jsp">
			<jsp:param name="owner" value="true" />
		</jsp:include>
	</div>
	<!-- Wrapper end -->

	<!-- Footer -->
	<jsp:include page="../fragmentPages/footer.jsp" />

	<jsp:include page="../fragmentPages/javascript.jsp" />
	<script
		src="${pageContext.request.contextPath}/themes/js/jquery.dataTables.min.js"></script>
	
	<script type="text/javascript">
	$(document).ready(function() {
      $('#serviceRequestList').DataTable({
      	fixedHeader: {
            header: true,
            footer: true
        }
      });
      });
    </script>

</body>
</html>