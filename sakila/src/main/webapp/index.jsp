<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Index</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">HOME</h1>
			<table class="table table-bordered text-center table-striped table-dark">
				<tr>
					<td><a href="<%=request.getContextPath()%>/storeList.jsp">Store List</a></td>
				</tr>
				<tr>
					<td><a href="<%=request.getContextPath()%>/staffList.jsp">Staff List</a></td>
				</tr>
				<tr>
					<td><a href="<%=request.getContextPath()%>/actorInfoList.jsp">ActorInfo List</a></td>
				</tr>
			</table>
	</div>
</body>
</html>