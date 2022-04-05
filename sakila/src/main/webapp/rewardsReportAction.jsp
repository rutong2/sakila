<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="dao.FilmDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 	// 초기값 설정
	int minMonthlyPurchases = 99999;
	double minDollarAmountPurchased = 99999.0;
	
	// 받아온 값이 null이 아니면 그 값으로 변경
	if((request.getParameter("minMonthlyPurchases") != null) && (request.getParameter("minDollarAmountPurchased") != null)) {
		minMonthlyPurchases = Integer.parseInt(request.getParameter("minMonthlyPurchases"));
		minDollarAmountPurchased = Double.parseDouble(request.getParameter("minDollarAmountPurchased"));
	}
	
	// 디버깅
	System.out.println("minMonthlyPurchases : " + minMonthlyPurchases);
	System.out.println("minDollarAmountPurchased : " + minDollarAmountPurchased);
	
	FilmDao filmDao = new FilmDao(); // 메서드 실행을 위한 객체 생성
	Map<String, Object> map = filmDao.rewardsReportCall(minMonthlyPurchases, minDollarAmountPurchased); // 정보 가져오기
	
	// map에 저장된 정보 저장
	List<Map<String, Object>> list = (List<Map<String, Object>>)(map.get("list"));
	int count = (Integer)(map.get("count"));
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>rewardsReportAction</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">재고 현황</h1>
		<table class="table table-bordered text-center table-striped table-dark">
			<tr>
				<td colspan="3"><%="구매 또는 렌탈 횟수 : " + minMonthlyPurchases%></td>
				<td colspan="3"><%="금맥 : "+ minDollarAmountPurchased%></td>
				<td colspan="3"><%="조건에 만족하는 고객 수 : " + count + "명"%></td>
			</tr>
			<%
				for(Map m : list) {
			%>
				<tr>
					<td><%=m.get("customerId")%></td>
					<td><%=m.get("storeId")%></td>
					<td><%=m.get("firstName")%></td>
					<td><%=m.get("lastName")%></td>
					<td><%=m.get("email")%></td>
					<td><%=m.get("addressId")%></td>
					<td><%=m.get("active")%></td>
					<td><%=m.get("createDate")%></td>
					<td><%=m.get("updateDate")%></td>
				</tr>
			<%
				}
			%>
		</table>
	</div>
</body>
</html>