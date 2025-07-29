package apitests;

import org.testng.annotations.*;

import io.qameta.allure.*;
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
    @Link(name = "API Documentation", url = "https://reqres.in/api-docs/#/default/get_users__id_")
    @Parameters({"userId", "schemaPath"})
    public void validarSchemaUsuarioPorId(@Optional("2") String userId, @Optional("schemas/user-schema.json") String schemaPath) {
        given()
                .spec(getRequestSpec())
                .when()
                .get("/users/" + userId)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(schemaPath));
    }
}
