package sqlite;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.SelUtil;

public class SqliteDBslack {
	private static String tableName = "slackTbl";
	private static final int heacId = 1;
	static final String slack_token = "slack_token";
	static final String slack_channel = "slack_channel";


	public static void insertSlackData(String slackToken, String slackChannel) throws Exception {
		Map<String, String> searchMap = new HashMap<String, String>(){
			{
				put(slack_token, slackToken);
				put(slack_channel, slackChannel);
			}
		};
		String insertSql = MakeSql.insertTable(tableName, searchMap);
		try {
			SqliteDB.update(insertSql,tableName);
		}catch (Exception e) {
			SelUtil.addLog(e);
		}
	}

	public static void updateHeadSlackData(String slackToken, String slackChannel) throws Exception {
		//更新用
		Map<String, String> updateMap = new HashMap<String, String>(){
			{
				put(slack_token, slackToken);
				put(slack_channel, slackChannel);
			}
		};

		//先頭データのみ取得
		//検索用
		Map<String, String> searchMap = new HashMap<String, String>(){
			{put("id",  Integer.toString(heacId));}
		};
		String selectSql = MakeSql.selectTable(tableName, searchMap);

		try {
			ResultSet  sqlResult = SqliteDB.search(selectSql,tableName);

			//検索したデータを確認
			//データがひとつなら更新
			int checkResult = dataSizeCheck(sqlResult);
			if(checkResult == 1) {
				String updateSql = MakeSql.updateTable(tableName, updateMap ,searchMap);
				SqliteDB.update(updateSql,tableName);
			}else if(checkResult == 0) {
				insertSlackData(slackToken,slackChannel);
			}else {
				SelUtil.addLog("データが複数登録されています。");
			}
		}catch (Exception e) {
			SelUtil.addLog(e);
		}
	}

	public static List<String> getSelectData() throws Exception{

		//検索用
		Map<String, String> searchMap = new HashMap<String, String>(){
			{put("id",  Integer.toString(heacId));}
		};
		String selectSql = MakeSql.selectTable(tableName,searchMap);
		ResultSet searchResult = SqliteDB.search(selectSql,tableName);

		//返却用リストを作成
		List<String> returnData = new ArrayList<String>();
		int i = 0;
		while(searchResult.next()) {
			if(i == 0) {
				returnData.add(searchResult.getString(slack_token));
				returnData.add(searchResult.getString(slack_channel));
			}else {
				break;
			}
			i++;
		}
		return returnData;
	}

	/*
	 * データのサイズが1つかをチェック
	 * */
	private static int dataSizeCheck(ResultSet  sqlResult) throws Exception {
		int i = 0;
		while(sqlResult.next()) {
			i++;
		}
		return i;
	}
}
