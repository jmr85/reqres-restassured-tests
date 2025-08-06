package apitests;

import org.testng.annotations.*;

import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;

import static io.restassured.RestAssured.given;

import apitests.models.User;

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
    @Parameters({"name", "job"})
    public void crearUsuario(String name, String job) {
    
        User newUser = new User(name, job);

        given()
                .spec(getRequestSpec())
                .body(newUser)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .log().all();
    }

    @Test(groups = "negativa", description = "Falla al actualizar un usuario con campos inválidos")
    @Story("Actualizar usuario")
    @Owner("Juan Martin Ruiz")
    @Severity(SeverityLevel.NORMAL)
    @Description("Actualiza los datos de un usuario existente")
    @Tag("PUT")
    @Link(name = "API Documentation", url = "https://reqres.in/api-docs/#/default/put_users__id_")
    @Parameters({"name", "job"})
    public void actualizarUsuario(String name, String job) {
  
        User editUser = new User(name, job);    

        given()
                .spec(getRequestSpec())
                .body(editUser)
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
    @Link(name = "API Documentation", url = "https://reqres.in/api-docs/#/default/delete_users__id_")
    @Parameters({"userId"})
    public void borrarUsuario(@Optional("690") String userId) {
        given()
                .spec(getRequestSpec())
                .when()
                .delete("/users/" + userId)
                .then()
                .statusCode(204)
                .log().all();
    }
}
