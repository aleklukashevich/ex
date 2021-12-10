package com.pearson.regression;

import com.pearson.BaseTest;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class DuckSearchTest extends BaseTest {

  @Test
  @Story("111")
  public void userCanSearchAnyKeyword() {
    open("https://duckduckgo.com/");
    $(By.name("q")).val("selenide").pressEnter();
    $$(".js-results").shouldHave(size(1));
    $$(".js-results .result").shouldHave(sizeGreaterThan(5));
    $(".js-results .result").shouldHave(text("selenide.org"));
  }
}
