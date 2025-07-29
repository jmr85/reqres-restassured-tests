package apitests;

import static org.hamcrest.Matchers.lessThan;
import org.testng.annotations.Test;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import static io.restassured.RestAssured.given;

@Epic("Reqres User API")
@Feature("Performance")
@Owner("Juan Martin Ruiz")
public class PerformanceTest extends BaseAPITest {

    @Test(groups = "performance", description = "Verifica que la respuesta sea menor a 2 segundos")
    @Story("Validar tiempo de respuesta")
    @Description("Verifica que el endpoint GET /users?page=2 responda en menos de 2 segundos")
    @Severity(SeverityLevel.MINOR)
    @Tag("GET")
    @Link(name = "API Documentation", url = "https://reqres.in/api-docs/#/default/get_users")
    public void respuestaRapida() {
        given()
                .spec(getRequestSpec())
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .time(lessThan(2000L));
    }
}
