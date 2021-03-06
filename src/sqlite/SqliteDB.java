package sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class SqliteDB {
	private static Connection con = null;
	private static final String sqlDriverName = "org.sqlite.JDBC";
	private static final String dbPath = "jdbc:sqlite:.db/status.DB";

////////////////////////////////////////////////////////////////////////////////////
//検索
////////////////////////////////////////////////////////////////////////////////////
	public static ResultSet search(String selectSql, String tableName) throws Exception{
		RowSetFactory rowSetFactory = RowSetProvider.newFactory();

		CachedRowSet rowSet = rowSetFactory.createCachedRowSet();

	    Statement stmt = null;
	    ResultSet resultSet = null;

	    try {
	    //DB接続
	    dbCon(tableName);

	    stmt = con.createStatement();

	    //SQL実行
	    resultSet = stmt.executeQuery(selectSql);
	    rowSet.populate(resultSet);
	    }catch(Exception e) {

	    }finally {
	    	if (resultSet != null) {
	            resultSet.close();
	          }
	          if (stmt != null) {
	            stmt.close();
	          }
	          if (con != null) {
	            con.close();
	          }
	    }
	    return rowSet;
	}
////////////////////////////////////////////////////////////////////////////////////
//追加・更新・削除
////////////////////////////////////////////////////////////////////////////////////
	public static void update(String selectSql ,String tableName) throws Exception{
		Statement stmt = null;

		try {
			//接続
			dbCon(tableName);
			//sql実行
			stmt = con.createStatement();
	        stmt.executeUpdate(selectSql);
		}catch(Exception e) {
		}finally{
			if (stmt != null) {
	              stmt.close();
	           }
	           if (con != null) {
	              con.close();
	           }
		}
	}
////////////////////////////////////////////////////////////////////////////////////
//接続
////////////////////////////////////////////////////////////////////////////////////
	private static void dbCon(String tableName) throws Exception {

		Statement stmt = null;

		Class.forName(sqlDriverName);

		try {
			con = DriverManager.getConnection(dbPath);

			stmt = con.createStatement();

			stmt.executeUpdate(MakeSql.createTable(tableName));
		}catch(Exception e) {

		}
	}
}
