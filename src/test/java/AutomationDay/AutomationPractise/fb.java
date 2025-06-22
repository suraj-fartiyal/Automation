package AutomationDay.AutomationPractise;

import org.openqa.selenium.chrome.ChromeDriver;

public class fb {
    public static void main(String[] args) throws InterruptedException {
        ChromeDriver driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.facebook.com");
        Thread.sleep(1000);  // Pauses execution for 1 second

        driver.close();

    }

}
