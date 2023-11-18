import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class secondLab{

    private WebDriver chromeDriver;
    private static final String baseUrl = "https://petstore.swagger.io/v2";

    @BeforeClass(alwaysRun = true)
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-fullscreen");
        chromeOptions.setHeadless(true);
        this.chromeDriver = new ChromeDriver(chromeOptions);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        chromeDriver.quit();
    }

    @Test
    public void testGetPetById() {
        int petId = 1;
        Response response = RestAssured.get(baseUrl + "/pet/" + petId);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getBody().asString().contains("id"));
        Assert.assertTrue(response.getBody().asString().contains("name"));
    }

    @Test
    public void testCreateNewPet() {

        String requestBody = "{ \"id\": 123, \"name\": \"YourPetName\", \"status\": \"available\" }";

        Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .post(baseUrl + "/pet");

        Assert.assertEquals(response.getStatusCode(), 200);


        Assert.assertTrue(response.getBody().asString().contains("id"));
        Assert.assertTrue(response.getBody().asString().contains("name"));
        Assert.assertTrue(response.getBody().asString().contains("status"));
    }


}