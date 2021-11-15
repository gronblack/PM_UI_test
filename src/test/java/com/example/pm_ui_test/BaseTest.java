package com.example.pm_ui_test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseTest {
    protected static final String baseURL = "http://localhost:8080/";

    protected static WebDriver driver;
    protected static MainPage mainPage;
    protected static final int defaultDelay = 1000;

    protected WebElement mainTable;
    protected WebElement reloadBtn;
    protected WebElement createBtn;
    protected WebElement modal, modalCloseBtn, modalSaveBtn, modalName;
    protected Select order;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseURL);
        mainPage = new MainPage(driver);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    protected void delay() throws InterruptedException {
        Thread.sleep(defaultDelay);
    }

    protected void initCommonElements() {
        order = new Select(getElement(driver, By.id("order")));

        mainTable = getElement(driver, By.id("mainTableBody"));
        reloadBtn = getElement(driver, By.cssSelector("button[data-reload]"));
        createBtn = getElement(driver, By.cssSelector("button[data-create]"));

        modal = getElement(driver, By.cssSelector("#modal .modal-dialog"));
        modalCloseBtn = getElement(driver, By.cssSelector("button.btn-secondary[data-modal-close]"));
        modalSaveBtn = getElement(driver, By.cssSelector("button[data-modal-send]"));
        modalName = getElement(driver, By.id("modal-name"));
    }

    protected static WebElement getElement(WebDriver webDriver, By by) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(5)).until(driver -> driver.findElement(by));
    }

    protected boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}
