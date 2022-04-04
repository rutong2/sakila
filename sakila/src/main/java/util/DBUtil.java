package util;

import java.sql.*;

public class DBUtil {
	public static Connection getConnection() {
		String dburl = "jdbc:mariadb://localhost:3306/sakila"; // DB 주소
		String dbuser = "root"; // DB 아이디
		String dbpw = "java1234"; // DB 패스워드
		
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver"); // 드라이버 로딩
			conn = DriverManager.getConnection(dburl, dbuser, dbpw);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
}
