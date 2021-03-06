package common;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumCommon {
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//要素のタグ名 label
	protected static final String elementTagLabel = "aria-label";
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static final String mac = "mac";
	private static final String windows10 = "windows";


	public SeleniumCommon() {
	}

	protected String readDriverPath() {

		osCheck thisOs = new osCheck();
		String driverPath = null;
		if(thisOs.getOsName().equals(windows10)) {
			//OSがwindowsの場合
			driverPath = "./exe/win/chromedriver.exe";
		}else if(thisOs.getOsName().equals(mac)) {
			//OSがmacの場合
			driverPath = "./exe/mac/chromedriver";
		}
		return driverPath;
	}

	protected  void ScrollTo( WebElement element ,WebDriver driver) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
		}catch(JavascriptException e ) {

		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	//ランダムに待つ
	//rowNo 最短時間
	//hightNo 最長時間
	////////////////////////////////////////////////////////////////////////////////////////////////////
	protected void randomWait(int rowNo, int highNo) throws InterruptedException {
		Random random = new Random();
		int difference = highNo - rowNo;
		int num =10;
		if(difference > 0) {
			num = random.nextInt(difference) + rowNo;
		}
		Thread.sleep(num * 1000);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	//要素のリストから、欲しい要素だけを取り出す。
	//List<WebElement> Elements リスト形式の要素情報
	//ElNo 欲しい要素の番号
	//return 該当する要素情報
	////////////////////////////////////////////////////////////////////////////////////////////////////
	protected WebElement getPurposeElement(List<WebElement> Elements, int ElNo) {
		if(Elements != null) {
			int i = 0;
			for(WebElement Element: Elements) {
				if(i == ElNo) {
					return Element;
				}else {
					i++;
				}
			}
		}
		return null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	//要素のリストから、欲しい要素だけを取り出す。
	//List<WebElement> Elements リスト形式の要素情報
	//tagName 欲しいタグ情報
	//tagName 欲しいタグの値
	//return 該当する要素情報　複数ある時は一番先頭をとる
	////////////////////////////////////////////////////////////////////////////////////////////////////
	protected WebElement getPurposeElement(List<WebElement> Elements, String textName) {
		if(Elements != null) {
			for(WebElement Element: Elements) {
				try {
					if(Element.getText().equals(textName)) {
						return Element;
					}
				}catch(Exception e) {
					return null;
				}
			}
			return null;
		}
		return null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	//要素のリストから、欲しい要素だけを取り出す。
	//List<WebElement> Elements リスト形式の要素情報
	//tagName 欲しいタグ情報
	//tagName 欲しいタグの値
	//return 該当する要素情報　複数ある時は一番先頭をとる
	////////////////////////////////////////////////////////////////////////////////////////////////////
	protected WebElement getPurposeElement(List<WebElement> Elements, String[] textNames) {
		int checkCount = textNames.length;
		if(Elements != null) {
			for(WebElement Element: Elements) {
				try {
					int i = 0;
					if (i < checkCount) {
						for(String textName:textNames) {
							if(Element.getText().contains(textName)) {
								i++;
							}
						}
					}
					if(i == checkCount) {
						return Element;
					}
				}catch(Exception e) {
					return null;
				}
			}
			return null;
		}
		return null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	//要素のリストから、欲しい要素だけを取り出す。
	//List<WebElement> Elements リスト形式の要素情報
	//tagName 欲しいタグ情報
	//tagName 欲しいタグの値
	//return 該当する要素情報　複数ある時は一番先頭をとる
	////////////////////////////////////////////////////////////////////////////////////////////////////
	protected WebElement getPurposeElement(List<WebElement> Elements, String tagName, String tagValue) {
		if(Elements != null) {
			for(WebElement Element: Elements) {
				try {
					if(Element.getAttribute(tagName).equals(tagValue)) {
						return Element;
					}
				}catch(Exception e) {
					return null;
				}
			}
			return null;
		}
		return null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	//要素のリストから、欲しい要素だけを取り出す。
	//List<WebElement> Elements リスト形式の要素情報
	//tagName 欲しいタグ情報
	//tagName 欲しいタグの値
	//return 該当する要素情報　複数ある時は一番先頭をとる
	////////////////////////////////////////////////////////////////////////////////////////////////////
	protected WebElement getPurposeElement(List<WebElement> Elements, String tagName, String[] tagValues) {
		int checkCount = tagValues.length;
		if(Elements != null) {
			for(WebElement Element: Elements) {
				try {
					int i = 0;
					if (i < checkCount) {
						for(String tagValue:tagValues) {
							if(Element.getAttribute(tagName).contains(tagValue)) {
								i++;
							}
						}
					}
					if(i == checkCount) {
						return Element;
					}
				}catch(Exception e) {
					return null;
				}
			}
			return null;
		}
		return null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	//min 最小値
	//max 最大値
	//return 最小値と最大値の間の数をランダムに決めて返す
	////////////////////////////////////////////////////////////////////////////////////////////////////
	protected int customRandom(int min ,int max) {
		if(min < max) {
			int difference = max -min;
			Random random = new Random();
			int differenceRandom = random.nextInt(difference);
			int randomNum = differenceRandom + min;
			return randomNum;
		}else {
			return 0;
		}
	}
}
