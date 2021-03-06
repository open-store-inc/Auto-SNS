package test;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

public class SlackSend {
	private String token;
	private String channel;
	private String textMessage;
	private Slack slack;

	//コンストラクタ
	public SlackSend(String token, String channel, String textMessage) {
		setToken(token);
		setChannel(channel);
		setTextMessage(textMessage);

		//インスタンス取得しパラメータセット
		Slack slack = Slack.getInstance();
		setSlack(slack);
	}

	//private 関数  getter setter
		/*
		 *
		 * */
	public boolean sendMessage() {
		//各パラメータ取得
		String token = getToken();
		String channel = getChannel();
		String textMessage = getTextMessage();
		Slack slack = getSlack();

		try {
			// API メソッドのクライアントを上のトークンと共に初期化します
			MethodsClient methods = slack.methods(token);

			// API リクエスト内容を構成します
			ChatPostMessageRequest request = ChatPostMessageRequest.builder().channel(channel).text(textMessage).build();

			// API レスポンスを Java オブジェクトとして受け取ります
			ChatPostMessageResponse response = methods.chatPostMessage(request);
		} catch (Exception e) {
//			処理に失敗した場合falseを返却
			return false;
		}
//		処理に成功した場合trueを返却
		return true;
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
	private String getTextMessage() {
		return this.textMessage;
	}
	private void setTextMessage(String textaMessage) {
		this.textMessage = textaMessage;
	}

	private Slack getSlack() {
		return this.slack;
	}
	private void setSlack(Slack slack) {
		this.slack = slack;
	}
}

