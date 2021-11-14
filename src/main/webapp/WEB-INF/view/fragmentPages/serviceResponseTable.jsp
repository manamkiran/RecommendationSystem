<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="rate" value="<%=request.getParameter(\"rate\")%>" />

<table id="serviceResponseList" class="table table-striped">
	<thead>
		<tr>
			<th>Id</th>
			<th>Category</th>
			<th>Sub Category</th>
			<th>Request Name</th>
			<th>Description</th>
			<th>Provider Name</th>
			<th>Quantity Provided</th>
			<th>Quantity Required</th>
			<th>Rating</th>
			<th>Comment</th>
			<th>Responded Date</th>
			<c:if test="${rate}">
				<th>Rate</th>
			</c:if>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="serviceResponse" items="${serviceResponses}">
			<tr>
				<td>${serviceResponse.id}</td>
				<td>${serviceResponse.serviceRequest.category}</td>
				<td>${serviceResponse.serviceRequest.subCategory}</td>
				<td>${serviceResponse.serviceRequest.requestName}</td>
				<td>${serviceResponse.serviceRequest.description}</td>
				<td><a
					href="/user/${serviceResponse.user.userName}/viewdetails">${serviceResponse.user.userName}</a></td>
				<td>${serviceResponse.quantityProvided}</td>
				<td>${serviceResponse.serviceRequest.totalQuantityRequired}</td>
				<td>${serviceResponse.rating}</td>
				<td>${serviceResponse.comment}</td>
				<td>${serviceResponse.createdDate}</td>
				<c:if test="${rate}">
					<td><a href="/serviceResponse/${serviceResponse.id}/rating" class="btn btn-outline-success">Rate</a></td>
				</c:if>
			</tr>
		</c:forEach>
	</tbody>

	</tbody>
</table>