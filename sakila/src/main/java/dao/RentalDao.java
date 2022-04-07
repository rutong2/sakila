package dao;

import java.sql.*;
import java.util.*;

import util.DBUtil;

public class RentalDao {
	public List<Map<String, Object>> selectRentalSearchList(int beginRow, int rowPerPage, int storeId, String customerName, String beginDate, String endDate) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection(); // DB접속
		
		try {
			String sql = "SELECT"
					+ "	r.rental_id rentalId,"
					+ " CONCAT(c.first_name,' ',c.last_name) name,"
					+ " s.store_id storeId,"
					+ " i.film_id filmId,"
					+ " f.title title,"
					+ " r.rental_date rentalDate,"
					+ " r.return_date returnDate"
					+ "	FROM rental r INNER JOIN customer c"
					+ "	ON r.customer_id = c.customer_id"
					+ "	INNER JOIN staff s"
					+ "	ON r.staff_id = s.staff_id"
					+ "	INNER JOIN inventory i"
					+ "	ON r.inventory_id = i.inventory_id"
					+ "	INNER JOIN film f"
					+ "	ON i.film_id = f.film_id"
					+ "	WHERE CONCAT(c.first_name ,' ', c.last_name) LIKE ?";
			if(beginDate.equals("") && endDate.equals("")) { // 대여날짜가 선택되지 않았을때
				if(storeId == -1) { // 모든 가게 보여줄때
					sql += " ORDER BY rental_id LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setInt(2, beginRow);
					stmt.setInt(3, rowPerPage);
				} else if(storeId != -1) { // 특정 상점 보여줄때
					sql += " AND s.store_id=? ORDER BY rental_id LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setInt(2, storeId);
					stmt.setInt(3, beginRow);
					stmt.setInt(4, rowPerPage);
				}
			} else if(!beginDate.equals("") && !endDate.equals("")) { // 대여날짜가 선택됐을때
				if(storeId == -1) { // 모든 가게 보여줄때
					sql += " AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d') ORDER BY rental_id LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setString(2, beginDate);
					stmt.setString(3, endDate);
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
				} else if(storeId != -1) { // 특정 상점 보여줄때
					sql += " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d') ORDER BY rental_id LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setInt(2, storeId);
					stmt.setString(3, beginDate);
					stmt.setString(4, endDate);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				}
			}
			rs = stmt.executeQuery(); // 쿼리 저장
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rentalId", rs.getInt("rentalId"));
				map.put("name", rs.getString("name"));
				map.put("storeId", rs.getInt("storeId"));
				map.put("filmId", rs.getInt("filmId"));
				map.put("title", rs.getString("title"));
				map.put("rentalDate", rs.getString("rentalDate"));
				map.put("returnDate", rs.getString("returnDate"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 자원 반납
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	public int selectRentalSearchTotalRow(int storeId, String customerName, String beginDate, String endDate) {
		int row = 0;
		
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection(); // DB접속
		
		try {
			String sql = "SELECT COUNT(*) cnt"
					+ "	FROM rental r INNER JOIN customer c"
					+ "	ON r.customer_id = c.customer_id"
					+ " INNER JOIN staff s"
					+ "	ON r.staff_id = s.staff_id"
					+ "	INNER JOIN inventory i"
					+ "	ON r.inventory_id = i.inventory_id"
					+ "	INNER JOIN film f"
					+ "	ON i.film_id = f.film_id"
					+ "	WHERE CONCAT(c.first_name,' ',c.last_name) LIKE ?";
			if(beginDate.equals("") && endDate.equals("")) { // 대여날짜가 선택되지 않았을때
				if(storeId == -1) { // 모든 가게 보여줄때
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
				} else if(storeId != -1) { // 특정 상점 보여줄때
					sql += " AND s.store_id=?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setInt(2, storeId);
				}
			} else if(!beginDate.equals("") && !endDate.equals("")) { // 대여날짜가 선택됐을때
				if(storeId == -1) { // 모든 가게 보여줄때
					sql += " AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setString(2, beginDate);
					stmt.setString(3, endDate);
				} else if(storeId != -1) { // 특정 상점 보여줄때
					sql += " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setInt(2, storeId);
					stmt.setString(3, beginDate);
					stmt.setString(4, endDate);
				}
			}
			rs = stmt.executeQuery(); // 쿼리 저장
			while(rs.next()) {
				row = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 자원 반납
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	return row;
	}
}