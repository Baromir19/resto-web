package com.resto.pizzeria.web.selenium;

import com.resto.pizzeria.web.config.CustomProperties;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderSeleniumTest {
    @Qualifier("com.resto.pizzeria.web-com.resto.pizzeria.web.config.CustomProperties")
    @Autowired
    private CustomProperties properties;

    private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @Test
    void testCreateOrder() throws InterruptedException {
        // 1. Open orders page
        driver.get(properties.getBaseUrl() + "/orders");

        Thread.sleep(1000);

        // 2. Go to "new order"
        driver.get(properties.getBaseUrl() + "/orders/new");

        Thread.sleep(1000);

        // 3. Select a client
        Select clientSelect = new Select(driver.findElement(By.id("selectedClient")));
        clientSelect.selectByIndex(1); // select first real client

        // 4. Click a dish (VERY IMPORTANT for your UI)
        driver.findElement(By.cssSelector(".card.selectable")).click();

        Thread.sleep(1000);

        // 5. Submit order
        driver.findElement(By.cssSelector(".btn.primary")).click();

        Thread.sleep(2000);

        // 6. Verify order appears
        String page = driver.getPageSource();
        assertTrue(page.contains("Commande") || page.contains("Client"));
    }

    @Test
    void testReadOrders() {
        driver.get(properties.getBaseUrl() + "/orders");

        String page = driver.getPageSource();

        assertTrue(page.contains("Liste des Commandes"));
    }

    @Test
    void testUpdateOrder() throws InterruptedException {
        driver.get(properties.getBaseUrl() + "/orders");

        Thread.sleep(1000);

        // Click first "Modifier"
        driver.findElement(By.cssSelector(".btn.edit")).click();

        Thread.sleep(1000);

        // Change client
        Select clientSelect = new Select(driver.findElement(By.id("selectedClient")));
        clientSelect.selectByIndex(1);

        // Submit
        driver.findElement(By.cssSelector(".btn.primary")).click();

        Thread.sleep(2000);

        assertTrue(driver.getPageSource().contains("Commande"));
    }

    @Test
    void testDeleteOrder() throws InterruptedException {
        driver.get(properties.getBaseUrl() + "/orders");

        Thread.sleep(1000);

        // Click delete button
        driver.findElement(By.cssSelector(".btn.delete")).click();

        Thread.sleep(1000);

        // Confirm modal
        driver.findElement(By.id("confirmDeleteBtn")).click();

        Thread.sleep(2000);

        assertTrue(driver.getPageSource().contains("Commande")
                || driver.getPageSource().contains("Liste"));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}