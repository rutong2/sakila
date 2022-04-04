package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.StaffListView;

public class StaffListViewDao {
	public List<StaffListView> selectStaffListView() {
		List<StaffListView> list = new ArrayList<StaffListView>();
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		try {
			String sql = "SELECT s.ID id, s.name name, s.address address, s.`zip code` zipCode, s.phone phone, s.city city, s.country country, s.SID sid FROM staff_list s";
			stmt = conn.prepareStatement(sql); // 쿼리 작성
			rs = stmt.executeQuery(); // 쿼리 저장
			
			while(rs.next()) {
				StaffListView staffListView = new StaffListView();
				staffListView.setId(rs.getInt("id"));
				staffListView.setName(rs.getString("name"));
				staffListView.setAddress(rs.getString("address"));
				staffListView.setZipCode(rs.getString("zipCode"));
				staffListView.setPhone(rs.getString("phone"));
				staffListView.setCity(rs.getString("city"));
				staffListView.setCountry(rs.getString("country"));
				staffListView.setSid(rs.getInt("sid"));
				list.add(staffListView);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 단위 테스트
	public static void main(String[] args) {
		StaffListViewDao staffListViewDao = new StaffListViewDao();
		List<StaffListView> list = staffListViewDao.selectStaffListView();
		for(StaffListView s : list) {
			System.out.print(s.getId());
			System.out.print(s.getName());
			System.out.print(s.getAddress());
			System.out.print(s.getZipCode());
			System.out.print(s.getPhone());
			System.out.print(s.getCity());
			System.out.print(s.getCountry());
			System.out.println(s.getSid());
		}
	}
}
