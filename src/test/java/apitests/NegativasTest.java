package apitests;

import org.testng.annotations.*;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import static io.restassured.RestAssured.given;

@Epic("Reqres User API")
@Feature("Pruebas Negativas")
@Owner("Juan Martin Ruiz")
public class NegativasTest extends BaseAPITest {

    @Test(groups = {"negativa"}, description = "Usuario no encontrado")
    @Story("Usuario inexistente")
    @Description("Valida que la API responde 404 cuando el usuario no existe")
    @Severity(SeverityLevel.NORMAL)
    @Tag("GET")
    @Link(name = "API Documentation", url = "https://reqres.in/api-docs/#/default/get_users__id_")
    @Parameters({"missingUserId"})
    public void usuarioNoEncontrado(@Optional("23") String missingUserId) {
        given()
                .spec(getRequestSpec())
                .when()
                .get("/users/" + missingUserId)
                .then()
                .statusCode(404);
    }

    @Test(groups = "negativa", description = "Falla al crear un usuario sin body")
    @Story("Crear usuario con body vacío")
    @Description("Falla esperada al intentar crear un usuario sin body")
    @Severity(SeverityLevel.NORMAL)
    @Tag("POST")
    public void crearUsuarioSinBody() {
        given()
                .spec(getRequestSpec())
                .when()
                .post("/users")
                .then()
                .statusCode(400); // La API responde 200, pero deberia ser 400
    }

    @Test(groups = "negativa", description = "Falla al actualizar un usuario con campos inválidos")
    @Story("Actualizar usuario con campos inválidos")
    @Description("Falla esperada al actualizar un usuario usando campos no válidos o mal nombrados")
    @Severity(SeverityLevel.NORMAL)
    @Tag("PUT")
    @Parameters({"invalidUserId"})
    public void actualizarUsuarioCamposInvalidos(@Optional("2") String invalidUserId) {
        String body = """
            {
                "nombre_completo": "sin formato"
            }
        """;

        given()
                .spec(getRequestSpec())
                .body(body)
                .when()
                .put("/users/" + invalidUserId)
                .then()
                .statusCode(400); // La API responde 200, pero deberia ser 400
    }
}
