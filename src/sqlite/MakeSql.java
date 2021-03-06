package sqlite;

import java.util.Map;

public class MakeSql {
	/*
	 * テーブル作成用のメソッド
	 * tableName => 作成するテーブルの名前
	 * 今はインスタテーブルのみに対応
	 */
	public static String createTable(String tableName) {
		String sql = "";
		//テーブル名で分岐させる
		if(tableName.equals("instaTbl")) {
		sql = "create table if not exists " + tableName +"("
				+ "id integer primary key,"
				+ SqliteDBInsta.insta_id + " text,"
				+ SqliteDBInsta.insta_pass + " text,"
				+ SqliteDBInsta.insta_word + " text,"
				+ SqliteDBInsta.insta_count + " text,"
				+ "date timestamp NOT NULL default (datetime(CURRENT_TIMESTAMP,'localtime')))";
		}else if(tableName.equals("slackTbl")) {
		sql = "create table if not exists " + tableName +"("
				+ "id integer primary key,"
				+ SqliteDBslack.slack_token + " text,"
				+ SqliteDBslack.slack_channel + " text,"
				+ "date timestamp NOT NULL default (datetime(CURRENT_TIMESTAMP,'localtime')))";
		}

		return sql;
	}
	
	/*
	 *セレクト作成用
	 * tableName => テーブルの名前
	 *whereDatas => 検索 key=>項目, value=>値
	 */
	public static String selectTable(String TableName ,Map<String, String> whereDatas) {
		String sql =
				"select * "
				+ "from "
				+ TableName;
		sql = sql + whereSql(whereDatas);
		return sql;
	}

	/*
	 *新規作成用
	 * tableName => テーブルの名前
	 *inputData => key=>項目, value=>値
	 */
	public static String insertTable(String TableName ,Map<String, String> inputDatas) {
		String sql =
				"insert into "
				+ TableName
				+ "(";
		int k = 0;
		for (String fieldName : inputDatas.keySet()) {
			if(k != 0) {
				sql = sql + "," + fieldName;
			}else {
				sql = sql + fieldName;
			}
			k++;
		}
		sql = sql + ")values(";
		int i = 0;
		for (String fieldValue : inputDatas.values()) {
			if(i != 0) {
				sql = sql + ",'" + fieldValue + "'";
			}else {
				sql = sql + "'" + fieldValue + "'";
			}
			i++;
		}
		sql = sql +")";
		return sql;
	}

	/*
	 *更新作成用
	 * tableName => テーブルの名前
	 *whereDatas => key=>項目, value=>値
	 */
	public static String updateTable(String TableName ,Map<String, String> inputDatas, Map<String, String> whereDatas) {
		String sql =
			"update "
			+ TableName
			+ " set ";
		int i = 0;
		for (String fieldName : inputDatas.keySet()) {
			if(i != 0) {
				sql = sql +"," +  fieldName + "='" + inputDatas.get(fieldName) + "'";
			}else {
				sql = sql + fieldName + "='" + inputDatas.get(fieldName) + "'";
			}
			i++;
		}
		sql = sql + whereSql(whereDatas);
		return sql;
	}

	/*
	 *削除作成用
	 * tableName => テーブルの名前
	 *whereDatas => key=>項目, value=>値
	 */
	public static String deleteData(String TableName ,Map<String, String> whereDatas) {
		String sql =
				"delete "
				+ "from "
				+ TableName;
		sql = sql + whereSql(whereDatas);
		return sql;
	}


	private static String whereSql(Map<String, String> whereDatas) {
		String sql = " where ";
		int i = 0;
		for (String fieldName : whereDatas.keySet()) {
			if(i==0) {
				sql = sql + fieldName + " = '" + whereDatas.get(fieldName) + "'";
			}else {
				sql = sql + " and " + fieldName + " = '" + whereDatas.get(fieldName) + "'";
			}
			i++;
		}
		return sql;
	}
}
