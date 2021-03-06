package application;

import java.util.List;

import Slack.SlConnect;
import Util.SelUtil;
import common.inputCheck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import mainPG.instaAuto;
import sqlite.SqliteDBslack;

public class InputController {
	private static final String errorWindowTitle = "エラー";
	private static final String sucsuessWindowTitle = "終了";
	private static final String doSucsuess = "処理が完了しました";
	private static final String doFail = "処理に失敗しました";
	private static final String inputErrorl = "入力してください";
	private static final String inputFail ="入力内容に誤りがあります";

	//以下DBより読み込み予定

	private String apiToken;
	private String slackChanel;

	@FXML private TextField loginId;

	@FXML private TextField passWord;

	@FXML private TextField searchWord;

	@FXML private TextField collectionNo;

	@FXML private CheckBox followMode;

	@FXML Button button;

	@FXML Button settingButton;


	@FXML
	public void onButtonClick(ActionEvent evt) throws Exception {

		//入力情報を各変巣に格納
		String instaId = loginId.getText();
		String instaPass = passWord.getText();
		String instaWord = searchWord.getText();
		String inputNo = collectionNo.getText();
		boolean inputFollow = followMode.isSelected();


        inputCheck check =  new inputCheck();
        if(check.numCheck(inputNo) && check.halfCheck(instaId) && check.halfCheck(instaPass)) {

        	//DB更新
//        	SqliteDBInsta.insertInstaData(instaId, instaPass, instaWord);

        	//SlackのパラメータをDBから取得し、セットする。
        	setSlackParam(SqliteDBslack.getSelectData());

        	//Slack用のオブジェクト作成
        	SlConnect slack = new SlConnect(this.apiToken, this.slackChanel, instaId, instaWord, inputNo);

        	//インスタ制御のオブジェクト作成
        	instaAuto movement = new instaAuto(
        			instaId,
        			instaPass,
        			instaWord,
        			Integer.parseInt(inputNo),
        			inputFollow);

        	try {

        		//スタートメッセージ送信
        		try {
        			slack.sendStartMessage();
        		} catch (Exception e){
//        			メッセージ送信が失敗したらエラーを出力
        			SelUtil.addLog(e);
        		}

        		//自動いいね処理開始の成功と失敗で処理を変える
				if(movement.autoIine()) {
					//処理が成功した場合

					//終了メッセージ送信
					try {
						slack.sendEndMessage();
					}catch (Exception e){
//	        			メッセージ送信が失敗したらエラーを出力
	        			SelUtil.addLog(e);
	        		}

					//終了ポップアップ表示
					showPopWindow(sucsuessWindowTitle,doSucsuess);
				}else {
					//処理が失敗した場合
					//動作エラーが発生したメッセージ送信
					slack.sendErrorMessage("selenium動作でエラーが発生しました。");

					//エラーポップアップ表示
					showPopWindow(errorWindowTitle,doFail);
				}
			} catch (Exception e) {

				//エラーメッセージを送信
				try {
					slack.sendErrorMessage(e);
				}catch (Exception e2){
//        			メッセージ送信が失敗したらエラーを出力
        			SelUtil.addLog(e2);
        		}

				SelUtil.addLog(e);

				//エラーポップアップ表示
				showPopWindow(errorWindowTitle,doFail);
			} finally {
				if(!movement.driverCheck()) {
					movement.quitIine();
				}
			}
        }else if(collectionNo.getText().equals("") || loginId.getText().equals("") || passWord.getText().equals("")){
        	showPopWindow(errorWindowTitle,inputErrorl);
        }
        else {
        	showPopWindow(errorWindowTitle,inputFail);
        }
    }

	@FXML
	public void settingButtonClick(ActionEvent evt) throws Exception {
		//現在表示されている画面を閉じる
		Scene s = ((Node)evt.getSource()).getScene();
		Window window = s.getWindow();
		window.hide();

		//新しい画面を生成する。
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("setting.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void showPopWindow(String title, String message) {
		Alert alrt = new Alert(AlertType.INFORMATION);
		alrt.setTitle(title);
		alrt.setContentText(message);
		alrt.showAndWait();
	}

	private void setSlackParam(List <String> paramList) {
		String token = paramList.get(0);
		String channel = paramList.get(1);

		this.apiToken = token;
		this.slackChanel = channel;
	}

}
