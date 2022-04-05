<%@page import="java.util.*"%>
<%@page import="dao.FilmDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// 재고를 확인할 영화 번호와 상점 번호 받아오기
	int filmId = Integer.parseInt(request.getParameter("filmId"));
	int storeId = Integer.parseInt(request.getParameter("storeId"));
	
	// 디버깅
	System.out.println("filmId : " + filmId);
	System.out.println("storeId : " + storeId);
	
	FilmDao filmDao = new FilmDao(); // 메서드 사용을 위한 객체 생성
	Map<String, Object> map = filmDao.filmInStockCall(filmId, storeId); // 메서드를 이용해 정보를 가져온 후 Map<String, Object> 형식으로 저장
	List<Integer> list = (List<Integer>)(map.get("list")); // 재고가 몇번째에 꽂혀있는지 확인
	int count = (Integer)(map.get("count")); // 재고가 몇개있는지
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>filmInStockAction.jsp</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">재고 현황</h1>
		<table class="table table-bordered text-center table-striped table-dark">
			<tr>
				<td><%=storeId + "번 상점의 " + filmId + "번 영화 재고 현황" %></td>
				<td><%="재고 : " + count + "개"%></td>
			</tr>
			<%
				for(int i : list) {
			%>
				<tr>
					<td colspan="2"><%=i+"번"%></td>
				</tr>
			<%
				}
			%>
		</table>
	</div>
</body>
</html>