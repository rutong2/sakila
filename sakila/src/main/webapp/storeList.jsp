<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="dao.StoreDao"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%
	StoreDao storeDao = new StoreDao(); // 출력 메서드 사용을 위한 객체 생성
	List<Map<String, Object>> list = storeDao.selectStoreList(); // 메서드 실행 후 반환 값 저장
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Store List</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">Store List</h1>
		
		<table class="table table-bordered text-center table-striped table-dark">
			<thead>
				<th>storeId</th>
				<th>staffId</th>
				<th>staffName</th>
				<th>addressId</th>
				<th>staffAddress</th>
				<th>lastUpdate</th>
			</thead>
			<tbody>
				<%
					for(Map m : list) {
				%>
						<tr>
							<td><%=m.get("storeId")%></td>
							<td><%=m.get("staffId")%></td>
							<td><%=m.get("staffName")%></td>
							<td><%=m.get("addressId")%></td>
							<td><%=m.get("staffAddress")%></td>
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