package tests;

import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.TestConfig;
import libraries.generalFunctions.Log;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class DefaultAnnotations {

	public TCResult Result;
	public SeleniumBrowser Browser;
	
	@BeforeMethod(groups = { TestConfig.Initialization }, alwaysRun = true)
	@Parameters("remote")
	public void beforeMethod(@Optional("") String pIsRemote, Method pTestMethod) throws MalformedURLException {

		DOMConfigurator.configure("log4j.xml");
		
		Browser = new SeleniumBrowser();
		if(Constants.Browsertype.equals("IE")) {
			if (pIsRemote.equals("") || pIsRemote.toUpperCase().equals("NO")) {
			System.setProperty("webdriver.ie.driver", Constants.Init_Folder + "/IEDriverServer.exe");
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			dc.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
			dc.setJavascriptEnabled(true);
			Browser.Driver = new InternetExplorerDriver(dc);
		
			} else {
			DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
			
			dc.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			dc.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
			dc.setJavascriptEnabled(true);
			String node = "http://10.1.67.30:4444/wd/hub";
			Browser.Driver = new RemoteWebDriver(new URL(node), dc);
			}
		}
		
		
		else if(Constants.Browsertype.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", Constants.Init_Folder + "/geckodriver.exe");
			Browser.Driver = new FirefoxDriver();
		
			
		/*	
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
			
			cap.setJavascriptEnabled(true);	
			
			 Proxy proxy = new Proxy();
	         String proxyServer = String.format("AProxyIDontWantToDisplay", System.getenv("proxy.username"), System.getenv("proxy.password"));
	         proxy.setHttpProxy(proxyServer);
	         cap.setCapability("proxy", proxy);
			
	         cap.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true)		;	
			
			Browser.Driver = new FirefoxDriver(cap);
			*/
		}
		
		
		else if(Constants.Browsertype.equals("chrome")) {
			if (pIsRemote.equals("") || pIsRemote.toUpperCase().equals("NO")) {
				
				String downloadFilepath = Constants.DataPath + "\\Download";
				  HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				  chromePrefs.put("profile.default_content_settings.popups", 0);
				  chromePrefs.put("download.default_directory", downloadFilepath);
				
				System.setProperty("webdriver.chrome.driver", Constants.Init_Folder + "/chromedriver.exe");
				//enable Automation in Chrome
				ChromeOptions options = new ChromeOptions();
				
				options.addArguments("disable-infobars");
				//Browser.Driver = new ChromeDriver(options);
				  
				  options.setExperimentalOption("prefs", chromePrefs);
				  DesiredCapabilities cap = DesiredCapabilities.chrome();
				  cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				  cap.setCapability(ChromeOptions.CAPABILITY, options);
				  Browser.Driver = new ChromeDriver(cap);
				
				
			//	DesiredCapabilities dcap = new DesiredCapabilities();
								
	/*			
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
				
				cap.setJavascriptEnabled(true);	
				
				 Proxy proxy = new Proxy();
		         String proxyServer = String.format("AProxyIDontWantToDisplay", System.getenv("proxy.username"), System.getenv("proxy.password"));
		         proxy.setHttpProxy(proxyServer);
		         cap.setCapability("proxy", proxy);
				
		         cap.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true)		;	
				
				Browser.Driver = new ChromeDriver(cap);
			*/
				} else {
			}
		}
		
		Browser.Driver.manage().window().maximize();
		Browser.Driver.manage().timeouts().implicitlyWait(Constants.TimeOut, TimeUnit.SECONDS);
		Browser.Driver.manage().timeouts().pageLoadTimeout(Constants.TimeOut, TimeUnit.SECONDS);
		Browser.Driver.manage().timeouts().setScriptTimeout(Constants.TimeOut, TimeUnit.SECONDS);
		
		Browser.Wait = new WebDriverWait(Browser.Driver, Constants.TimeOut);
	//	Constants.CurrentHandle.add(Browser.Driver.getWindowHandle());
		
		Result = new TCResult();
		
		System.out.println("Test case: " + pTestMethod.getName() + ".");
	}

	@AfterMethod(groups = { TestConfig.Initialization })
	public void afterMethod(ITestResult pResult) {
		LocalTime t = LocalTime.now();
		
		switch(pResult.getStatus()){
		case ITestResult.SUCCESS:
			System.out.println("Result: PASSED.");
			break;
		case ITestResult.FAILURE:
			System.out.println("Result: FAILED.");
			Browser.takeScreenshot(pResult.getTestClass().getName(), pResult.getName(), t) ;
			
			break;
		case ITestResult.SKIP:
			System.out.println("Result: SKIPPED.");
			break;
		}
		
		Browser.Driver.close();
		
		try {
			Browser.Driver.quit();
		} catch (Exception e) {

		}
		
	}

	@BeforeSuite(groups = { TestConfig.Initialization })
	public void beforeTest() {
		try {
			File inputFile = new File(Constants.Init_Folder + "/Init.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			
			//------------- Global Parking site user ------
			
			NodeList nList = doc.getElementsByTagName("ANPRGlobal");
			Constants.Browsertype =  ((Element) nList.item(0)).getElementsByTagName("Browser").item(0).getTextContent();
			Constants.globalUrl = ((Element) nList.item(0)).getElementsByTagName("URL").item(0).getTextContent();
			if (System.getProperty("Environment") != null){
				Constants.globalUrl = System.getProperty("Environment");
				System.out.println(System.getProperty("Environment"));
			}
			Constants.GlobalUsername = ((Element) nList.item(0)).getElementsByTagName("Username").item(0).getTextContent();
			Constants.Password = ((Element) nList.item(0)).getElementsByTagName("Password").item(0).getTextContent();
//			Constants.Broker = ((Element) nList.item(0)).getElementsByTagName("Broker").item(0).getTextContent();
//			Constants.ProcessOwner = ((Element) nList.item(0)).getElementsByTagName("ProcessOwner").item(0)
//					.getTextContent();
			Constants.GlobalFullName = ((Element) nList.item(0)).getElementsByTagName("FullName").item(0).getTextContent();
			Constants.GlobalAuthor = ((Element) nList.item(0)).getElementsByTagName("Author").item(0).getTextContent();
			
			
			//------------- Local Parking site user ------
			NodeList nList2 = doc.getElementsByTagName("ANPRLocal");
			Constants.Browsertype =  ((Element) nList2.item(0)).getElementsByTagName("Browser").item(0).getTextContent();
			Constants.localUrl = ((Element) nList2.item(0)).getElementsByTagName("URL").item(0).getTextContent();
			if (System.getProperty("Environment") != null){
				Constants.localUrl = System.getProperty("Environment");
				System.out.println(System.getProperty("Environment"));
			}
			Constants.LocalUsername = ((Element) nList2.item(0)).getElementsByTagName("Username").item(0).getTextContent();
			Constants.LocalUserPass = ((Element) nList2.item(0)).getElementsByTagName("Password").item(0).getTextContent();
//			Constants.Broker = ((Element) nList2.item(0)).getElementsByTagName("Broker").item(0).getTextContent();
//			Constants.ProcessOwner = ((Element) nList2.item(0)).getElementsByTagName("ProcessOwner").item(0)
//					.getTextContent();
			Constants.LocalFullName = ((Element) nList2.item(0)).getElementsByTagName("FullName").item(0).getTextContent();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterSuite(groups = { TestConfig.Initialization })
	public void afterTest() {
		Browser.Driver.quit();
	}
	
	
	
}