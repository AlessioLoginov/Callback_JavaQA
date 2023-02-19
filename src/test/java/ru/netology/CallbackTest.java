package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallbackTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldBeValidTest() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Алексей Логинов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79113345476");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual.trim());

    }

    @Test
    void shouldBeNoValidTestInvName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Alessio Loginov");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79113345476");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.trim());
    }

    @Test
    void shouldBeNoValidTestNotName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79113345476");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }


    @Test
    void shouldBeNoValidTestInvPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Алексей Логинов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("89113345476");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }


    @Test
    void shouldBeNoValidTestNotPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Алексей Логинов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }

    @Test
    void shouldBeNoValidTestNotCheck() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Алексей Логинов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79113345476");
        driver.findElement(By.className("button__text")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", actual.trim());
    }

}

