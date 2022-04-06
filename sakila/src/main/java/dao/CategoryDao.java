package dao;

import java.sql.*;
import java.util.*;

import util.DBUtil;
import vo.Category;

public class CategoryDao {
	// 카테고리 출력부분
	public List<Category> selectCategoryList() {
		List<Category> list = new ArrayList<Category>();
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT category_id categoryId, name, last_update lastUpdate FROM category";
		conn = DBUtil.getConnection(); // DB 접속
		
		try {
			stmt = conn.prepareStatement(sql); // 쿼리 작성
			rs = stmt.executeQuery(); // 쿼리 저장
			while(rs.next()) {
				Category c = new Category();
				c.setCategoryId(rs.getInt("categoryId"));
				c.setName(rs.getString("name"));
				c.setLastUpdate(rs.getString("lastUpdate"));
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 자원 해지
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
