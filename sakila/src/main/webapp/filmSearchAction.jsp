<%@page import="dao.FilmListDao"%>
<%@page import="vo.FilmList"%>
<%@page import="java.util.List"%>
<%@page import="dao.FilmDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	int currentPage = 1; // 현재 페이지

	// 이전이나 다음페이지로 넘어갔을때 페이지 값이 넘어오면 currentPage를 그 값으로 바꾸기
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}

	// 사용자가 선택한 값 받아오기
	String category = request.getParameter("category");
	String rating = request.getParameter("rating");
	double price = -1; // price 데이터가 입력되지 않았을때
	
	if(!request.getParameter("price").equals("")) { // price의 값을 입력했으면
		price = Double.parseDouble(request.getParameter("price"));
		System.out.println("price : " + price);
	}
	int length = -1; // length 데이터가 입력되지 않았을때
	
	if(!request.getParameter("length").equals("")) { // length의 값을 입력했으면
		length = Integer.parseInt(request.getParameter("length"));
	}
	String title = request.getParameter("title");
	String actor = request.getParameter("actor");
	
	// 디버깅
	System.out.println("category : " + category);
	System.out.println("rating : " + rating);
	System.out.println("price : " + price);
	System.out.println("length : " + length);
	System.out.println("title : " + title);
	System.out.println("actor : " + actor);
	
	int rowPerPage = 10; // 내가 보고싶은 정보량
	int beginRow = (currentPage-1)/rowPerPage; // 시작하는 행 번호
	
	// 메서드를 이용해 출력
	FilmDao filmDao = new FilmDao(); // 검색한 영화 정보 출력 메서드 사용을 위한 객체 생성
	List<FilmList> list = filmDao.selectFilmListSearch(beginRow, rowPerPage, category, rating, price, length, title, actor);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>filmSearchAction</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">영화 목록</h1>
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
	</div>
</body>
</html>