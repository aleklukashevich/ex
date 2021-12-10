package com.pearson;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        Configuration.browser =
                StringUtils.isEmpty(System.getProperty("browser")) ? "chrome" : System.getProperty("browser");
        LOG.info("Start test on env: '{}' with browser: '{}'", System.getProperty("env"), Configuration.browser);
    }

    @AfterClass(alwaysRun = true)
    public void wipeOut() {
        System.out.println("CLEAN");
    }
}
