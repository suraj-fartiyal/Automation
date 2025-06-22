package AutomationDay.AutomationPractise;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Collections;


public class Gmailautomation {
    public static void main(String[] args) {
        // Initialize ChromeDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        //WebDriver driver = new ChromeDriver();
        ChromeDriver driver = new ChromeDriver(options);

        // Maximize the browser window
        driver.manage().window().maximize();

        // Navigate to Gmail
        driver.get("https://mail.google.com");

        // Login to Gmail
        login(driver, "fartiyalsuraj2598@gmail.com", "Gmail@123!@#");

        // Recipients
        String[] recipients = {
                "amit19012014@gmail.com",
                "garvitnigam007@gmail.com",
                "recipient3@example.com"
        };

        for (int i = 0; i < 10; i++) {
            for (String recipient : recipients) {
                String subject = "Test email " + (i + 1) + " to " + recipient;
                String newContent = "This is a test email sent using Selenium WebDriver.\n\n";
                String cc = "garvitnigam007@gmail.com";
                String bcc = "suraj7080312901@gmail.com";
                composeAndSendEmail(driver, recipient, cc, bcc, subject, newContent);

                try {
                    Thread.sleep(2000); // delay to mimic human-like interaction
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // Quit browser
        driver.quit();
    }

    public static void login(WebDriver driver, String email, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId")));
        emailInput.sendKeys(email);
        driver.findElement(By.id("identifierNext")).click();

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd")));
        passwordInput.sendKeys(password);
        driver.findElement(By.id("passwordNext")).click();

        // Optional: wait until inbox is loaded
        wait.until(ExpectedConditions.titleContains("Inbox"));
    }

    public static void composeAndSendEmail(WebDriver driver, String to, String cc, String bcc, String subject, String newContent) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click Compose
        WebElement composeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Compose')]")));
        composeButton.click();

        // Wait for To input and enter recipient
        WebElement toInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label='To recipients']")));
        toInput.sendKeys(to);

        // Click Cc and enter CC email
        WebElement ccButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Add Cc recipients ‪(Ctrl-Shift-C)‬']")));
        ccButton.click();
        WebElement ccInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[aria-label='CC recipients']")));
        ccInput.sendKeys(cc);

        // Click Bcc and enter BCC email
        WebElement bccButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Add Bcc recipients ‪(Ctrl-Shift-B)‬']")));
        bccButton.click();
        WebElement bccInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[aria-label='BCC recipients']")));
        bccInput.sendKeys(bcc);

        // Enter subject
        WebElement subjectInput = driver.findElement(By.name("subjectbox"));
        subjectInput.sendKeys(subject);

        // Enter message body (do not use .clear() on contenteditable elements)
        WebElement messageBody = driver.findElement(By.cssSelector("div[aria-label='Message Body']"));
        messageBody.click();
        messageBody.sendKeys(newContent);

        // Click Send
        WebElement sendButton = driver.findElement(By.xpath("//div[@role='button' and text()='Send']"));
        sendButton.click();

        // Wait for confirmation (e.g., "Message sent" popup)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Message sent')]")));
    }
}
