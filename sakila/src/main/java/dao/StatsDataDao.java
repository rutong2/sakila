package dao;

import java.util.*;

import javax.print.attribute.standard.PresentationDirection;

import util.DBUtil;

import java.sql.*;

public class StatsDataDao {
	public List<Map<String, Object>> amountByCustomer() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		try {
			String sql = "SELECT p.customer_id customerId, "
					+ " CONCAT(c.first_name, ' ' ,c.last_name) name,"
					+ " SUM(p.amount) total"
					+ " FROM payment p INNER JOIN customer c"
					+ " ON p.customer_id = c.customer_id"
					+ " GROUP BY p.customer_id"
					+ " HAVING total >= 180"
					+ " ORDER BY total DESC";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("customerId", rs.getInt("customerId"));
				map.put("name", rs.getString("name"));
				map.put("total", rs.getInt("total"));
				list.add(map);
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
	
	public List<Map<String, Object>> filmCountByRentalRate() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		
		try {
			String sql = "SELECT"
					+ " rental_rate rentalRate,"
					+ " COUNT(*) total"
					+ " FROM film"
					+ " GROUP BY rental_rate"
					+ " ORDER BY total DESC";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rentalRate", rs.getDouble("rentalRate"));
				map.put("total", rs.getInt("total"));
				list.add(map);
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
	
	public List<Map<String, Object>> filmCountByRating() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		
		try {
			String sql = "SELECT"
					+ " rating,"
					+ " COUNT(*) total"
					+ " FROM film"
					+ " GROUP BY rating"
					+ " ORDER BY total DESC";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rating", rs.getString("rating"));
				map.put("total", rs.getInt("total"));
				list.add(map);
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
	
	public List<Map<String, Object>> filmCountByLanguage() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		
		try {
			String sql = "SELECT l.name name, "
					+ " COUNT(*) total"
					+ " FROM film f INNER JOIN language l"
					+ " ON f.language_id = l.language_id"
					+ " GROUP BY name";
			stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", rs.getString("name"));
				map.put("total", rs.getInt("total"));
				list.add(map);
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
	
	public List<Map<String, Object>> filmCountByLength() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		
		try {
			String sql = "SELECT t.length2 leng, COUNT(*) total"
					+ " FROM (SELECT title, LENGTH,"
					+ "		CASE WHEN LENGTH <= 60 THEN '60분 이하'"
					+ "			  WHEN LENGTH BETWEEN 61 AND 120 THEN '60분 초과 ~ 120분 이하'"
					+ "			  WHEN LENGTH BETWEEN 121 AND 180 THEN '120분 초과 ~ 180분 이하'"
					+ "			  ELSE '180분 초과'"
					+ "		END LENGTH2"
					+ " FROM film) t"
					+ " GROUP BY t.LENGTH2"
					+ " ORDER BY total DESC";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("leng", rs.getString("leng"));
				map.put("total", rs.getInt("total"));
				list.add(map);
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
}
