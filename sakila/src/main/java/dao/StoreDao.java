package dao;

import java.util.*;

import util.DBUtil;

import java.sql.*;

public class StoreDao {
	
	// storeList 출력 메서드
	public List<Map<String, Object>> selectStoreList() {
		// ArrayList는 List 인터페이스의 구현체 중 하나다. -> List가 ArrayList의 부모
		// HashMap은 Map 인터페이스의 구현체 중 하나다. -> Map가 HashMap의 부모
		List<Map<String, Object>> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String dburl = "jdbc:mariadb://localhost:3306/sakila"; // DB 주소
		String dbuser = "root"; // DB 아이디
		String dbpw = "java1234"; // DB 패스워드
		
		
		try {
			conn = DBUtil.getConnection();
			
			// storeList를 보여줄 쿼리
			String sql = "SELECT"
					+ "	s.store_id storeId,"
					+ "	s.manager_staff_id staffId,"
					+ "	CONCAT(sf.first_name, ' ', sf.last_name) staffName,"
					+ "	s.address_id addressId,"
					+ "	CONCAT(a.address, IFNULL(a.address2, ''), a.district) staffAddress,"
					+ "	s.last_update lastUpdate"
					+ " FROM store s INNER JOIN staff sf"
					+ " INNER JOIN address a"
					+ " ON s.manager_staff_id = sf.staff_id"
					+ " AND s.address_id = a.address_id;";
			
			stmt = conn.prepareStatement(sql); // 접속한 DB에 쿼리 작성
			System.out.println("stmt : " + stmt); // 디버깅
			
			rs = stmt.executeQuery(); // ResultSet 형식으로 쿼리 저장
			System.out.println("rs : " + rs); // 디버깅
			
			while(rs.next()) {
				// 정보들을 쿼리를 이용해 DB에서 가져온 후 list에 저장
				Map<String, Object> map = new HashMap<>();
				map.put("storeId", rs.getInt("storeId"));
				map.put("staffId", rs.getInt("staffId"));
				map.put("staffName", rs.getString("staffName"));
				map.put("addressId", rs.getInt("addressId"));
				map.put("staffAddress", rs.getString("staffAddress"));
				map.put("lastUpdate", rs.getString("lastUpdate"));
				list.add(map);
			}
			
		} catch (Exception e) {
			e.printStackTrace(); // 오류 메세지 출력
			System.out.println("예외 발생");
		}finally {
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
		StoreDao dao = new StoreDao(); // 출력 메서드 사용을 위한 객체 생성
		List<Map<String, Object>> list = dao.selectStoreList(); // 출력 메서드를 사용 후 반환된 값이 List<Map<String, Object>>형이므로 객체를 만들어 저장
		
		// 출력 부분
		for(Map m : list) {
			System.out.print(m.get("storeId") + ", ");
			System.out.print(m.get("staffId") + ", ");
			System.out.print(m.get("staffName") + ", ");
			System.out.print(m.get("addressId") + ", ");
			System.out.print(m.get("staffAddress") + ", ");
			System.out.print(m.get("lastUpdate"));
			System.out.println();
		}
	}
}
