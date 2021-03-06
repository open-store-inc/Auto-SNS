package Util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.sun.javafx.binding.Logging;

public  class SelUtil {
	private static final String logFolder = "log";
	private static final String logFileType = ".txt";
	static final Logger logger = Logger.getLogger(Logging.class.getName());

/*
 * ログ記述用のメソッド
 * text => String 文字型
 */
	public static void addLog(String text ) throws IOException {
		FileHandler fileHandler = new FileHandler(logFolder  + "/" + getTextName(), false);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
		logger.log(Level.WARNING, text );
	}

/*
 * ログ記述用のメソッド
 * e => Exception参照型
 * Stringに変換して、addLogに渡す
 */
	public static void addLog(Exception e ) throws IOException {
		addLog(e.toString());
	}

/*
 * ファイル作成用のメソッド
 * path => String パス名
 * name => ファイル名
 * フォルダがない場合作成
 * ファイルがない場合は作成
 */
	public static void makeFile(String path, String name) throws IOException {
		String filePath = path + "/" + name;

		//フォルダとファイルに関するオブジェクトを作成
		File folder = new File(logFolder);
		File file = new File(filePath);

		//フォルダ存在確認
		if(!folder.exists()) {
			//ない場合作成
			folder.mkdir();
		}

		//ファイル存在確認
		if(!file.exists()) {
			//ない場合作成
			file.createNewFile();
		}
	}


	private static String getTextName() {
		Calendar today = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String todayCahgeFormat = format.format(today.getTime()) + logFileType;
		return todayCahgeFormat;
	}

}
