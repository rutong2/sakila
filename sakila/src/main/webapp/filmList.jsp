<%@page import="vo.FilmList"%>
<%@page import="java.util.List"%>
<%@page import="dao.FilmListDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	int currentPage = 1;
	
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	
	int rowPerPage = 10;
	int beginRow = (currentPage-1)*10;
	
	FilmListDao filmListDao = new FilmListDao();
	List<FilmList> list = filmListDao.selectFilmListByPage(beginRow, rowPerPage);
	
	int totalRow = filmListDao.selectFilmListTotalRow();
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
	<title>filmList</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">film List</h1>
		<table class="table table-bordered text-center table-striped table-dark">
			<thead>
				<tr>
					<th>FID</th>
					<th>title</th>
					<th>description</th>
					<th>category</th>
					<th>price</th>
					<th>length</th>
					<th>rating</th>
					<th>actors</th>
				</tr>
			</thead>
			<tbody>
				<%
					for(FilmList f : list) {
				%>
						<tr>
							<td><%=f.getFid()%></td>
							<td><%=f.getTitle()%></td>
							<td><%=f.getDescription()%></td>
							<td><%=f.getCategory()%></td>
							<td><%=f.getPrice()%></td>
							<td><%=f.getLength()%></td>
							<td><%=f.getRating()%></td>
							<td><%=f.getActors()%></td>
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
						<li class="page-item"><a class="page-link float-left" href="<%=request.getContextPath()%>/filmList.jsp?currentPage=<%=currentPage-1%>">이전</a></li>	
				<%		
					} if(currentPage < lastPage) {
				%>
						<li class="page-item"><a class="page-link float-left" href="<%=request.getContextPath()%>/filmList.jsp?currentPage=<%=currentPage+1%>">다음</a></li>
				<%		
					}
				%>
			</ul>
		</div>
	</div>
</body>
</html>