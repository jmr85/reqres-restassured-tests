package apitests;

import org.testng.annotations.*;

import io.qameta.allure.testng.*;

import io.qameta.allure.*;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

@Epic("Reqres Auth API")
@Feature("Authentication")
@Owner("Juan Martin Ruiz")
public class AuthTest extends BaseAPITest {

    @DataProvider(name = "credencialesRegistro")
    public Object[][] credencialesRegistro() {
        return new Object[][] {
                {"eve.holt@reqres.in", "pistol"},
                {"janet.weaver@reqres.in", "pistol"}
        };
    }

    @DataProvider(name = "credencialesLogin")
    public Object[][] credencialesLogin() {
        return new Object[][] {
                {"eve.holt@reqres.in", "cityslicka"},
                {"janet.weaver@reqres.in", "superpassword"}
        };
    }
    
    // ✅ Registro exitoso
    @Test(groups = {"auth", "positiva"}, dataProvider = "credencialesRegistro")
    @Story("Registro de usuario")
    @Description("Registra un usuario correctamente")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("POST")
    @Link(name = "API Documentation", url = "https://reqres.in/api-docs/#/default/post_register")
    public void registroExitoso(String email, String password) {
        String body = String.format("""
            {
                "email": "%s",
                "password": "%s"
            }
        """, email, password);

        given()
                .spec(getRequestSpec()) 
                .body(body)
                .when()
                .post("/register")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue())
                .log().all();
    }

    // ❌ Registro fallido (sin password)
    @Test(groups = {"auth", "negativa"})
    @Story("Registro de usuario fallido")
    @Description("Falla el registro cuando falta el password")
    @Severity(SeverityLevel.NORMAL)
    @Tag("POST")
    @Link(name = "API Documentation", url = "https://reqres.in/api-docs/#/default/post_register")
    public void registroFallidoSinPassword() {
        String body = """
            {
                "email": "eve.holt@reqres.in"
            }
        """;

        given()
                .spec(getRequestSpec()) 
                .body(body)
                .when()
                .post("/register")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"))
                .log().all();
    }

    // ✅ Login exitoso
    @Test(groups = {"auth", "positiva"}, dataProvider = "credencialesLogin")
    @Story("Login de usuario")
    @Description("Login exitoso con email y password válidos")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("POST")
    @Link(name = "API Documentation", url = "https://reqres.in/api-docs/#/default/post_login")
    public void loginExitoso(String email, String password) {
        String body = String.format("""
            {
                "email": "%s",
                "password": "%s"
            }
        """, email, password);

        given()
                .spec(getRequestSpec()) 
                .body(body)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .log().all();
    }

    // ❌ Login fallido (sin password)
    @Test(groups = {"auth", "negativa"})
    @Story("Login fallido")
    @Description("Falla el login cuando falta el password")
    @Severity(SeverityLevel.NORMAL)
    @Tag("POST")
    @Link(name = "API Documentation", url = "https://reqres.in/api-docs/#/default/post_login")
    public void loginFallidoSinPassword() {
        String body = """
            {
                "email": "peter@klaven"
            }
        """;

        given()
                .spec(getRequestSpec()) 
                .body(body)
                .when()
                .post("/login")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"))
                .log().all();
    }

}
