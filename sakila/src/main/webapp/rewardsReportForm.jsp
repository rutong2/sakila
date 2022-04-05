<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>rewardsReportForm</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">rewardsReport</h1>
		<form method="post" action="rewardsReportAction.jsp">
			<table class="table table-bordered text-center table-striped table-dark">
				<tr>
					<td>minMonthlyPurchases</td>
					<td><input type="text" name="minMonthlyPurchases"></td>
					<td>minDollarAmountPurchased</td>
					<td><input type="text" name="minDollarAmountPurchased"></td>
				</tr>
				<tr>
					<td colspan="4"><button type="submit" class="btn btn-primary float-right">고객 확인</button></td>
				</tr>
			</table>
		</form>
	</div>	
</body>
</html>