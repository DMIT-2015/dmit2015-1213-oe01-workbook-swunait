package dmit2015.view;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieCrudSeleniumIT {

    private WebDriver driver;

    static Long sharedEditId;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();

        // https://www.omgubuntu.co.uk/2022/04/how-to-install-firefox-deb-apt-ubuntu-22-04
//        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();

//        driver = new FirefoxDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    private void setValue(String id, String value) {
        WebElement element = driver.findElement(By.id(id));
        element.clear();
        element.sendKeys(value);
    }

    @Order(3)
    @Test
    void shouldCreate() {
        driver.get("http://localhost:8080/movies/create.xhtml");
        assertEquals("Create Movie", driver.getTitle());

        setValue("title","Java 17 Release Party");
        setValue("genre","Horror");
        setValue("rating","NC-17");
        setValue("price","19.99");
        setValue("releaseDate_input","2021-09-14");

        driver.manage().window().maximize();
        driver.findElement(By.id("createButton")).click();

        var wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        var facesMessages = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-messages-info-summary")));
        assertEquals("Movie - List", driver.getTitle());
        String feedbackMessage = facesMessages.getText();

        assertThat(feedbackMessage, containsString("Create was successful."));
        final int indexOfPrimaryKeyValue = feedbackMessage.indexOf(".") + 2;
        sharedEditId = Long.parseLong(feedbackMessage.substring(indexOfPrimaryKeyValue));
    }

    @Order(1)
    @Test
    void shouldList() {
        driver.get("http://localhost:8080/movies/index.xhtml");
        assertEquals("Movie - List", driver.getTitle());

        String firstRowTitle = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[1]/td[1]")).getText();
        String firstRowGenre = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[1]/td[2]")).getText();
        String firstRowRating = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[1]/td[3]")).getText();
        String firstRowPrice = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[1]/td[4]")).getText();
        String firstRowReleaseDate = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[1]/td[5]")).getText();

        assertEquals("When Harry Met Sally", firstRowTitle);
        assertEquals("Romantic Comedy", firstRowGenre);
        assertEquals("G", firstRowRating);
        assertEquals("$7.99", firstRowPrice);
//        assertEquals("Feb 12, 1989", firstRowReleaseDate);

        String lastRowTitle = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[4]/td[1]")).getText();
        String lastRowGenre = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[4]/td[2]")).getText();
        String lastRowRating = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[4]/td[3]")).getText();
        String lastRowPrice = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[4]/td[4]")).getText();
//        String lastRowReleaseDate = driver.findElement(By.xpath("//table[@role='grid']/tbody/tr[4]/td[5]")).getText();

        assertEquals("Rio Bravo", lastRowTitle);
        assertEquals("Western", lastRowGenre);
        assertEquals("PG-13", lastRowRating);
        assertEquals("$7.99", lastRowPrice);
//        assertEquals("Apr 15, 1959", lastRowReleaseDate);

        driver.findElements(By.xpath("//a[contains(@id,'editLink')]")).get(0).click();
        assertEquals("Edit Movie", driver.getTitle());
        driver.navigate().back();

        driver.findElements(By.xpath("//a[contains(@id,'detailsLink')]")).get(0).click();
        assertEquals("Movie Details", driver.getTitle());
        driver.navigate().back();

        driver.findElements(By.xpath("//a[contains(@id,'deleteLink')]")).get(0).click();
        assertEquals("Delete Movie", driver.getTitle());
        driver.navigate().back();
    }

    @Order(4)
    @Test
    void shouldEdit() {
        driver.get("http://localhost:8080/movies/edit.xhtml?editId=" + sharedEditId);
        assertEquals("Edit Movie", driver.getTitle());

        setValue("title","Java 18 Release Party");
        setValue("genre","Action");
        setValue("rating","G");
        setValue("price","29.99");
        var releaseDateElement = driver.findElement(By.id("releaseDate_input"));
        releaseDateElement.click();
        releaseDateElement.sendKeys(Keys.CONTROL,"a");
        releaseDateElement.sendKeys("2022-03-22");

        driver.manage().window().maximize();
        driver.findElement(By.id("updateButton")).click();

        var wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        var facesMessages = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-messages-info-summary")));
        assertEquals("Movie - List", driver.getTitle());
        assertThat(facesMessages.getText(), containsString("Update was successful."));
    }

    @Order(2)
    @Test
    void shouldDetails() {
        driver.get("http://localhost:8080/movies/details.xhtml?editId=2");
        assertEquals("Movie Details", driver.getTitle());

        var actualTitle = driver.findElement(By.id("title")).getText();
        var actualGenre = driver.findElement(By.id("genre")).getText();
        var actualRating = driver.findElement(By.id("rating")).getText();
        var actualPrice = driver.findElement(By.id("price")).getText();
        var actualReleaseDate = driver.findElement(By.id("releaseDate")).getText();

        assertEquals("Ghostbusters", actualTitle);
        assertEquals("Comedy", actualGenre);
        assertEquals("PG", actualRating);
        assertEquals("1984-03-13", actualReleaseDate);
        assertEquals("8.99", actualPrice);

    }

    @Order(5)
    @Test
    void shouldDelete() {
        driver.get("http://localhost:8080/movies/delete.xhtml?editId=" + sharedEditId);
        assertEquals("Delete Movie", driver.getTitle());

        driver.findElement(By.id("deleteButton")).click();

        var wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        var yesConfirmationButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-confirmdialog-yes")));
        yesConfirmationButton.click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        var facesMessages = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-messages-info-summary")));
        assertEquals("Movie - List", driver.getTitle());
        assertThat(facesMessages.getText(), containsString("Delete was successful."));
    }

}