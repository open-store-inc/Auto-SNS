package Slack;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import Util.SelUtil;

public class SlConnect {
	private static final String startMessage = "下記ユーザーでの処理を開始いたします。";
	private static final String endMessage = "下記ユーザーでの処理を終了しました。";
	private static final String errorMessage = "下記ユーザーでのエラーが発生しました。";


	//下記でパラメータ定義
	private String token;
	private String channel;
	private String inputId;
	private String inputWord;
	private String inputCount;

	private Slack slack;



	public SlConnect(String token, String channel, String inputId, String inputWord, String inputCount) {
		setToken(token);
		setChannel(channel);
		setInputId(inputId);
		setInputWord(inputWord);
		setInputCount(inputCount);

		//インスタンス取得しパラメータセット
		Slack slack = Slack.getInstance();
		setSlack(slack);
	}



	public boolean sendStartMessage() throws Exception {
		if(sendMessage(startMessage)) {
			return true;
		}else {
			return false;
		}
	}

	public boolean sendEndMessage() throws  Exception{
		if(sendMessage(endMessage)) {
			return true;
		}else {
			return false;
		}
	}

	public boolean sendErrorMessage(Exception e) throws  Exception{
		String javaError = e.toString();
		String messageText = errorMessage + "\n" + javaError;
		if(sendMessage(messageText)) {
			return true;
		}else {
			return false;
		}
	}

	public boolean sendErrorMessage(String Message) throws  Exception{
		String messageText = errorMessage + "\n" + Message;
		if(sendMessage(messageText)) {
			return true;
		}else {
			return false;
		}
	}

	private boolean sendMessage(String messageText) throws Exception {

//		パラメータ取得
		String token = getToken();
		String channel = getChannel();
		String textMessage = messageText;
		Slack slack = getSlack();

		String userInfo = "ユーザーID　： " + getInputId() + "\n"
									+ "ハッシュタグ： #" + getInputWord() + "\n"
									+"いいね数　　：" + getInputCount() + "\n";

		textMessage = textMessage + "\n" + userInfo;

		try {
			// API メソッドのクライアントを上のトークンと共に初期化します
			MethodsClient methods = slack.methods(token);

			// API リクエスト内容を構成します
			ChatPostMessageRequest request = ChatPostMessageRequest.builder().channel(channel).text(textMessage).build();

			// API レスポンスを Java オブジェクトとして受け取ります
			ChatPostMessageResponse response = methods.chatPostMessage(request);

			return true;
		} catch (Exception e) {
			SelUtil.addLog(e);
			return false;
		}
	}




	//private 関数  getter setter
	/*
	 *
	 * */
	private String getToken() {
		return this.token;
	}
	private void setToken(String token) {
		this.token = token;
	}
	private String getChannel() {
		return this.channel;
	}
	private void setChannel(String channel) {
		this.channel = channel;
	}
	private String getInputId() {
		return this.inputId;
	}
	private void setInputId(String inputId) {
		this.inputId = inputId;
	}
	private String getInputWord() {
		return this.inputWord;
	}
	private void setInputWord(String inputWord) {
		this.inputWord = inputWord;
	}
	private String getInputCount() {
		return this.inputCount;
	}
	private void setInputCount(String inputCount) {
		this.inputCount = inputCount;
	}
	private Slack getSlack() {
		return this.slack;
	}
	private void setSlack(Slack slack) {
		this.slack = slack;
	}
}
