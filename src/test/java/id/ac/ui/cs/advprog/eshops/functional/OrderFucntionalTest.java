package id.ac.ui.cs.advprog.eshops.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class OrderFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d/order", testBaseUrl, serverPort);
    }

    @Test
    void testCreateOrderPage(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/create");
        String title = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Create New Order", title);
    }

    @Test
    void testOrderHistorySearchPage(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/history");
        String title = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Search Order History", title);
    }
}