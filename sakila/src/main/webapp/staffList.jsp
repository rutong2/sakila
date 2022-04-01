<%@page import="java.util.*"%>
<%@page import="dao.StaffDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	StaffDao staffDao = new StaffDao(); // 출력 메서드를 위한 객체 생성
	List<Map<String, Object>> list = staffDao.selectStaffList(); // 메서드 실행 후 반환값 저장
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Staff List</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark overflow-auto">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white"> Staff List</h1>
		<table class="table table-bordered text-center table-striped table-dark table-sm">
			<thead>
				<th>staffId</th>
				<th>staffName</th>
				<th>addressId</th>
				<th>staffAddress</th>
				<th>picture</th>
				<th>email</th>
				<th>storeId</th>
				<th>active</th>
				<th>password</th>
				<th>username</th>
				<th>lastUpdate</th>
			</thead>
			<tbody>
				<%
					for(Map m : list) {
				%>
						<tr>
							<td><%=m.get("staffId")%></td>
							<td><%=m.get("staffName")%></td>
							<td><%=m.get("addressId")%></td>
							<td><%=m.get("staffAddress")%></td>
							<td><%=m.get("picture")%></td>
							<td><%=m.get("email")%></td>
							<td><%=m.get("storeId")%></td>
							<td><%=m.get("active")%></td>
							<td><%=m.get("password")%></td>
							<td><%=m.get("username")%></td>
							<td><%=m.get("lastUpdate")%></td>
						</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>