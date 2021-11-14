<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Social Service</title>
<jsp:include page="./fragmentPages/header.jsp" />
</head>
<body>
	<div id="headerSection">
		<div class="container">
			<h1 class="brand text-center">🙏</h1>
			<div class="navbar">
				<div class="nav-collapse">
					<ul class="nav">
						<%
						if (request.getSession().getAttribute("userName") != null) {
							out.print("<a href=\"/user/home\">HOME</a>");
						}
						%>
						<li class="nav-item"><a class="nav-link"
							href="#welcomeSection">Testimonials</a></li>
						<!-- <li class="nav-item"><a class="nav-link"
							href="#feedBackSection">FeedBack</a></li> -->
						<%
						if (request.getSession().getAttribute("userName") != null) {
							out.print("<a href=\"/user/logout\">LOGOUT</a>");
						}
						%>
					</ul>
				</div>
				<c:if
					test="<%=request.getSession().getAttribute(\"userName\") == null%>">
					<jsp:include page="./fragmentPages/login.jsp" />
				</c:if>
			</div>
		</div>
	</div>

	<jsp:include page="./carousel/socialServiceImageCarousel.jsp" />

	<div id="welcomeSection">
		<div class="container text-center">
			<h4>Be the change you wish to see in the world</h4>
			<jsp:include page="./carousel/quotationCarousel.jsp" />
			<br> <br> <br>

			<h2>Welcome!</h2>
			<p>We think sometimes that poverty is only being hungry, naked,
				and homeless. The poverty of being unwanted, unloved, and uncared
				for is the greatest poverty. We must start in our own homes to
				remedy this kind of poverty.</p>
		</div>
	</div>


	<!-- Sectionone block ends======================================== -->



	<jsp:include page="./carousel/testimonalCarousel.jsp" />

	<!-- Feedback -->
	<!--<jsp:include page="./fragmentPages/feedback.jsp" />-->
	<!-- Footer -->
	<jsp:include page="./fragmentPages/footer.jsp" />

	<jsp:include page="./fragmentPages/javascript.jsp" />

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