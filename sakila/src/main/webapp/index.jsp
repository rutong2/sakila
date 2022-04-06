<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Index</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container bg-dark">
		<jsp:include page="upMenu.jsp"></jsp:include>
		<h1 class="text-center text-white">HOME</h1>
		<h2 class="text-white">테이블 리스트</h2>
		<ol class="text-white">
			<li><a href="<%=request.getContextPath()%>/storeList.jsp">StoreList</a></li>
			<li><a href="<%=request.getContextPath()%>/staffList.jsp">StaffList</a></li>
		</ol>
		<!-- 뷰 -->
		<h2 class="text-white">뷰 리스트</h2>
		<ol class="text-white">
			<li><a href="<%=request.getContextPath()%>/actorInfoList.jsp">ActorInfoList(view)</a></li>
			<li><a href="<%=request.getContextPath()%>/customerList.jsp">customerList(view)</a></li>
			<li><a href="<%=request.getContextPath()%>/filmList.jsp">filmList(view)</a></li>
			<li><a href="<%=request.getContextPath()%>/nicerButSlowerFilmList.jsp">nicerButSlowerFilmList(view)</a></li>
			<li><a href="<%=request.getContextPath()%>/salesByFilmCategoryList.jsp">salesByFilmCategoryList(view)</a></li>
			<li><a href="<%=request.getContextPath()%>/salesByStore.jsp">salesByStore(view)</a></li>
			<li><a href="<%=request.getContextPath()%>/staffListView.jsp">staffListView(view)</a></li>
		</ol>
		<!-- 프로시저 -->
		<h2 class="text-white">프로시저 리스트</h2>
		<ol class="text-white">
			<li><a href="<%=request.getContextPath()%>/filmInStockForm.jsp">filmInstock(procedures)</a></li>
			<li><a href="<%=request.getContextPath()%>/filmNotInStockForm.jsp">filmNotInStock(procedures)</a></li>
			<li><a href="<%=request.getContextPath()%>/rewardsReportForm.jsp">rewardsReport(procedures)</a></li>
		</ol>
		<!-- 상세검색 -->
		<h2 class="text-white">상세검색</h2>
		<ol class="text-white">
			<li><a href="<%=request.getContextPath()%>/filmSearchForm.jsp">filmSearchForm</a></li>
		</ol>
	</div>
</body>
</html>