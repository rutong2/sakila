<%@page import="dao.StoreDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%
	StoreDao storeDao = new StoreDao();
	List<Map<String, Object>> list = storeDao.selectStoreList();
%>p
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Store List</title>
</head>
<body>
	<h1>Store List</h1>
	<table>
		<thead>
			<th></th>
			<th></th>
			<th></th>
			<th></th>
			<th></th>
			<th></th>
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
</body>
</html>