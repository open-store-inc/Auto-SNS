package sqlite;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Util.SelUtil;

public class SqliteDBInsta {
	private static final String tableName = "instaTbl";
	static final String insta_id = "insta_id";
	static final String insta_pass = "insta_pass";
	static final String insta_word = "insta_word";
	static final String insta_count = "insta_count";
	public static void insertInstaData(String userId, String passWord, String serchWord) throws IOException {
		Map<String, String> searchMap = new HashMap<String, String>(){
			{
				put(insta_id,userId);
				put(insta_pass,passWord);
				put(insta_word,serchWord);
				put(insta_count, "0");
			}
		};
		String insertSql = MakeSql.insertTable(tableName, searchMap);
		try {
			SqliteDB.update(insertSql,tableName);
		} catch (Exception e) {
			SelUtil.addLog(e);
		}
	}
}
