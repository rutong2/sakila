<%@page import="java.util.*"%>
<%@page import="dao.StatsDataDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	StatsDataDao statsDataDao = new StatsDataDao ();

	// 1) customer별 총 amount
	List<Map<String, Object>> amountByCustomer = statsDataDao.amountByCustomer();
	
	// 2) rental_rate별 영화 갯수
	List<Map<String, Object>> filmCountByRentalRate = statsDataDao.filmCountByRentalRate();

	// 3) rating별 영화 갯수
	List<Map<String, Object>> filmCountByRating = statsDataDao.filmCountByRating();
	
	// 4) language별 영화 갯수
	List<Map<String, Object>> filmCountByLanguage = statsDataDao.filmCountByLanguage();
	
	// 5) length별 영화 갯수
	List<Map<String, Object>> filmCountByLength = statsDataDao.filmCountByLength();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>statsData</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">amountByCustomer(180이상)</h1>
		<table class="table table-bordered text-center table-striped table-dark">
			<thead>
				<tr>
					<th>고객 아이디</th>
					<th>고객 이름</th>
					<th>총 지불액</th>
				</tr>
			</thead>
			<tbody>
				<%
					for(Map<String, Object> m : amountByCustomer) {
				%>
						<tr>
							<td><%=m.get("customerId")%></td>
							<td><%=m.get("name")%></td>
							<td><%=m.get("total")%>$</td>
						</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<h1 class="text-center text-white">rental_rate별 영화 갯수</h1>
		<table class="table table-bordered text-center table-striped table-dark">
			<thead>
				<tr>
					<th>대여 비용</th>
					<th>영화 갯수</th>
				</tr>
			</thead>
			<tbody>
				<%
					for(Map<String, Object> m : filmCountByRentalRate) {
				%>
						<tr>
							<td><%=m.get("rentalRate")%></td>
							<td><%=m.get("total")%>개</td>
						</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<h1 class="text-center text-white">rating별 영화 갯수</h1>
		<table class="table table-bordered text-center table-striped table-dark">
			<thead>
				<tr>
					<th>등급</th>
					<th>영화 갯수</th>
				</tr>
			</thead>
			<tbody>
				<%
					for(Map<String, Object> m : filmCountByRating) {
				%>
						<tr>
							<td><%=m.get("rating")%></td>
							<td><%=m.get("total")%>개</td>
						</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<h1 class="text-center text-white">language별 영화 갯수</h1>
		<table class="table table-bordered text-center table-striped table-dark">
			<thead>
				<tr>
					<th>언어</th>
					<th>영화 갯수</th>
				</tr>
			</thead>
			<tbody>
				<%
					for(Map<String, Object> m : filmCountByLanguage) {
				%>
						<tr>
							<td><%=m.get("name")%></td>
							<td><%=m.get("total")%>개</td>
						</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<h1 class="text-center text-white">length별 영화 갯수</h1>
		<table class="table table-bordered text-center table-striped table-dark">
			<thead>
				<tr>
					<th>시간</th>
					<th>영화 갯수</th>
				</tr>
			</thead>
			<tbody>
				<%
					for(Map<String, Object> m : filmCountByLength) {
				%>
						<tr>
							<td><%=m.get("leng")%></td>
							<td><%=m.get("total")%>개</td>
						</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>