package AutomationDay.AutomationPractise;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Collections;

public class Gmailautomation {

    WebDriver driver;
    WebDriverWait wait;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        // Extent Report setup
        ExtentSparkReporter spark = new ExtentSparkReporter("GmailTestReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        // Chrome setup
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    @Test(priority = 1)
    public void loginToGmail() {
        test = extent.createTest("Gmail Login Test");
        try {
            driver.get("https://mail.google.com");

            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId")));
            emailInput.sendKeys("fartiyalsuraj2598@gmail.com");
            driver.findElement(By.id("identifierNext")).click();

            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd")));
            passwordInput.sendKeys("Gmail@123!@#");
            driver.findElement(By.id("passwordNext")).click();

            wait.until(ExpectedConditions.titleContains("Inbox"));
            test.pass("Login successful");
        } catch (Exception e) {
            test.fail("Login failed: " + e.getMessage());
        }
    }

    @Test(priority = 2, dependsOnMethods = "loginToGmail")
    public void sendMultipleEmails() {
        test = extent.createTest("Send Multiple Emails");

        String[] recipients = {
                "amit19012014@gmail.com",
                "garvitnigam007@gmail.com",
                "garvitchitransh@gmail.com"
        };

        for (int i = 0; i < 10; i++) {
            for (String recipient : recipients) {
                String subject = "Test email " + (i + 1) + " to " + recipient;
                String body = "This is a test email sent using Selenium WebDriver.\n\n";
                try {
                    composeAndSendEmail(recipient, "garvitnigam007@gmail.com", "suraj7080312901@gmail.com", subject, body);
                    test.pass("Email sent to: " + recipient);
                } catch (Exception e) {
                    test.fail("Failed to send email to " + recipient + ": " + e.getMessage());
                }
            }
        }
    }

    public void composeAndSendEmail(String to, String cc, String bcc, String subject, String content) {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement composeButton = shortWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Compose')]")));
        composeButton.click();

        WebElement toInput = shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label='To recipients']")));
        toInput.sendKeys(to);

        WebElement ccButton = shortWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Add Cc recipients ‪(Ctrl-Shift-C)‬']")));
        ccButton.click();
        WebElement ccInput = shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[aria-label='CC recipients']")));
        ccInput.sendKeys(cc);

        WebElement bccButton = shortWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Add Bcc recipients ‪(Ctrl-Shift-B)‬']")));
        bccButton.click();
        WebElement bccInput = shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[aria-label='BCC recipients']")));
        bccInput.sendKeys(bcc);

        WebElement subjectInput = driver.findElement(By.name("subjectbox"));
        subjectInput.sendKeys(subject);

        WebElement messageBody = driver.findElement(By.cssSelector("div[aria-label='Message Body']"));
        messageBody.click();
        messageBody.sendKeys(content);

        WebElement sendButton = driver.findElement(By.xpath("//div[@role='button' and text()='Send']"));
        sendButton.click();

        shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Message sent')]")));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }
}
