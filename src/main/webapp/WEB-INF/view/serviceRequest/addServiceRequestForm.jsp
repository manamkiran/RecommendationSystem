<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html lang="en">
<head>
<title>Service Request Page</title>
<jsp:include page="../fragmentPages/header.jsp" /></head>
<body data-spy="scroll" data-target=".navbar">
	<div id="headerSection">
		<jsp:include page="../fragmentPages/navbar.jsp">
			<jsp:param name="imageIcon" value="️✋" />
		</jsp:include>
	</div>
	<!--Header Ends================================================ -->

	<div class="clearfix1" id="carouselSection"></div>

	<div class="container">
		<h2 class="text-center">Add New Service Request</h2>
		<form:form action="create" method="POST"
			modelAttribute="serviceRequest">
			<table>
				<tr>
					<td><h4 style="color: orangered">Request Type:</h4></td>
					<td><form:select path="category">
							<form:options items="${categories}" />
						</form:select></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Request Sub Type:</h4></td>
					<td><form:select path="subCategory">
							<form:options items="${subCategories}" />
						</form:select></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Request Name:</h4></td>
					<td><form:input path="requestName" required="required" /></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Request Description:</h4></td>
					<td><form:input path="description" required="required"/></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Schedule Type:</h4></td>
					<td><form:select path="schedule">
							<form:options items="${schedules}" />
						</form:select></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Requester Name:</h4></td>
					<td><form:input path="requesterName" required="required" /></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Requester Number:</h4></td>
					<td><form:input path="requesterNumber" required="required" /></td>
				</tr>
				<tr>
					<td><h4 style="color: orangered">Quantity Required:</h4></td>
					<td><form:input path="totalQuantityRequired" required="required" type="number" min="1" /></td>
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
	<script type="text/javascript">
	$(document).ready(function(){
      $("#category").change(function(){
            
            var url = "${pageContext.request.contextPath}/service/subCategory/"+$("#category option:selected").val();
			$('#subCategory').html('');
            $.getJSON(url, function (data) {
                $.each(data, function (index, value) {
                    $('#subCategory').append('<option value="' + value + '">' + value + '</option>');
                });
            });
        });
        });
	
	</script>
</body>
</html>