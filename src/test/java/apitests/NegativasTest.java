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
@Feature("Pruebas Negativas")
@Owner("Juan Martin Ruiz")
public class NegativasTest extends BaseAPITest {

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
    public void actualizarUsuarioCamposInvalidos() {
        String body = """
            {
                "nombre_completo": "sin formato"
            }
        """;

        given()
                .spec(getRequestSpec())
                .body(body)
                .when()
                .put("/users/2")
                .then()
                .statusCode(400); // La API responde 200, pero deberia ser 400
    }
}
