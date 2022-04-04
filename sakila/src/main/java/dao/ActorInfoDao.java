package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.ActorInfo;

public class ActorInfoDao {
	public List<ActorInfo> selectActorInfoListByPage(int beginRow, int rowPerPage) {
		List<ActorInfo> list = new ArrayList<ActorInfo>(); // 출력해줄 정보 ArrayList 생성
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection(); // DB 접속
		
		String sql = "SELECT actor_id actorId, first_name firstName, last_name lastName, film_info filmInfo FROM actor_info ORDER BY actor_id LIMIT ?, ?";
		try {
			stmt = conn.prepareStatement(sql); // 쿼리 작성
			// 내가 보여주고 싶은 정보량 셋팅
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			
			rs = stmt.executeQuery(); // ResultSet 형식으로 쿼리 저장
			
			while(rs.next()) {
				// vo를 이용해서 객체 생성 후 각 필드를 이용해서 정보 저장 -> ArrayList에 저장
				ActorInfo actorInfo = new ActorInfo();
				actorInfo.setActorId(rs.getInt("actorId"));
				actorInfo.setFirstName(rs.getString("firstName"));
				actorInfo.setLastName(rs.getString("lastName"));
				actorInfo.setFilmInfo(rs.getString("filmInfo"));
				list.add(actorInfo);
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
	
	// ActorInfo의 정보 총 행의 갯수
	public int selectActorInfoTotalRow() {
		int row = 0;
		Connection conn = DBUtil.getConnection(); // DB 접속
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT COUNT(*) cnt FROM actor_info";
		try {
			stmt = conn.prepareStatement(sql); // 쿼리 작성
			rs = stmt.executeQuery(); // ResultSet 형식으로 쿼리 저장
			
			while(rs.next()) {
				ActorInfo actorInfo = new ActorInfo(); // 객체 생성
				row = rs.getInt("cnt"); // 총 행의 갯수 저장
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
	
	// 단위 테스트
	public static void main(String[] args) {
		int currentPage = 1; // 현재페이지
		int rowPerPage = 10; // 내가 보고싶은 정보량
		int beginRow = (currentPage-1)*rowPerPage; // 정보의 시작점
		ActorInfoDao actorInfoDao = new ActorInfoDao(); // 출력 메서드 사용을 위한 객체 생성
		List<ActorInfo> list = actorInfoDao.selectActorInfoListByPage(beginRow, rowPerPage);
		
		for(ActorInfo a : list) {
			System.out.print(a.getActorId());
			System.out.print(a.getFirstName());
			System.out.print(a.getLastName());
			System.out.println(a.getFilmInfo());
		}
	}
}
