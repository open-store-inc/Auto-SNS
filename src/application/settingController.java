package application;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import sqlite.SqliteDBslack;

public class settingController {

	@FXML private TextField slackToken;
	@FXML private TextField channel;

	@FXML Button settingFinButton;

	@FXML Button backButton;

	@FXML Button LoadButton;

	@FXML
	public void slackinfButtonClick(ActionEvent evt) throws Exception {

		//入力情報を各変数に格納
		String slackBotToken = slackToken.getText();
		String slackChannel = channel.getText();

		if(!slackBotToken.equals("") || !slackChannel.equals("")) {
			SqliteDBslack.updateHeadSlackData(slackBotToken, slackChannel);
			showPopWindow("成功","更新が完了しました。");
		}else {
			showPopWindow("エラー","入力値が空です。");
		}


	}

	@FXML
	public void moveMain(ActionEvent evt) throws Exception {
		//現在表示されている画面を閉じる
		Scene s = ((Node)evt.getSource()).getScene();
		Window window = s.getWindow();
		window.hide();

		//新しい画面を生成する。
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("input.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void loadParam(ActionEvent evt) throws Exception {
		//パラメータをロード
    	//SlackのパラメータをDBから取得し、セットする。
    	List<String> paramList =  new ArrayList<String>();
    	paramList = SqliteDBslack.getSelectData();
    	String token = paramList.get(0);
		String ch = paramList.get(1);

		//画面のテキストフィールドにセット
		slackToken.setText(token);
		channel.setText(ch);
	}

	private void showPopWindow(String title, String message) {
		Alert alrt = new Alert(AlertType.INFORMATION);
		alrt.setTitle(title);
		alrt.setContentText(message);
		alrt.showAndWait();
	}
}
