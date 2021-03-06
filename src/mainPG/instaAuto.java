package mainPG;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import Util.SelUtil;
import common.SeleniumCommon;

public class instaAuto extends SeleniumCommon{
//	アクセス情報
	private String logId;
	private String logPass;
	private String searchWord;
	private int retryNo;
	private int countNo = 0;
	private WebDriver driver = null;

	private Document htmlDocument = null;

//	フォロー機能の有無
	private boolean followCheck = false;


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//定数
	//アクセルURL
	private final String accessUrl="https://www.instagram.com/";

	//いいね率[%]
	private final int iineRithu = 70;

	//フォロー率[%]
	private final int followRithu = 70;

	//詳細ページを閉じるボタンのラベルテキスト
	private final String closeElementLabel = "「いいね！」を取り消す";

	//フォローしていない時のテキスト
	private final String followLabel = "フォロー中";
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public instaAuto(String logId, String logPass, String searchWord, int retryNo,boolean Followcheck) {
		super();
		setPram(logId, logPass, searchWord, retryNo,Followcheck);
	}

	private void setPram(String logId, String logPass, String searchWord, int retryNo, boolean Followcheck) {
		this.logId = logId;
		this.logPass = logPass;
		this.searchWord = searchWord;
		this.retryNo = retryNo;
		this.followCheck = Followcheck;
	}

	private void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	private String getLogId() {
		return this.logId;
	}
	private String getLogPass() {
		return this.logPass;
	}
	private String getSearchWord() {
		return this.searchWord;
	}
	private int getRetryNo() {
		return this.retryNo;
	}
	private int getCountNo() {
		return this.countNo;
	}
	private WebDriver getDriver(){
		return this.driver;
	}
	private String getAccessUrl(){
		return this.accessUrl;
	}

	private boolean getFollowCheck() {
		return this.followCheck;
	}
	private Document getHtmlDocument() {
		return this.htmlDocument;
	}
	private void setHtmlDocument(Document htmlDocument) {
		this.htmlDocument = htmlDocument;
	}

	public boolean autoIine() throws IOException, InterruptedException {

		String url = getAccessUrl();
		try {
			//ドライバー読み込み
			System.setProperty("webdriver.chrome.driver", readDriverPath());
			setDriver(new ChromeDriver());
	//ログイン画面
			getDriver().get(url);
		    Thread.sleep(5000);
	//	      要素情報取得
	//	      ID入力のための要素取得
		    WebElement inputId = getDriver().findElement(By.name("username"));
	//	      パスワード入力のための要素取得
		    WebElement inputPass = getDriver().findElement(By.name("password"));
	//	      ID入力
		    inputId.sendKeys(getLogId());
		    Thread.sleep(2000);
	//	      パスワード入力
		      inputPass.sendKeys(getLogPass() );
		      Thread.sleep(2000);
	//	      フォーム送信
		      inputPass.submit();

	//	      お知らせ等が出てくる場合があるので数回リロード
		      Thread.sleep(5000);
		      getDriver().get(url);
		      Thread.sleep(5000);
		      getDriver().get(url);
		      Thread.sleep(2000);

	//	      お知らせ削除用ボタン取得
		      WebElement delinf = getDriver().findElement(By.className("HoLwm"));
		      Thread.sleep(2000);
		      delinf.click();
		      Thread.sleep(2000);

	//一覧画面
	//	      ハッシュタグ検索要素取得
		      WebElement searchBox = getDriver().findElement(By.className("XTCLo"));
		      Thread.sleep(2000);
	//	      検索ワード入力
		      searchBox.sendKeys(getSearchWord());
		      Thread.sleep(2000);

	//	      検索ボックスクリック要素取得
//		      List<WebElement> searchBox2 = getDriver().findElements(By.cssSelector(".Ap253"));

		      List<WebElement> searchBox2 = getDriver().findElements(By.cssSelector(".-qQT3"));


		      Thread.sleep(2000);

	//	      検索ボックスクリック
		      WebElement serchWordElement = getPurposeElement(searchBox2, "/explore/tags/" + getSearchWord(),"href");

		      if(serchWordElement != null) {
		    	  serchWordElement.click();
		      }else {
//		    	  ハッシュタグがない場合の処理 回収が必要
		    	  String[] checkWords = {"#"};
		    	  WebElement serchWordElement2 = getPurposeElement(searchBox2,  checkWords);
		    	  if(serchWordElement2!=null) {
		    		  serchWordElement2.click();
		    	  }else {
		    		  SelUtil.addLog("検索ボックス処理にてエラー");
		    		  return false;
		    	  }
		      }

		      Thread.sleep(10000);

//	      詳細ページクリック
		      List<WebElement> scrollBoxs = null;
		      boolean scrollCheck = true;
		      while(getCountNo() < getRetryNo()) {
		    	  try {
			    	  scrollBoxs =  getDriver().findElements(By.cssSelector("._4emnV"));
			    	  if(scrollCheck) {
			    		  ScrollTo(getPurposeElement(scrollBoxs, 0), getDriver());
			    	  }
			    	  randomWait(1,4);
			    	  if(!clickPic()) {
			    		  scrollCheck = false;
			    	  }else {
			    		  scrollCheck = true;
			    	  }
		    	  }catch(Exception e){
		    		  SelUtil.addLog(e);
		    	  }
		      }

		}catch(Exception e){
			SelUtil.addLog(e);
			return false;
		}
		return true;
	}

	public boolean driverCheck() {
		if(getDriver() == null) {
			return true;
		}
		return false;
	}

	public void quitIine() {
		getDriver().quit();
	}


//以下privateメソッド
////////////////////////////////////////////////////////////////////////////////////////////////////
//ランダムに写真をクリックして、数秒後にフォーカスを外す
////////////////////////////////////////////////////////////////////////////////////////////////////
	private boolean clickPic() throws Exception {
		List<WebElement>  selectPic =  this.driver.findElements(By.cssSelector(".v1Nh3.kIKUG"));

		int num = customRandom(selectPic.size()/2, selectPic.size());
		getPurposeElement(selectPic, num).click();

		boolean clickCheck = clickIine();

		List<WebElement>  escElement = this.driver.findElements(By.cssSelector("._8-yf5"));
		getPurposeElement(escElement,"aria-label","閉じる").click();

		Thread.sleep(2500);
		if(clickCheck) {
			return true;
		}else {
			return false;
		}
	}


	private boolean clickIine() throws Exception {
    	 Thread.sleep(1500);
    	 if(doAction()) {
    		 //htmldocumentを取得
    		 setHtmlDocument(Jsoup.parseBodyFragment(this.driver.getPageSource()));

    		 if(checkiine()) {

    			 //以下フォロー機能
    			 if(getFollowCheck()) {
	    			 if(doFollow()) {
		    			 if(checF()) {
		        			 randomWait(2,6);
		        			 List<WebElement> followButons = this.driver.findElements(By.cssSelector(".yWX7d"));
		    				 getPurposeElement(followButons,1).click();
		    				 randomWait(2,6);
		    			 }
	    			 }
    			 }
    			 //以上フォロー機能


    			 //以下いいね機能
    			 randomWait(3,9);
    			 List<WebElement> Iinebutons = this.driver.findElements(By.cssSelector(".QBdPU"));
				 getPurposeElement(Iinebutons,1).click();
				 randomWait(8,20);
    			 this.countNo++;
    			//以上いいね機能


    			 return true;
    		 }else {
    			 randomWait(1,3);
    			 return false;
    		 }
    	 }else {
    		 randomWait(1,3);
    		 return false;
    	 }
	}




	private boolean doAction() throws Exception {
		 Random random = new Random();
		 int checkpram = random.nextInt(100);
		 if(checkpram < this.iineRithu) {
			 randomWait(3,9);
			 return true;
		 }else {
			 randomWait(1,3);
			 return false;
		 }
	}

	//フォロー率をもとにフォローするかを決定。
	private boolean doFollow() throws Exception {
		 Random random = new Random();
		 int checkpram = random.nextInt(100);
		 if(checkpram < this.followRithu) {
			 return true;
		 }else {
			 return false;
		 }
	}


	private boolean checkiine() {
//		String html = this.driver.getPageSource();
//		Document doc = Jsoup.parseBodyFragment(html);
		Document doc = getHtmlDocument();
		Elements datas = doc.select("._8-yf5");
		for(Element data: datas) {
			String ariaLabelText = "";
			try{
				ariaLabelText = data.attr(elementTagLabel);
			}catch(Exception e) {
				continue;
			}
			if(ariaLabelText.contains(closeElementLabel)) {
				return false;
			}else {
				continue;
			}
		}
		return true;
	}

	private boolean checF() {
		Document doc = getHtmlDocument();
		//フォローチェック
//		sqdOP yWX7d    y3zKF
		Elements datas = doc.select(".yWX7d");
		for(Element data: datas) {
			String ariaLabelText = "";
			try{
				ariaLabelText = data.text();
			}catch(Exception e) {
				continue;
			}

			if(ariaLabelText.contains(followLabel)) {
				return false;
			}else {
				continue;
			}
		}
		return true;
	}

}
