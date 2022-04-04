package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.SalesByFilmCategory;

public class SalesByFilmCategoryDao {
	public List<SalesByFilmCategory> selectSalesByFilmCategory() {
		List<SalesByFilmCategory> list = new ArrayList<SalesByFilmCategory>();
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		try {
			String sql = "SELECT s.category category, s.total_sales totalSales FROM sales_by_film_category s ORDER BY total_sales DESC";
			stmt = conn.prepareStatement(sql); // 쿼리 작성
			rs = stmt.executeQuery(); // 쿼리 저장
			
			while(rs.next()) {
				SalesByFilmCategory salesByFilmCategory = new SalesByFilmCategory();
				salesByFilmCategory.setCategory(rs.getString("category"));
				salesByFilmCategory.setTotalSales(rs.getInt("totalSales"));
				list.add(salesByFilmCategory);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int selectSalesByFilmCategoryTotalRow() {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		
		try {
			String sql="SELECT COUNT(*) cnt FROM sales_by_film_category";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				row = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// 단위 테스트
	public static void main(String[] args) {
		SalesByFilmCategoryDao salesByFilmCategoryDao = new SalesByFilmCategoryDao();
		List<SalesByFilmCategory> list = salesByFilmCategoryDao.selectSalesByFilmCategory();
		for(SalesByFilmCategory s : list) {
			System.out.print(s.getCategory());
			System.out.println(s.getTotalSales());
		}
	}
}
