<%@page import="vo.NicerButSlowerFilmList"%>
<%@page import="java.util.List"%>
<%@page import="dao.NicerButSlowerFilmListDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	int currentPage = 1;
	
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	
	int rowPerPage = 10;
	int beginRow = (currentPage-1)*10;
	
	NicerButSlowerFilmListDao nicerButSlowerFilmListDao = new NicerButSlowerFilmListDao();
	List<NicerButSlowerFilmList> list = nicerButSlowerFilmListDao.selectNicerButSlowerFilmListByPage(beginRow, rowPerPage);
	
	int totalRow = nicerButSlowerFilmListDao.selectNicerButSlowerFilmListTotalRow();
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
	<title>nicerButSlowerFilmList</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">nicerButSlowerFilm List</h1>
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
					for(NicerButSlowerFilmList n : list) {
				%>
						<tr>
							<td><%=n.getFid()%></td>
							<td><%=n.getTitle()%></td>
							<td><%=n.getDescription()%></td>
							<td><%=n.getCategory()%></td>
							<td><%=n.getPrice()%></td>
							<td><%=n.getLength()%></td>
							<td><%=n.getRating()%></td>
							<td><%=n.getActors()%></td>
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
						<li class="page-item"><a class="page-link float-left" href="<%=request.getContextPath()%>/nicerButSlowerFilmList.jsp?currentPage=<%=currentPage-1%>">이전</a></li>	
				<%		
					} if(currentPage < lastPage) {
				%>
						<li class="page-item"><a class="page-link float-left" href="<%=request.getContextPath()%>/nicerButSlowerFilmList.jsp?currentPage=<%=currentPage+1%>">다음</a></li>
				<%		
					}
				%>
			</ul>
		</div>
	</div>
</body>
</html>