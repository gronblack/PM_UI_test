package com.example.pm_ui_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class PlanetsPageTest extends BaseTest {

    @BeforeEach
    void goToPage() throws InterruptedException {
        mainPage.planetsLink.click();
        delay();
        initCommonElements();
    }

    @Test
    void getAllPlanets() throws InterruptedException {
        String page2XPath = "//*[@id='paging-bar']/li[2]/a";
        if (isElementPresent(By.xpath(page2XPath))) {
            getElement(driver, By.xpath(page2XPath)).click();
        }
        delay();
        assertTrue(mainTable.isDisplayed());
    }

    @Test
    void editPlanet() throws InterruptedException {
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

        String lordIdUpd = "10";
        WebElement modalLordId = getElement(driver, By.cssSelector("#modal-lord-id"));
        modalLordId.clear();
        modalLordId.sendKeys(lordIdUpd);
        delay();

        modalSaveBtn.click();
        delay();

        WebElement row = mainTable.findElement(By.cssSelector("tr:first-child"));
        WebElement name = row.findElement(By.cssSelector("[data-name]"));
        WebElement lordId = row.findElement(By.cssSelector("[data-lord-id] input"));
        assertEquals(nameUpd, name.getText());
        assertEquals(lordIdUpd, lordId.getAttribute("value"));
    }

    @Test
    void createPlanet() throws InterruptedException {
        WebElement modalLordId = getElement(driver, By.cssSelector("#modal-lord-id"));

        order.selectByValue("name");
        reloadBtn.click();
        delay();

        createBtn.click();
        delay();
        assertTrue(modal.isDisplayed());

        String nameNew = "!!New Planet";
        String lordIdNew = "10";
        modalName.sendKeys(nameNew);
        modalLordId.sendKeys(lordIdNew);
        delay();
        modalSaveBtn.click();
        delay();

        WebElement row = mainTable.findElement(By.cssSelector("tr:first-child"));
        WebElement name = row.findElement(By.cssSelector("[data-name]"));
        WebElement lordId = row.findElement(By.cssSelector("[data-lord-id] input"));
        assertEquals(nameNew, name.getText());
        assertEquals(lordIdNew, lordId.getAttribute("value"));
    }

    @Test
    void deletePlanet() throws InterruptedException {
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

    @Test
    void setLord() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement row = mainTable.findElement(By.cssSelector("tr:first-child"));
        WebElement lordId = row.findElement(By.cssSelector("[data-lord-id] input"));
        String idOld = lordId.getAttribute("value");

        String idNew = "3";
        lordId.sendKeys(Keys.chord(Keys.CONTROL, "a"), idNew, Keys.ENTER);
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (Exception ignored) {
        }
        delay();

        assertEquals(idNew, lordId.getAttribute("value"));
        assertNotEquals(idOld, lordId.getAttribute("value"));
    }
}
