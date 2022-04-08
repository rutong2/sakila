<%@page import="dao.RentalDao"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	int currentPage = 1; // 현재 페이지
	
	// 다음이나 이전페이지를 갔을때 현재페이지값 변겅
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	
	// 정보들 받아오기
	int storeId = -1; // 상점번호가 선택되지 않았을때 
	if(!request.getParameter("storeId").equals("")) {
		storeId = Integer.parseInt(request.getParameter("storeId"));
		System.out.println("storeId : " + storeId);
	}
	String customerName = request.getParameter("customerName");
	String beginDate = "1900-01-01";
	String endDate = "2022-04-07";
	if(request.getParameter("beginDate") != null) {
		beginDate = request.getParameter("beginDate");
	}
	if(request.getParameter("endDate") != null) {
		endDate = request.getParameter("endDate");
	}
	
	// 디버깅
	System.out.println("storeId : " + storeId);
	System.out.println("customerName : " + customerName);
	System.out.println("beginDate : " + beginDate);
	System.out.println("endDate : " + endDate);
	
	// 페이징
	int rowPerPage = 10; // 내가 보고싶은 정보량
	int beginRow = (currentPage-1)*rowPerPage; // 시작하는 행의 번호
	RentalDao rentalDao = new RentalDao();
	
	int totalRow = rentalDao.selectRentalSearchTotalRow(storeId, customerName, beginDate, endDate); // 분기별 행의 갯수
	int lastPage = 0;
	
	if(totalRow%rowPerPage==0) { // 더이상 출력할 정보가 없을때
		lastPage = totalRow/rowPerPage;
	} else { // 아직 다 출력하지 못한 정보가 있을때
		lastPage = (totalRow/rowPerPage)+1;
	}
	
	// 디버깅
	System.out.println("totalRow : " + totalRow);
	System.out.println("lastPage : " + lastPage);
	
	// 메서드를 이용해 정보 저장
	List<Map<String, Object>> list = rentalDao.selectRentalSearchList(beginRow, rowPerPage, storeId, customerName, beginDate, endDate);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>rentalSearchAction</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">rentalSearchAction</h1>
		<table class="table table-bordered text-center table-striped table-dark">
			<thead>
				<tr>
					<th>rentalId</th>
					<th>name</th>
					<th>storeId</th>
					<th>filmId</th>
					<th>title</th>
					<th>rentalDate</th>
					<th>returnDate</th>
				</tr>
			</thead>
			<tbody>
					<%
						for(Map m : list) {
					%>
							<tr>
								<td><%=m.get("rentalId")%></td>
								<td><%=m.get("name")%></td>
								<td><%=m.get("storeId")%></td>
								<td><%=m.get("filmId")%></td>
								<td><%=m.get("title")%></td>
								<td><%=m.get("rentalDate")%></td>
								<td><%=m.get("returnDate")%></td>
							</tr>
					<%		
						}
					%>
			</tbody>
			<div>
				<ul class="pagination">
					<%
						if(currentPage > 1) {
					%>
							<li class="page-item"><a class="page-link float-left" href="<%=request.getContextPath()%>/rentalSearchAction.jsp?currentPage=<%=currentPage-1%>&storeId=<%=storeId%>&customerName=<%=customerName%>&beginDate=<%=beginDate%>&endDate=<%=endDate%>">이전</a></li>
					<%	
						}
						if(currentPage < lastPage) {
					%>
							<li class="page-item"><a class="page-link float-left" href="<%=request.getContextPath()%>/rentalSearchAction.jsp?currentPage=<%=currentPage+1%>&storeId=<%=storeId%>&customerName=<%=customerName%>&beginDate=<%=beginDate%>&endDate=<%=endDate%>">다음</a></li>
					<%		
						}
					%>
				</ul>
			</div>
		</table>
	</div>
</body>
</html>