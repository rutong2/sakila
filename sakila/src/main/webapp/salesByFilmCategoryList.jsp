<%@page import="vo.SalesByFilmCategory"%>
<%@page import="java.util.List"%>
<%@page import="dao.SalesByFilmCategoryDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	int currentPage = 1;
	
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	
	int rowPerPage = 10;
	int beginRow = (currentPage-1)*10;
	
	SalesByFilmCategoryDao salesByFilmCategoryDao = new SalesByFilmCategoryDao();
	List<SalesByFilmCategory> list = salesByFilmCategoryDao.selectSalesByFilmCategoryByPage(beginRow, rowPerPage);
	
	int totalRow = salesByFilmCategoryDao.selectSalesByFilmCategoryTotalRow();
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
	<title>salesByFilmCategoryList</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">salesByFilmCategory List</h1>
		<table class="table table-bordered text-center table-striped table-dark">
			<thead>
				<tr>
					<th>category</th>
					<th>totalSales</th>
				</tr>
			</thead>
			<tbody>
				<%
					for(SalesByFilmCategory s : list) {
				%>
						<tr>
							<td><%=s.getCategory()%></td>
							<td><%=s.getTotalSales()%></td>
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
						<li class="page-item"><a class="page-link float-left" href="<%=request.getContextPath()%>/salesByFilmCategoryList.jsp?currentPage=<%=currentPage-1%>">이전</a></li>	
				<%		
					} if(currentPage < lastPage) {
				%>
						<li class="page-item"><a class="page-link float-left" href="<%=request.getContextPath()%>/salesByFilmCategoryList.jsp?currentPage=<%=currentPage+1%>">다음</a></li>
				<%		
					}
				%>
			</ul>
		</div>
	</div>
</body>
</html>