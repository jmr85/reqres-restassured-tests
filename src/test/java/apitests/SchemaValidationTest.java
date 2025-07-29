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
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("Reqres User API")
@Feature("Validación de Schemas")
@Owner("Juan Martin Ruiz")
public class SchemaValidationTest extends BaseAPITest {

    @Test(groups = "schema", description = "Valida el schema del usuario por ID")
    @Story("Validación de esquema JSON")
    @Description("Valida que la respuesta del endpoint GET /users/2 cumpla con el esquema JSON definido")
    @Severity(SeverityLevel.NORMAL)
    @Tag("GET")
    public void validarSchemaUsuarioPorId() {
        given()
                .spec(getRequestSpec())
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
    }
}
