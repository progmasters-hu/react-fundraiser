package com.progmasters.fundraiser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class RegistrationTest {

    static String driverPath = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\";
    public static WebDriver driver;
    private LoginTest loginTest = new LoginTest();

    @BeforeClass
    public void setUp() {
        System.out.println("*******************");
        System.out.println("launching chrome browser");
        System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }


    @Test
    public void testRegistration() throws InterruptedException {
        driver.navigate().to("http://localhost:3000/");
        Thread.sleep(1000);

        WebElement currentElement = driver.findElement(By.xpath("(//button)[2]"));
        currentElement.click();

        currentElement = driver.findElement(By.xpath("(//input[@name='userName'])"));
        currentElement.sendKeys("Gerzson");
        Thread.sleep(500);

        currentElement = driver.findElement(By.xpath("(//input[@name='password'])"));
        currentElement.sendKeys("Password111");
        Thread.sleep(500);

        currentElement = driver.findElement(By.xpath("(//input[@name='email'])"));
        currentElement.sendKeys("team.bobok@gmail.com");
        Thread.sleep(500);

        currentElement = driver.findElement(By.xpath("(//input[@name='goal'])"));
        currentElement.sendKeys("Art School");
        Thread.sleep(500);

        currentElement = driver.findElement(By.xpath("(//textarea[@name='goalBody'])"));
        currentElement.sendKeys("Lorem ipsum dolor sit amet, in ius case qualisque elaboraret, \n" +
                "his nihil molestie et, sit eu justo petentium disputationi. \n" +
                "Eum saepe tation appellantur id. Aeterno malorum propriae pro te, nec atqui audiam admodum at. ");
        Thread.sleep(500);


        WebElement fileInput = driver.findElement(By.name("file"));
        fileInput.sendKeys("C:\\Users\\sando\\Desktop\\pictures\\art.jpg");
        Thread.sleep(500);

        currentElement = driver.findElement(By.xpath("(//select[@name='currencyType'])"));
        currentElement.click();
        Thread.sleep(500);

        currentElement = driver.findElement(By.xpath("(//option[@value='EUR'])"));
        currentElement.click();
        Thread.sleep(500);

        currentElement = driver.findElement(By.xpath("(//button[@type='submit'])"));
        currentElement.click();

        currentElement = driver.findElement(By.xpath("(//button)[2]"));
        currentElement.click();

        currentElement = driver.findElement(By.xpath("(//button[@type='submit'])"));
        currentElement.click();
        Thread.sleep(1000);

        WebElement helpBlockElement = driver.findElement(By.className("help-block"));
        String errorText = helpBlockElement.getAttribute("innerText");
        Assert.assertTrue(errorText.equalsIgnoreCase("Invalid username or password"));

        currentElement = driver.findElement(By.xpath("(//input[@name='userName'])"));
        currentElement.sendKeys("Gerzson");
        Thread.sleep(1000);

        currentElement = driver.findElement(By.xpath("(//input[@name='password'])"));
        currentElement.sendKeys("Password111");
        Thread.sleep(1000);

        currentElement = driver.findElement(By.xpath("(//button[@type='submit'])"));
        currentElement.click();
        Thread.sleep(1000);

        currentElement = driver.findElement(By.xpath("(//button[@class='btn btn-primary btn-sm orange-button'])"));
        currentElement.click();
        Thread.sleep(2000);

        currentElement = driver.findElement(By.xpath("(//input[@name='amount'])"));
        currentElement.sendKeys("400");
        Thread.sleep(2000);

        currentElement = driver.findElement(By.xpath("(//button[@type='submit'])"));
        currentElement.click();
        Thread.sleep(2000);

        openGmail();

        currentElement = driver.findElement(By.xpath("(//a[@href='/accounts'])"));
        currentElement.click();
        Thread.sleep(2000);

        currentElement = driver.findElement(By.xpath("(//a[@href='/logout'])"));
        currentElement.click();
        Thread.sleep(2000);

        String strPageTitle = driver.getTitle();
        System.out.println("Page title: - " + strPageTitle);
        Assert.assertTrue(strPageTitle.equalsIgnoreCase("Fundraiser"), "Page title doesn't match");

    }

    @Test
    private void openGmail() throws InterruptedException {
        WebElement currentElement;
        driver.navigate().to("https://mail.google.com/mail/u/0/#inbox");
        Thread.sleep(2000);

        currentElement = driver.findElement(By.xpath("(//input[@type='email'])"));
        currentElement.sendKeys("team.bobok@gmail.com");
        Thread.sleep(1000);
        currentElement = driver.findElement(By.xpath("//*[text() = 'Next']"));
        currentElement.click();
        Thread.sleep(2000);
        currentElement = driver.findElement(By.xpath("(//input[@type='password'])"));
        currentElement.sendKeys("pm2018jan");
        Thread.sleep(1000);
        currentElement = driver.findElement(By.xpath("//*[text() = 'Next']"));
        currentElement.click();
        Thread.sleep(5000);

        currentElement = driver.findElement(By.cssSelector("div.xT>div.y6>span>span"));
        currentElement.click();
        Thread.sleep(1000);

        currentElement = driver.findElement(By.cssSelector("div.a3s>a"));
        currentElement.click();
        Thread.sleep(1000);
    }


    @AfterClass
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            System.out.println("Closing chrome browser");
            Thread.sleep(5000);
            driver.quit();
        }
    }

}