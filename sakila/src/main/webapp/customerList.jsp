<%@page import="java.util.List"%>
<%@page import="vo.CustomerList" %>
<%@page import="dao.CustomerListDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	int currentPage = 1;
	
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	
	int rowPerPage = 10;
	int beginRow = (currentPage-1)*10;
	
	CustomerListDao customerListDao = new CustomerListDao();
	List<CustomerList> list = customerListDao.selectCustomerListByPage(beginRow, rowPerPage);
	
	int totalRow = customerListDao.selectCustomerListTotalRow();
	int lastPage = 0;
	
	if(totalRow%rowPerPage == 0) {
		lastPage = totalRow/rowPerPage;
	} else {
		lastPage = (totalRow/rowPerPage)+1;
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>customerList</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">customer List</h1>
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
					<th>notes</th>
					<th>SID</th>
				</tr>
			</thead>
			<tbody>
				<%
					for(CustomerList c : list) {
				%>
						<tr>
							<td><%=c.getId()%></td>
							<td><%=c.getName()%></td>
							<td><%=c.getAddress()%></td>
							<td><%=c.getZipCode()%></td>
							<td><%=c.getPhone()%></td>
							<td><%=c.getCity()%></td>
							<td><%=c.getCountry()%></td>
							<td><%=c.getNotes()%></td>
							<td><%=c.getSid()%></td>
						</tr>
				<%
					}
				
				%>
			</tbody>
		</table>
		<div>
			<ul class="pagination">
				<%
					if(currentPage > 1) {
				%>
						<li class="page-item"><a class="page-link float-left" href="<%=request.getContextPath()%>/customerList.jsp?currentPage=<%=currentPage-1%>">이전</a></li>	
				<%		
					} if(currentPage < lastPage) {
				%>
						<li class="page-item"><a class="page-link float-left" href="<%=request.getContextPath()%>/customerList.jsp?currentPage=<%=currentPage+1%>">다음</a></li>
				<%		
					}
				%>
			</ul>
		</div>
	</div>
</body>
</html>