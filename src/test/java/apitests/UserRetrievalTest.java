package apitests;

import org.testng.annotations.*;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Reqres User API")
@Feature("User Retrieval")
@Owner("Juan Martin Ruiz")
public class UserRetrievalTest extends BaseAPITest {

    @Test(groups = {"positiva"}, description = "Obtiene un usuario existente por ID")
    @Story("Obtener usuario por ID")
    @Description("Verifica que la API devuelve los datos correctos para un usuario existente")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("GET")
    @Link(name = "API Documentation", url = "https://reqres.in/api-docs/#/default/get_users__id_")
    @Parameters({"userId"})
    public void obtenerUsuarioExistente(@Optional("2") String userId) {
        given()
                .spec(getRequestSpec())
                .when()
                .get("/users/" + userId)
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", notNullValue());
    }

    @Test(groups = {"positiva"}, description = "Lista todos los usuarios de una pagina")
    @Story("Listar usuarios")
    @Description("Verifica que el endpoint GET /users?page=2 responde con una lista de usuarios")
    @Severity(SeverityLevel.MINOR)
    @Tag("GET")
    @Link(name = "API Documentation", url = "https://reqres.in/api-docs/#/default/get_users")
    @Parameters({"page"})
    public void listarUsuariosPaginaDos(@Optional("2") String page) {
        given()
                .spec(getRequestSpec())
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data", not(empty()));
    }
}
