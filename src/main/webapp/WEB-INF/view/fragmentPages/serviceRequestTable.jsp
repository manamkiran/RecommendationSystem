<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="owner" value="<%=request.getParameter(\"owner\")%>" />

<table id="serviceRequestList" class="table table-striped">
	<thead>
		<tr>
			<th>Id</th>
			<th>Category</th>
			<th>Sub Category</th>
			<th>Request Name</th>
			<th>Description</th>
			<th>Requester Name</th>
			<th>Requester Number</th>
			<c:if test="${owner}">
				<th>Quantity Achieved</th>
			</c:if>
			<th>Quantity Required</th>
			<th>Creator Name</th>
			<th>Created Date</th>
			<th>${owner ? "Response Details" : "Accept Now"}</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="serviceRequest" items="${serviceRequests}">
			<tr>
				<td>${serviceRequest.id}</td>
				<td>${serviceRequest.category}</td>
				<td>${serviceRequest.subCategory}</td>
				<td>${serviceRequest.requestName}</td>
				<td>${serviceRequest.description}</td>
				<td>${serviceRequest.requesterName}</td>
				<td>${serviceRequest.requesterNumber}</td>
				<c:if test="${owner}">
					<td>${serviceRequest.totalQuantityAcquired}</td>
				</c:if>
				<td>${serviceRequest.totalQuantityRequired}</td>
				<td>${serviceRequest.creator.userName}</td>
				<td>${serviceRequest.createdDate}</td>
				<td><c:choose>
						<c:when test="${owner}">
							<a href="/service/${serviceRequest.id}/serviceResponses">View
								Responses</a>
						</c:when>
						<c:otherwise>
							<form action="/service/${serviceRequest.id}/donate" method="post">
								<div class="form-row align-items-center">
									<div class="col">
										<input type="number" class="form-control" name="quantityProvided"
											placeholder="amount" min="1" required>
									</div>
									<div class="col">
										<button type="submit" class="btn btn-primary">Donate</button>
									</div>
								</div>
							</form>
						</c:otherwise>
					</c:choose></td>
			</tr>
		</c:forEach>
	</tbody>

	</tbody>
</table>