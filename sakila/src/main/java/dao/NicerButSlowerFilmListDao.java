package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.NicerButSlowerFilmList;

public class NicerButSlowerFilmListDao {
	public List<NicerButSlowerFilmList> selectNicerButSlowerFilmListByPage(int beginRow, int rowPerPage) {
		List<NicerButSlowerFilmList> list = new ArrayList<NicerButSlowerFilmList>();
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		try {
			String sql = "SELECT n.FID fid, n.title title, n.description description, n.category category, n.price price, n.length length, n.rating rating, n.actors actors FROM nicer_but_slower_film_list n ORDER BY n.FID LIMIT ?, ?";
			stmt = conn.prepareStatement(sql); // 쿼리 작성
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery(); // 쿼리 저장
			
			while(rs.next()) {
				NicerButSlowerFilmList nicerButSlowerFilmList = new NicerButSlowerFilmList();
				nicerButSlowerFilmList.setFid(rs.getInt("fid"));
				nicerButSlowerFilmList.setTitle(rs.getString("title"));
				nicerButSlowerFilmList.setDescription(rs.getString("description"));
				nicerButSlowerFilmList.setCategory(rs.getString("category"));
				nicerButSlowerFilmList.setPrice(rs.getDouble("price"));
				nicerButSlowerFilmList.setLength(rs.getInt("length"));
				nicerButSlowerFilmList.setRating(rs.getString("rating"));
				nicerButSlowerFilmList.setActors(rs.getString("actors"));
				list.add(nicerButSlowerFilmList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int selectNicerButSlowerFilmListTotalRow() {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		
		try {
			String sql="SELECT COUNT(*) cnt FROM nicer_but_slower_film_list";
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
		NicerButSlowerFilmListDao nicerButSlowerFilmListDao = new NicerButSlowerFilmListDao();
		List<NicerButSlowerFilmList> list = nicerButSlowerFilmListDao.selectNicerButSlowerFilmListByPage(1, 10);
		for(NicerButSlowerFilmList n : list) {
			System.out.print(n.getFid());
			System.out.print(n.getTitle());
			System.out.print(n.getDescription());
			System.out.print(n.getCategory());
			System.out.print(n.getPrice());
			System.out.print(n.getLength());
			System.out.print(n.getRating());
			System.out.println(n.getActors());
		}
	}
}
