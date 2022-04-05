<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>filmInStock</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">filmInStock</h1>
		<form method="post" action="<%=request.getContextPath()%>/filmInStockAction.jsp">
			<table class="table table-bordered text-center table-striped table-dark">
				<tr>
					<td>filmId</td>
					<td><input type="text" name="filmId"></td>
					<td>storeId</td>
					<td><input type="text" name="storeId"></td>
				</tr>
				<tr>
					<td colspan="4"><button type="submit" class="btn btn-primary float-right">재고확인</button></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>