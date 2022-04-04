<%@page import="vo.SalesByStore"%>
<%@page import="java.util.List"%>
<%@page import="dao.SalesByStoreDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	SalesByStoreDao salesByStoreDao = new SalesByStoreDao();
	List<SalesByStore> list = salesByStoreDao.selectSalesByStore();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>salesByStoreList</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">salesByStore List</h1>
		<table class="table table-bordered text-center table-striped table-dark">
			<thead>
				<tr>
					<th>store</th>
					<th>manager</th>
					<th>totalSales</th>
				</tr>
			</thead>
			<tbody>
				<%
					for(SalesByStore s : list) {
				%>
						<tr>
							<td><%=s.getStore()%></td>
							<td><%=s.getManager()%></td>
							<td><%=s.getTotalSales()%></td>
						</tr>
				<%
					}
				
				%>
			</tbody>
		</table>
</body>
</html>