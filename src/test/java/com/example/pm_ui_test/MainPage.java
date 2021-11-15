package com.example.pm_ui_test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

    @FindBy(css = "a[href='/lords']")
    public WebElement lordsLink;

    @FindBy(css = "a[href='/planets']")
    public WebElement planetsLink;

    @FindBy(css = "a[href='/young10']")
    public WebElement young10LordsLink;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
