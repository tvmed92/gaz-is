import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class TestClass extends BaseRunner {

    @Test
    public void test() {
        driver.get(yandexUrl);
        driver.findElement(By.xpath("//input[@class='input__control input__input']"))
                .sendKeys("Газинформсервис");
        driver.findElement(By.xpath("//button[span[text()='Найти']]")).click();
        By listItems = By.xpath("//a[@href='https://www.gaz-is.ru/']");
        List<WebElement> items = driver.findElements(listItems);
        wait
                .ignoring(StaleElementReferenceException.class)
                .pollingEvery(Duration.ofMillis(500))
                .until(driver -> {
                    for (WebElement element : items) {
                        if (element.getText().contains(" - информационная безопасность")) {
                            element.click();
                            break;
                        }
                    }
                    Set<String> ids = driver.getWindowHandles();
                    ids.forEach(id -> {
                        if (!id.equals(driver.getWindowHandle())) {
                            driver.switchTo().window(id);
                        }
                    });
                    return driver.getTitle().equals("Газинформсервис - информационная безопасность");
                });
    }
}
