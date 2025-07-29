package apitests;

import org.testng.annotations.*;
import org.testng.Assert;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anyOf;

@Epic("Reqres User API")
@Feature("Idempotencia")
@Owner("Juan Martin Ruiz")
public class IdempotencyTest extends BaseAPITest {
  
    @Test(groups = {"idempotencia"}, description = "GET idempotente")
    @Story("Verificar idempotencia en GET")
    @Description("La misma petici\u00f3n GET /users/2 debe devolver la misma respuesta")
    @Severity(SeverityLevel.NORMAL)
    @Tag("GET")
    @Link(name = "API Documentation", url = "https://reqres.in/api-docs/#/default/get_users__id_")
    @Parameters({"get_userId"})
    public void getUsuarioEsIdempotente(@Optional("2") String get_userId) {
        Response primera =
                given()
                        .spec(getRequestSpec())
                .when()
                        .get("/users/" + get_userId);

        Response segunda =
                given()
                        .spec(getRequestSpec())
                .when()
                        .get("/users/" + get_userId);

        primera.then().statusCode(200);
        segunda.then().statusCode(200);
        Assert.assertEquals(segunda.asString(), primera.asString());
    }

    @Test(groups = {"idempotencia"}, description = "DELETE /users/{id} debería ser idempotente")
    @Story("Verificar idempotencia en DELETE")
    @Description("Repetir DELETE /users/2 debe responder con status code 404 en la segunda vez")
    @Severity(SeverityLevel.MINOR)
    @Tag("DELETE")
    @Link(name = "API Documentation", url = "https://reqres.in/api-docs/#/default/delete_users__id_")
    @Parameters({"delete_userId"})
    public void deleteUsuarioEsIdempotente(@Optional("690") String delete_userId) {
        Response primera =
                given()
                        .spec(getRequestSpec())
                .when()
                        .delete("/users/" + delete_userId);

        Response segunda =
                given()
                        .spec(getRequestSpec())
                .when()
                        .delete("/users/" + delete_userId);

        primera.then().statusCode(204);
        segunda.then().statusCode(404);
    }
}
