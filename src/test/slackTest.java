package test;

import java.io.IOException;

import com.slack.api.methods.SlackApiException;



public class slackTest {

	public static void main(String[] args) throws IOException, SlackApiException {
//		// TODO 自動生成されたメソッド・スタブ
//		Slack slack = Slack.getInstance();
//		String botToken = "xoxb-1653175992615-1654258778999-WfrwkzwZzQQy32Fx57qvtqEW";
//
//
//		// API メソッドのクライアントを上のトークンと共に初期化します
//		MethodsClient methods = slack.methods(botToken);
//
//		// API リクエスト内容を構成します
//		ChatPostMessageRequest request = ChatPostMessageRequest.builder()
//		  .channel("#random") // ここでは簡単に試すためにチャンネル名を指定していますが `C1234567` のような ID を指定する方が望ましいです
//		  .text(":wave: Hi from a bot written in Java!")
//		  .build();
//
//		// API レスポンスを Java オブジェクトとして受け取ります
//		ChatPostMessageResponse response = methods.chatPostMessage(request);


		String token =  "xoxb-1653175992615-1654258778999-WfrwkzwZzQQy32Fx57qvtqEW";
		String channel = "testchanel";
		String textWord = "テスト";


		SlackSend test = new SlackSend(token, channel, textWord);

		System.out.println(test.sendMessage());
	}

}
