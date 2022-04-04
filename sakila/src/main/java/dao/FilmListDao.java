package dao;

import java.util.*;

import util.DBUtil;

import java.sql.*;

import vo.FilmList;

public class FilmListDao {
	public List<FilmList> selectFilmListByPage(int beginRow, int rowPerPage) {
		List<FilmList> list = new ArrayList<FilmList>();
		// 자원 준비
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getConnection();
		try {
			String sql = "SELECT fl.FID fid, fl.title title, fl.description description, fl.category category, fl.price price, fl.length length, fl.rating rating, fl.actors actors FROM film_list fl ORDER BY fl.FID LIMIT ?, ?";
			stmt = conn.prepareStatement(sql); // 쿼리 작성
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery(); // 쿼리 저장
			
			while(rs.next()) {
				FilmList filmList = new FilmList();
				filmList.setFid(rs.getInt("fid"));
				filmList.setTitle(rs.getString("title"));
				filmList.setDescription(rs.getString("description"));
				filmList.setCategory(rs.getString("category"));
				filmList.setPrice(rs.getDouble("price"));
				filmList.setLength(rs.getInt("length"));
				filmList.setRating(rs.getString("rating"));
				filmList.setActors(rs.getString("actors"));
				list.add(filmList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int selectFilmListTotalRow() {
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		
		try {
			String sql="SELECT COUNT(*) cnt FROM film_list";
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
		FilmListDao filmListDao = new FilmListDao();
		List<FilmList> list = filmListDao.selectFilmListByPage(1, 10);
		for(FilmList f : list) {
			System.out.print(f.getFid());
			System.out.print(f.getTitle());
			System.out.print(f.getDescription());
			System.out.print(f.getCategory());
			System.out.print(f.getPrice());
			System.out.print(f.getLength());
			System.out.print(f.getRating());
			System.out.println(f.getActors());
		}
	}
}
