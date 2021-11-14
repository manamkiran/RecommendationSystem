<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html lang="en">
<head>
<title>Service Request Page</title>
<link
	href="${pageContext.request.contextPath}/themes/css/jquery.dataTables.min.css"
	type="text/css" rel="stylesheet" />
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
		<h3 class="text-center">Service Requests</h3>
		<form:form class="form-inline  " action="list" method="GET"
			modelAttribute="filter">
			Category
			<form:select path="category" class="form-control"
				items="${categories}" />
			SubCategory
			<form:select path="subCategory" class="form-control">
				<form:options items="${subCategories}" />
			</form:select>
			<input type="submit" value="Search" class="btn btn-success" />
		</form:form>
		<jsp:include page="../fragmentPages/serviceRequestTable.jsp" />
	</div>
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
      $('.dataTables_length').addClass('bs-select');
      $("#category").change(function(){
            var url = "${pageContext.request.contextPath}/service/subCategory/"+$("#category option:selected").val();
			$('#subCategory').html('');
            $.getJSON(url, function (data) {
            $('#subCategory').append('<option value="ALL">ALL</option>');
                $.each(data, function (index, value) {
                    $('#subCategory').append('<option value="' + value + '">' + value + '</option>');
                });
            });
        });
        });
</script>
</body>
</html>