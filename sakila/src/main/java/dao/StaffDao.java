package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StaffDao {
	
	// staffList 출력
	public List<Map<String, Object>> selectStaffList() {
		List<Map<String, Object>> list = new ArrayList<>();
	
		// DB 접속을 위한 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String dburl = "jdbc:mariadb://localhost:3306/sakila"; // DB 주소
		String dbuser = "root"; // DB 아이디
		String dbpw = "java1234"; // DB 패스워드
		
		try {
			Class.forName("org.mariadb.jdbc.Driver"); // 드라이버 접속
			System.out.println("드라이버 로딩 완료"); // 디버깅
			
			conn = DriverManager.getConnection(dburl, dbuser, dbpw); // DB 접속
			System.out.println("conn : " + conn); // 디버깅
			
			// staffList 출력 쿼리
			String sql = "SELECT"
					+ "	sf.staff_id staffId,"
					+ "	CONCAT(sf.first_name, ' ', sf.last_name) staffName,"
					+ "	sf.address_id addressId,"
					+ "	CONCAT(a.address, IFNULL(a.address2, ''), a.district) staffAddress,"
					+ "	sf.picture picture,"
					+ "	sf.email email,"
					+ "	sf.store_id storeId,"
					+ "	sf.active active,"
					+ " sf.username username,"
					+ " sf.password password,"
					+ "	sf.last_update lastUpdate"
					+ " FROM staff sf INNER JOIN address a"
					+ " ON sf.address_id = a.address_id;";
			
			stmt = conn.prepareStatement(sql); // 쿼리 작성
			System.out.println("stmt : " + stmt); // 디버깅
			
			rs = stmt.executeQuery(); // ResultSet 형식으로 쿼리 저장
			System.out.println("rs : " + rs); // 디버깅
			
			while(rs.next()) {
				// map 객체를 이용해서 정보들을 저장 후 list에 추가
				Map<String, Object> map = new HashMap<>();
				map.put("staffId", rs.getInt("staffId"));
				map.put("staffName", rs.getString("staffName"));
				map.put("addressId", rs.getInt("addressId"));
				map.put("staffAddress", rs.getString("staffAddress"));
				map.put("picture", rs.getString("picture"));
				map.put("email", rs.getString("email"));
				map.put("storeId", rs.getInt("storeId"));
				map.put("active", rs.getInt("active"));
				map.put("password", rs.getString("password"));
				map.put("username", rs.getString("username"));
				map.put("lastUpdate", rs.getString("lastUpdate"));
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("예외발생");
		} finally {
			try {
				// DB 자원 해지
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	// 단위 테스트
	public static void main(String[] args) {
		StaffDao staffDao = new StaffDao(); // 출력 메서드 사용을 위한 객체 생성
		List<Map<String, Object>> list = staffDao.selectStaffList(); // 출력 메서드의 반환값이 List<Map<String, Object>>형 이므로 객체 생성 후 저장
		
		// 출력 부분
		for(Map m : list) {
			System.out.print(m.get("staffId") + ", ");
			System.out.print(m.get("staffName") + ", ");
			System.out.print(m.get("addressId") + ", ");
			System.out.print(m.get("staffAddress") + ", ");
			System.out.print(m.get("picture") + ", ");
			System.out.print(m.get("email") + ", ");
			System.out.print(m.get("storeId") + ", ");
			System.out.print(m.get("active") + ", ");
			System.out.print(m.get("password") + ", ");
			System.out.print(m.get("username") + ", ");
			System.out.print(m.get("lastUpdate"));
			System.out.println();
		}
	}
}
