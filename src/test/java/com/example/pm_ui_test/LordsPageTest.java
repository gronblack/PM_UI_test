package com.example.pm_ui_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LordsPageTest extends BaseTest {

    @Test
    void getTopYoung10Lords() throws InterruptedException {
        delay();
        mainPage.young10LordsLink.click();
        delay();
    }

    @Nested
    class PrimaryTests {
        @BeforeEach
        void goToPage() throws InterruptedException {
            mainPage.lordsLink.click();
            delay();
            LordsPageTest.super.initCommonElements();
        }

        @Test
        void getAllLords() throws InterruptedException {
            String page2XPath = "//*[@id='paging-bar']/li[2]/a";
            if (isElementPresent(By.xpath(page2XPath))) {
                getElement(driver, By.xpath(page2XPath)).click();
            }
            delay();
            assertTrue(mainTable.isDisplayed());
        }

        @Test
        void getIdleLords() throws InterruptedException {
            WebElement idleRadioBtn = getElement(driver, By.id("idle"));
            idleRadioBtn.click();
            delay();
            reloadBtn.click();
            delay();
        }

        @Test
        void editLord() throws InterruptedException {
            WebElement modalAge = getElement(driver, By.id("modal-age"));

            order.selectByValue("name");
            reloadBtn.click();
            delay();

            WebElement editBtn = getElement(driver, By.cssSelector("button[data-update]"));
            editBtn.click();
            delay();
            assertTrue(modal.isDisplayed());

            String nameUpd = "!Updated";
            modalName.clear();
            modalName.sendKeys(nameUpd);
            delay();

            String ageUpd = "100";
            modalAge.clear();
            modalAge.sendKeys(ageUpd);
            delay();

            modalSaveBtn.click();
            delay();

            WebElement row = mainTable.findElement(By.cssSelector("tr:first-child"));
            WebElement name = row.findElement(By.cssSelector("[data-name]"));
            WebElement age = row.findElement(By.cssSelector("[data-age]"));
            assertEquals(nameUpd, name.getText());
            assertEquals(ageUpd, age.getText());
        }

        @Test
        void createLord() throws InterruptedException {
            WebElement modalAge = getElement(driver, By.id("modal-age"));

            order.selectByValue("name");
            reloadBtn.click();
            delay();

            createBtn.click();
            delay();
            assertTrue(modal.isDisplayed());

            String nameNew = "!!New Lord";
            String ageNew = "888";
            modalName.sendKeys(nameNew);
            modalAge.sendKeys(ageNew);
            delay();
            modalSaveBtn.click();
            delay();

            WebElement row = mainTable.findElement(By.cssSelector("tr:first-child"));
            WebElement name = row.findElement(By.cssSelector("[data-name]"));
            WebElement age = row.findElement(By.cssSelector("[data-age]"));
            assertEquals(nameNew, name.getText());
            assertEquals(ageNew, age.getText());
        }

        @Test
        void deleteLord() throws InterruptedException {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement row = mainTable.findElement(By.cssSelector("tr:first-child"));
            String idText = row.findElement(By.cssSelector("[data-id]")).getText();
            WebElement deleteBtn = row.findElement(By.cssSelector("[data-delete]"));

            deleteBtn.click();
            delay();
            wait.until(ExpectedConditions.stalenessOf(row));
            delay();
            assertFalse(isElementPresent(By.xpath("//*[text()=" + idText + "]")));
        }
    }
}
