package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class FirstLab {

    private WebDriver edgeDriver;
    private static final String baseUrl = "https://rozetka.com.ua/";

    @BeforeClass(alwaysRun = true)
    public void setUp(){
        WebDriverManager.edgedriver().setup();
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--start-fullscreen");
        edgeOptions.setImplicitWaitTimeout(Duration.ofSeconds(15));
        this.edgeDriver = new EdgeDriver(edgeOptions);
    }

    @BeforeMethod
    public void preconditions(){
        edgeDriver.get(baseUrl);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){edgeDriver.quit();}

    @Test
    public void testLab(){
        if(edgeDriver.findElements(By.className("search-form__input")) != null && edgeDriver.findElement(By.className("search-form__submit")) != null){
            WebElement inputSearch = edgeDriver.findElement(By.className("search-form__input"));
            Assert.assertNotNull(inputSearch);
            inputSearch.sendKeys("apple homepod");
            Assert.assertNotEquals(edgeDriver.getCurrentUrl(), baseUrl);

            WebElement shopsButton = edgeDriver.findElement(By.className("search-form__submit"));
            Assert.assertNotNull(shopsButton);
            shopsButton.click();
            Assert.assertNotEquals(edgeDriver.getCurrentUrl(), baseUrl);
        }
    }
}