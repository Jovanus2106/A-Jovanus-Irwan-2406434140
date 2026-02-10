package id.ac.ui.cs.advprog.eshops.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumJupiter.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateProductFunctionalTest {

    @LocalServerPort
    private int port;

    @Test
    void testUserCanCreateProduct(WebDriver driver) {

        driver.get("http://localhost:" + port + "/product/create");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys("Produk Functional");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("10");

        driver.findElement(By.tagName("button")).click();

        assertTrue(
                driver.getPageSource().contains("Produk Functional")
        );
    }
}
