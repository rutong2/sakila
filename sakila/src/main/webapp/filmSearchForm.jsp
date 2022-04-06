<%@page import="dao.FilmDao"%>
<%@page import="dao.CategoryDao"%>
<%@page import="vo.Category"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	CategoryDao categoryDao = new CategoryDao();
	List<Category> list = categoryDao.selectCategoryList();
	FilmDao filmDao = new FilmDao();
	List<Double> priceList = filmDao.selectFilmPriceList();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>filmSearchForm</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">필름 리스트 뷰 검색</h1>
		<form method="post" action="<%=request.getContextPath()%>/filmSearchAction.jsp">
			<table class="table table-bordered text-center table-striped table-dark">
				<tr>
					<td>category</td>
					<td>
						<select name="category">
							<option value="">카테고리 선택</option>
							<%
								for(Category c : list) {
							%>
									<option value="<%=c.getName()%>"><%=c.getName()%></option>
							<%		
								}
							%>
						</select>
					</td>
				</tr>
				<tr>
					<td>rating</td>
					<td>
						<select name="rating">
							<option value="">등급 선택</option>
							<option value="G">G</option>
							<option value="PG">PG</option>
							<option value="PG-13">PG-13</option>
							<option value="R">R</option>
							<option value="NC-17">NC-17</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>대여료</td>
					<td>
						<div><input type="radio" name="price" value="" checked="checked">선택안함</div>
						<%
							for(Double p : priceList) {
						%>
								<div><input type="radio" name="price" value="<%=p%>"><%=p%></div>
						<%		
							}
						%>
					</td>
				</tr>
				<tr>
					<td>영화시간</td>
					<td>
						<div><input type="radio" name="length" value="" checked="checked">선택안함</div>
						<div><input type="radio" name="length" value="0">1시간 미만</div>
						<div><input type="radio" name="length" value="1">1시간 이상</div>
					</td>
				</tr>
				<tr>
					<td>제목</td>
					<td>
						<input type="text" name="title">
					</td>
				</tr>
				<tr>
					<td>배우</td>
					<td>
						<input type="text" name="actor">
					</td>
				</tr>
				<tr>
					<td colspan="2"><button class="btn btn-primary float-right">검색</button></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>