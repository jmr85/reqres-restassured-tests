package apitests;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;

import static io.restassured.RestAssured.given;

@Epic("Reqres User API")
@Feature("User Management")
@Owner("Juan Martin Ruiz")
public class APITest extends BaseAPITest {

    @Test
    @Story("Crear usuario")
    @Owner("Juan Martin Ruiz")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Crea un usuario nuevo con nombre y trabajo")
    @Tag("POST")
    public void crearUsuario() {
        String body = """
            {
                "name": "free range",
                "job": "patito"
            }
        """;

        given()
                .spec(getRequestSpec())
                .body(body)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .log().all();
    }

    @Test
    @Story("Actualizar usuario")
    @Owner("Juan Martin Ruiz")
    @Severity(SeverityLevel.NORMAL)
    @Description("Actualiza los datos de un usuario existente")
    @Tag("PUT")
    public void actualizarUsuario() {
        String body = """
            {
                "name": "John Doe",
                "job": "Writer"
            }
            """;

        given()
                .spec(getRequestSpec())
                .body(body)
                .when()
                .put("/users/690")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    @Story("Borrar usuario")
    @Owner("Juan Martin Ruiz")
    @Severity(SeverityLevel.MINOR)
    @Description("Elimina un usuario existente")
    @Tag("DELETE")
    public void borrarUsuario() {
        given()
                .spec(getRequestSpec())
                .when()
                .delete("/users/690")
                .then()
                .statusCode(204)
                .log().all();
    }
}
