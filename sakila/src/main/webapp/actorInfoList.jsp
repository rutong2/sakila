<%@page import="java.util.List"%>
<%@page import="vo.ActorInfo"%>
<%@page import="dao.ActorInfoDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	int currentPage = 1; // 현재페이지
	
	if(request.getParameter("currentPage") != null) { // 다음페이지나 이전페이지로 넘어갔으면
		currentPage = Integer.parseInt(request.getParameter("currentPage")); // 받아온 currentPage 값을 저장
	}
	
	int rowPerPage = 10; // 내가 보고싶은 정보량
	int beginRow = (currentPage-1)*rowPerPage; // 정보의 시작점
	
	// 디버깅
	System.out.println("현재 페이지 : " + currentPage);
	System.out.println("beginRow : " + beginRow);
	System.out.println("rowPerPage : " + rowPerPage);
	
	ActorInfoDao actorInfoDao = new ActorInfoDao(); // 메서드 사용을 위한 객체 생성
	List<ActorInfo> list = actorInfoDao.selectActorInfoListByPage(beginRow, rowPerPage); // 리스트 출력 메서드 실행 후 List<ActorInfo>형식으로 저장
	
	int totalRow = actorInfoDao.selectActorInfoTotalRow();
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
	<title>actorInfoList</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">actorInfo List</h1>
		
		<table class="table table-bordered text-center table-striped table-dark">
			<thead>
				<th>actorId</th>
				<th>firstName</th>
				<th>lastName</th>
				<th>filmInfo</th>
			</thead>
			<tbody>
				<%
					for(ActorInfo a : list) {
				%>
						<tr>
							<td><%=a.getActorId()%></td>
							<td><%=a.getFirstName()%></td>
							<td><%=a.getLastName()%></td>
							<td><%=a.getFilmInfo()%></td>
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
						<li class="page-item"><a class="page-link float-left" href="<%=request.getContextPath()%>/actorInfoList.jsp?currentPage=<%=currentPage-1%>">이전</a></li>	
				<%		
					} if(currentPage < lastPage) {
				%>
						<li class="page-item"><a class="page-link float-left" href="<%=request.getContextPath()%>/actorInfoList.jsp?currentPage=<%=currentPage+1%>">다음</a></li>
				<%		
					}
				%>
			</ul>
		</div>
	</div>
</body>
</html>