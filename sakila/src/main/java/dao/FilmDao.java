package dao;

import java.sql.*;
import java.util.*;

import util.DBUtil;
import vo.FilmList;

public class FilmDao {
	
	public Map<String, Object> filmInStockCall(int filmId, int storeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection conn = null;
		// PreparedStatement : 쿼리를 실행
		// CallableStatement : 프로시저를 실행
		CallableStatement stmt = null;
		ResultSet rs = null;
		// select inventory_id ...
		List<Integer> list = new ArrayList<>();
		// select count(inventory_id) ...
		Integer count = 0;
		
		conn = DBUtil.getConnection();
		try {
			stmt = conn.prepareCall("{CALL film_in_stock(?, ?, ?)}"); // 프로시저 실행
			stmt.setInt(1, filmId); // 영화 번호
			stmt.setInt(2, storeId); // 상점 번호
			stmt.registerOutParameter(3, Types.INTEGER); // int형 변수 -> 영화의 재고 갯수
			
			rs = stmt.executeQuery(); // 쿼리 저장
			
			while(rs.next()) {
				list.add(rs.getInt(1)); // rs.getInt("inventory_id")
			}
			
			count = stmt.getInt(3); // 프로시저 3번째 out변수값
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 정보들 저장
		map.put("list", list);
		map.put("count", count);
		
		return map;
	}
	
	public Map<String, Object> filmNotInStockCall(int filmId, int storeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		List<Integer> list = new ArrayList<Integer>();
		Integer count = 0;
		
		conn = DBUtil.getConnection();
		
		try {
			stmt = conn.prepareCall("{CALL film_not_in_stock(?, ?, ?)}"); // 프로시저 실행
			stmt.setInt(1, filmId); // 영화 번호
			stmt.setInt(2, storeId); // 상점 번호
			stmt.registerOutParameter(3, Types.INTEGER); // int형 변수 -> 대여한 영화의 갯수
			
			rs = stmt.executeQuery(); // 쿼리 저장
			
			while(rs.next()) {
				list.add(rs.getInt(1)); // rs.getInt("inventory_id")
			}
			
			count = stmt.getInt(3); // 프로시저 3번째 out변수값
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 정보들 저장
		map.put("list", list);
		map.put("count", count);
		
		return map;
	}
	
	public Map<String, Object> rewardsReportCall(int minMonthlyPurchases, double minDollarAmountPurchased) {
		Map<String, Object> firstMap = new HashMap<String, Object>(); // 최상위
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); // firstMap 밑에 List
		Map<String, Object> secondMap = null; // list 밑에 Map
		
		conn = DBUtil.getConnection(); // DB접속
		
		try {
			stmt = conn.prepareCall("{CALL rewards_report(?, ?, ?)}"); // 프로시저 실행
			stmt.setInt(1, minMonthlyPurchases); // 조건에 만족하기 위한 구매 또는 대여 횟수
			stmt.setDouble(2, minDollarAmountPurchased); // 조건에 만족하기 위한 지출한 최소 금액
			stmt.registerOutParameter(3, Types.INTEGER); // int형 변수
			rs = stmt.executeQuery(); // 쿼리 저장
			
			while(rs.next()) {
				secondMap = new HashMap<String, Object>(); // int와 String 두개의 정보를 받을 map 객체 생성
				secondMap.put("customerId", rs.getInt(1));
				secondMap.put("storeId", rs.getInt(2));
				secondMap.put("firstName", rs.getString(3));
				secondMap.put("lastName", rs.getString(4));
				secondMap.put("email", rs.getString(5));
				secondMap.put("addressId", rs.getInt(6));
				secondMap.put("active", rs.getInt(7));
				secondMap.put("createDate", rs.getString(8));
				secondMap.put("updateDate", rs.getString(9));

				list.add(secondMap); // secondMap의 상위인 list에 저장
			}
			
			count = stmt.getInt(3); // --> 조건에 만족한 손님 수
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// list의 상위 Map에 정보들 저장
		firstMap.put("list", list); // 조건에 만족한 손님 정보 리스트
		firstMap.put("count", count); // 조건에 만족한 손님 수
		
		return firstMap;
	}
	
	public List<Double> selectFilmPriceList() {
		List<Double> list = new ArrayList<Double>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		
		String sql = "SELECT DISTINCT price FROM film_list ORDER BY price";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				list.add(rs.getDouble("price"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public List<FilmList> selectFilmListSearch(int beginRow, int rowPerPage, String category, String rating, double price, int length, String title, String actor) {
		List<FilmList> list = new ArrayList<FilmList>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		
		try {
			String sql = "SELECT f.FID fid, f.title title, f.description description, f.category category, f.price price, f.length length, f.rating rating, f.actors actors FROM film_list f WHERE title LIKE ? AND actors LIKE ?";
			// ------------------- 아무것도 선택되지 않았을때 -------------------
			if(category.equals("") && rating.equals("") && price==-1 && length==-1) {
				sql += " ORDER BY fid LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setInt(3, beginRow);
				stmt.setInt(4, rowPerPage);
			} 
			// ------------------- 1개가 선택됐을때 -------------------  (4가지)
			else if(category.equals("") && rating.equals("") && price==-1 && length!=-1) { // length 선택
				if(length == 0) {
					sql += " AND length<60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setInt(3, beginRow);
					stmt.setInt(4, rowPerPage);
				} else if(length == 1) {
					sql += " AND length>=60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setInt(3, beginRow);
					stmt.setInt(4, rowPerPage);
				}
			} else if(category.equals("") && rating.equals("") && price!=-1 && length==-1) { // price 선택
				sql += " AND price=? ORDER BY fid LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setDouble(3, price);
				stmt.setInt(4, beginRow);
				stmt.setInt(5, rowPerPage);
			} else if(category.equals("") && !rating.equals("") && price==-1 && length==-1) { // rating 선택
				sql += " AND rating=? ORDER BY fid LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setString(3, rating);
				stmt.setInt(4, beginRow);
				stmt.setInt(5, rowPerPage);
			} else if(!category.equals("") && rating.equals("") && price==-1 && length==-1) { // category 선택
				sql += " AND category=? ORDER BY fid LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setString(3, category);
				stmt.setInt(4, beginRow);
				stmt.setInt(5, rowPerPage);
			} 
			// ------------------- 2개가 선택됐을때 -------------------  (6가지)
			else if(category.equals("") && rating.equals("") && price!=-1 && length!=-1) { // category, rating 선택
				if(length == 0) {
					sql += " AND price=? AND length<60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setDouble(3, price);
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
				} else if(length == 1) {
					sql += " AND price=? AND length>=60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setDouble(3, price);
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
				}
			} else if(category.equals("") && !rating.equals("") && price==-1 && length!=-1) { // category, price 선택
				if(length == 0) {
					sql += " AND rating=? AND length<60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, rating);
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
				} else if(length == 1) {
					sql += " AND rating=? AND length>=60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, rating);
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
				}
			} else if(!category.equals("") && rating.equals("") && price==-1 && length!=-1) { // rating, price 선택
				if(length == 0) {
					sql += " AND category=? AND length<60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
				} else if(length == 1) {
					sql += " AND category=? AND length>=60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
				}
			} else if(category.equals("") && !rating.equals("") && price!=-1 && length==-1) { // category, length 선택
				sql += " AND rating=? AND price=? ORDER BY fid LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setString(3, rating);
				stmt.setDouble(4, price);
				stmt.setInt(5, beginRow);
				stmt.setInt(6, rowPerPage);
			} else if(!category.equals("") && rating.equals("") && price!=-1 && length==-1) { // rating, length 선택
				sql += " AND category=? AND price=? ORDER BY fid LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setString(3, category);
				stmt.setDouble(4, price);
				stmt.setInt(5, beginRow);
				stmt.setInt(6, rowPerPage);
			} else if(!category.equals("") && !rating.equals("") && price==-1 && length==-1) { // price, length 선택
				sql += " AND category=? AND rating=? ORDER BY fid LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setString(3, category);
				stmt.setString(4, rating);
				stmt.setInt(5, beginRow);
				stmt.setInt(6, rowPerPage);
			} 
			// ------------------- 3개가 선택됐을때 ------------------- (4가지)
			else if(!category.equals("") && !rating.equals("") && price!=-1 && length==-1) { // length 선택 X
				sql += " AND category=? AND rating=? AND price=? ORDER BY fid LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setString(3, category);
				stmt.setString(4, rating);
				stmt.setDouble(5, price);
				stmt.setInt(6, beginRow);
				stmt.setInt(7, rowPerPage);
			} else if(!category.equals("") && !rating.equals("") && price==-1 && length!=-1) { // price 선택 X
				if(length == 0) {
					sql += " AND category=? AND rating=? AND length<60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setString(4, rating);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				} else if(length == 1) {
					sql += " AND category=? AND rating=? AND length>=60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setString(4, rating);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				}
			} else if(!category.equals("") && rating.equals("") && price!=-1 && length!=-1) { // rating 선택 X
				if(length == 0) {
					sql += " AND category=? AND price=? AND length<60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setDouble(4, price);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				} else if(length == 1) {
					sql += " AND category=? AND price=? AND length>=60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setDouble(4, price);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				}
			} else if(category.equals("") && !rating.equals("") && price!=-1 && length!=-1) { // category 선택 X
				if(length == 0) {
					sql += " AND rating=? AND price=? AND length<60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, rating);
					stmt.setDouble(4, price);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				} else if(length == 1) {
					sql += " AND rating=? AND price=? AND length>=60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, rating);
					stmt.setDouble(4, price);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				}
			}
			// ------------------- 모두 선택됐을때 -------------------
			else {
				if(length == 0) {
					sql += " AND category=? AND rating=? AND price=? AND length<60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setString(4, rating);
					stmt.setDouble(5, price);
					stmt.setInt(6, beginRow);
					stmt.setInt(7, rowPerPage);
				} else if(length == 1) {
					sql += " AND category=? AND rating=? AND price=? AND length>=60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setString(4, rating);
					stmt.setDouble(5, price);
					stmt.setInt(6, beginRow);
					stmt.setInt(7, rowPerPage);
				}
			}
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				FilmList filmList = new FilmList();
				filmList.setFid(rs.getInt("fid"));
				filmList.setTitle(rs.getString("title"));
				filmList.setDescription(rs.getString("description"));
				filmList.setCategory(rs.getString("category"));
				filmList.setPrice(rs.getDouble("price"));
				filmList.setLength(rs.getInt("length"));
				filmList.setRating(rs.getString("rating"));
				filmList.setActors(rs.getString("actors"));
				list.add(filmList);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static void main(String[] args) {
		FilmDao filmDao = new FilmDao();
		int filmId = 2;
		int storeId = 2;
		
		// film_in_stock 프로시저 구현
		Map<String, Object> map1 = filmDao.filmInStockCall(filmId, storeId);
		List<Integer> list1 = (List<Integer>)(map1.get("list"));
		int count1 = (Integer)(map1.get("count"));
		
		System.out.println(filmId + "번 영화가 " + storeId + "번 가게에 " + count1 +"개 남음");
		for(int id : list1) {
			System.out.println(id);
		}
		
		// film_not_in_stock 프로시저 구현
		Map<String, Object> map2 = filmDao.filmNotInStockCall(filmId, storeId);
		List<Integer> list2 = (List<Integer>)(map2.get("list"));
		int count2 = (Integer)(map2.get("count"));
		
		System.out.println(filmId + "번 영화가 " + storeId + "번 가게에 " + count2 +"개 남음");
		for(int id : list2) {
			System.out.println(id);
		}
		
		// rewards_report 프로시저 구현
		int minMonthlyPurchases = 7;
		double minDollarAmountPurchased = 20.00;
		Map<String, Object> map3 = filmDao.rewardsReportCall(minMonthlyPurchases, minDollarAmountPurchased);
		List<Map<String, Object>> list = (List<Map<String, Object>>)(map3.get("list"));
		int count3 = (Integer)(map3.get("count"));
		
		
		System.out.println("최소 구매 또는 대여 횟수 : " + minMonthlyPurchases + ", 지출한 최소 금액 : " + minDollarAmountPurchased);
		for(Map m : list) {
			System.out.print(m.get("customerId") + ", ");
			System.out.print(m.get("storeId") + ", ");
			System.out.print(m.get("firstName") + ", ");
			System.out.print(m.get("lastName") + ", ");
			System.out.print(m.get("email") + ", ");
			System.out.print(m.get("addressId") + ", ");
			System.out.print(m.get("active") + ", ");
			System.out.print(m.get("createDate") + ", ");
			System.out.print(m.get("updateDate"));
			System.out.println();
		}
		
		System.out.println("고객 수 : " + count3);
	}
}
