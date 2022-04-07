<%@page import="java.util.List"%>
<%@page import="dao.StoreDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	StoreDao storeDao = new StoreDao(); // 메서드 사용을위한 객체 생성
	List<Integer> storeIdList = storeDao.selectStoreIdList(); // 상점 번호 받아오기
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>rentalSearchForm</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">rentalSearchForm</h1>
		<form method="post" action="<%=request.getContextPath()%>/rentalSearchAction.jsp">
			<table class="table table-bordered text-center table-striped table-dark">
				<tr>
					<td>스토어 ID</td>
					<td>
						<div><input type="radio" name="storeId" value="" checked="checked">모든 가게</div>
						<%
							for(int i : storeIdList) {
						%>
								<div><input type="radio" name="storeId" value="<%=i%>"><%=i%>번 가게</div>
						<%		
							}
						%>
					</td>
				</tr>
				<tr>
					<td>고객 이름</td>
					<td>
						<input type="text" name="customerName">
					</td>
				</tr>
				<tr>
					<td>대여일자</td>
					<td>
						<input type="date" name="beginDate"> ~ <input type="date" name="endDate">
					</td>
				</tr>
				<tr>
					<td colspan="2"><button class="btn btn-primary float-right" type="submit">검색</button></td>
				</tr>
			</table>
		</form>
</body>
</html>