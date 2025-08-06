package apitests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.ITestResult;

import io.qameta.allure.Allure;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseAPITest {

    private static final Logger log = LoggerFactory.getLogger(BaseAPITest.class);

    protected static final AllureRestAssured allureFilter =
        new AllureRestAssured()
            .setRequestTemplate("my-http-request.ftl")
            .setResponseTemplate("my-http-response.ftl");

    protected RequestSpecification requestSpec;

    @BeforeMethod
    public void setup() {
        log.info("=== SETUP EJECUTÁNDOSE ===");
        try {
            Files.deleteIfExists(Paths.get("logs", "test.log"));
        } catch (IOException e) {
            log.warn("No se pudo limpiar el log de la prueba", e);
        }
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.config = RestAssured.config()
            .logConfig(LogConfig.logConfig()
                .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL));

        initializeRequestSpec();
        log.info("=== SETUP COMPLETADO ===");
    }

    @AfterMethod(alwaysRun = true)
    public void attachLog(ITestResult result) throws IOException {
        Path logPath = Paths.get("logs", "test.log");
        if (Files.exists(logPath) && result.getStatus() == ITestResult.FAILURE) {
            Allure.addAttachment("Ejecución Log4j", "text/plain",
                Files.newInputStream(logPath), "log");
        }
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
            log.debug("RequestSpec inicializado correctamente");
        } catch (Exception e) {
            log.error("Error inicializando requestSpec", e);
            throw e;
        }
    }
    
    protected RequestSpecification getRequestSpec() {
        if (requestSpec == null) {
            log.warn("requestSpec == null, inicializando...");
            initializeRequestSpec();
        }
        return requestSpec;
    }
}