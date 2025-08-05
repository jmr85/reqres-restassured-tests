package apitests;

import org.testng.annotations.BeforeMethod;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseAPITest {

    protected static final AllureRestAssured allureFilter =
        new AllureRestAssured()
            .setRequestTemplate("my-http-request.ftl")
            .setResponseTemplate("my-http-response.ftl");

    protected RequestSpecification requestSpec;

    @BeforeMethod
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.config = RestAssured.config()
            .logConfig(LogConfig.logConfig()
                .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL));
                
        System.out.println("=== SETUP EJECUTÁNDOSE ===");
        initializeRequestSpec();
        System.out.println("=== SETUP COMPLETADO ===");
    }
    
    private void initializeRequestSpec() {
        try {
            RequestSpecBuilder builder = new RequestSpecBuilder();
            builder.setBaseUri("https://reqres.in/api");
            builder.setContentType(ContentType.JSON);
            builder.addHeader("Accept", "application/json");
            builder.addHeader("x-api-key", "reqres-free-v1");
            builder.addFilter(allureFilter);
            
            requestSpec = builder.build();
            System.out.println("RequestSpec inicializado correctamente");
        } catch (Exception e) {
            System.err.println("Error inicializando requestSpec: " + e.getMessage());
            throw e;
        }
    }
    
    protected RequestSpecification getRequestSpec() {
        if (requestSpec == null) {
            System.out.println("RequestSpec es null, inicializando...");
            initializeRequestSpec();
        }
        return requestSpec;
    }
}