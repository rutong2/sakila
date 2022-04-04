<%@page import="java.util.List"%>
<%@page import="dao.StaffListViewDao"%>
<%@page import="vo.StaffListView"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	StaffListViewDao staffListViewDao = new StaffListViewDao();
	List<StaffListView> list = staffListViewDao.selectStaffListView();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>staffListView</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">staffListView</h1>
		<table class="table table-bordered text-center table-striped table-dark">
			<thead>
				<tr>
					<th>ID</th>
					<th>name</th>
					<th>address</th>
					<th>zipCode</th>
					<th>phone</th>
					<th>city</th>
					<th>country</th>
					<th>SID</th>
				</tr>
			</thead>
			<tbody>
				<%
					for(StaffListView s : list) {
				%>
						<tr>
							<td><%=s.getId()%></td>
							<td><%=s.getName()%></td>
							<td><%=s.getAddress()%></td>
							<td><%=s.getZipCode()%></td>
							<td><%=s.getPhone()%></td>
							<td><%=s.getCity()%></td>
							<td><%=s.getCountry()%></td>
							<td><%=s.getSid()%></td>
						</tr>
				<%
					}
				
				%>
			</tbody>
		</table>
</body>
</html>