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
			<table class="table table-bordered text-center table-striped table-dark">
				<tr>
					<td><a href="<%=request.getContextPath()%>/storeList.jsp">StoreList</a></td>
				</tr>
				<tr>
					<td><a href="<%=request.getContextPath()%>/staffList.jsp">StaffList</a></td>
				</tr>
				
				<!-- 뷰 -->
				<tr>
					<td><a href="<%=request.getContextPath()%>/actorInfoList.jsp">ActorInfoList(view)</a></td>
				</tr>
				<tr>
					<td><a href="<%=request.getContextPath()%>/customerList.jsp">customerList(view)</a></td>
				</tr>
				<tr>
					<td><a href="<%=request.getContextPath()%>/filmList.jsp">filmList(view)</a></td>
				</tr>
				<tr>
					<td><a href="<%=request.getContextPath()%>/nicerButSlowerFilmList.jsp">nicerButSlowerFilmList(view)</a></td>
				</tr>
				<tr>
					<td><a href="<%=request.getContextPath()%>/salesByFilmCategoryList.jsp">salesByFilmCategoryList(view)</a></td>
				</tr>
				<tr>
					<td><a href="<%=request.getContextPath()%>/salesByStore.jsp">salesByStore(view)</a></td>
				</tr>
				<tr>
					<td><a href="<%=request.getContextPath()%>/staffListView.jsp">staffListView(view)</a></td>
				</tr>
			</table>
	</div>
</body>
</html>