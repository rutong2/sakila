package dao;

import java.sql.*;
import java.util.*;

import util.DBUtil;
import vo.CustomerList;

public class CustomerListDao {
	// customerList 출력
	public List<CustomerList> selectCustomerListByPage(int beginRow, int rowPerPage) {
		List<CustomerList> list = new ArrayList<CustomerList>();
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		try {
			String sql = "SELECT cl.ID id, cl.name name, cl.address address, cl.`zip code` zipCode, cl.phone phone, cl.city city, cl.country country, cl.notes notes, cl.SID sid FROM customer_list cl ORDER BY cl.ID LIMIT ?, ?";
			stmt = conn.prepareStatement(sql); // 쿼리 작성
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery(); // 쿼리 저장
			
			while(rs.next()) {
				CustomerList cutomerList = new CustomerList();
				cutomerList.setId(rs.getInt("id"));
				cutomerList.setName(rs.getString("name"));
				cutomerList.setAddress(rs.getString("address"));
				cutomerList.setZipCode(rs.getString("zipCode"));
				cutomerList.setPhone(rs.getString("phone"));
				cutomerList.setCity(rs.getString("city"));
				cutomerList.setCountry(rs.getString("country"));
				cutomerList.setNotes(rs.getString("notes"));
				cutomerList.setSid(rs.getInt("sid"));
				list.add(cutomerList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int selectCustomerListTotalRow() {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		
		try {
			String sql="SELECT COUNT(*) cnt FROM customer_list";
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
		CustomerListDao customerListDao = new CustomerListDao();
		List<CustomerList> list = customerListDao.selectCustomerListByPage(1, 10);
		for(CustomerList c : list) {
			System.out.print(c.getId());
			System.out.print(c.getName());
			System.out.print(c.getAddress());
			System.out.print(c.getZipCode());
			System.out.print(c.getPhone());
			System.out.print(c.getCity());
			System.out.print(c.getCountry());
			System.out.print(c.getNotes());
			System.out.println(c.getSid());
		}
	}
}
