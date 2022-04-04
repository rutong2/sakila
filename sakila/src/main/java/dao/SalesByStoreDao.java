package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.SalesByStore;

public class SalesByStoreDao {
	public List<SalesByStore> selectSalesByStore() {
		List<SalesByStore> list = new ArrayList<SalesByStore>();
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		try {
			String sql = "SELECT s.store store, s.manager manager, s.total_sales totalSales FROM sales_by_store s";
			stmt = conn.prepareStatement(sql); // 쿼리 작성
			rs = stmt.executeQuery(); // 쿼리 저장
			
			while(rs.next()) {
				SalesByStore salesByStore = new SalesByStore();
				salesByStore.setStore(rs.getString("store"));
				salesByStore.setManager(rs.getString("manager"));
				salesByStore.setTotalSales(rs.getDouble("totalSales"));
				list.add(salesByStore);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 단위 테스트
	public static void main(String[] args) {
		SalesByStoreDao salesByStoreDao = new SalesByStoreDao();
		List<SalesByStore> list = salesByStoreDao.selectSalesByStore();
		for(SalesByStore s : list) {
			System.out.print(s.getStore());
			System.out.print(s.getManager());
			System.out.println(s.getTotalSales());
		}
	}
}
