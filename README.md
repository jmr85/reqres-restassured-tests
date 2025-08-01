# 🧪 Reqres API Testing con RestAssured

![CI](https://github.com/jmr85/reqres-restassured-tests/actions/workflows/deploy-report.yml/badge.svg)
[![Allure Report](https://img.shields.io/badge/Allure-Report-brightgreen.svg)](https://jmr85.github.io/reqres-restassured-tests/)

Este proyecto contiene pruebas automatizadas para la API pública de [Reqres](https://reqres.in), utilizando las tecnologías **Java**, **RestAssured**, **TestNG**, **Gradle** y **Allure** como generador de reportes.

---

## 🚀 Tecnologías y herramientas

- ✅ [RestAssured](https://rest-assured.io/) – Testeo de APIs REST en Java
- ✅ [TestNG](https://testng.org/) – Framework de testing con soporte para grupos y configuración avanzada
- ✅ [Gradle](https://gradle.org/) – Sistema de construcción
- ✅ [Allure Reports](https://docs.qameta.io/allure/) – Reportes de ejecución visuales
- ✅ [GitHub Actions](https://docs.github.com/en/actions) – Automatización de CI/CD

---

## 📂 Estructura de carpetas

```
src/
└── test/
    ├── java/apitests/
    │   ├── BaseAPITest.java
    │   ├── AuthTest.java
    │   ├── NegativasTest.java
    │   ├── PerformanceTest.java
    │   ├── SchemaValidationTest.java
    │   ├── UserRetrievalTest.java
    │   └── IdempotencyTest.java
    └── resources/
        ├── testng.xml
        └── schemas/  # Esquemas JSON para validación
```

---

## 📌 Funcionalidades implementadas

- 🔐 **Login y Registro** (positivas y negativas)
- 📥 **Recuperación de usuarios por ID**
- 🔄 **Verificación de idempotencia en métodos GET**
- 📊 **Medición de performance**
- 📄 **Validación de schemas JSON**
- 🔀 Agrupación de tests mediante `@Test(groups = "...")` para ejecución selectiva

---

## 🧪 Ejecución de tests local

```bash
./gradlew clean test
```

> Se ejecuta el `testng.xml` definido en `src/test/resources`.

---

## 📊 Generación de reportes Allure

1. Ejecutar los tests:
   ```bash
   ./gradlew clean test
   ```

2. Generar y levantar el reporte localmente:
   ```bash
   allure serve build/allure-results
   ```

---

## ⚙️ GitHub Actions: CI y despliegue de reportes

Este repositorio utiliza GitHub Actions para ejecutar los tests automáticamente en cada push o pull request sobre la rama principal, o en forma programada mediante un job con cron. El workflow:

- Ejecuta `./gradlew clean test`
- Genera el reporte Allure
- Publica automáticamente el reporte en GitHub Pages:  
  📎 [Ver reporte](https://jmr85.github.io/reqres-restassured-tests/)

Archivo del workflow:  
`.github/workflows/deploy-report.yml`

> Además, el job solo publica el reporte si hay tests ejecutados, y puede configurarse para enviar notificaciones por email o Slack si hay fallos.

Nota:
También se ejecuta automáticamente todos los días a las 2:00 AM (UTC) gracias a la configuración de schedule con cron en el workflow.

---

## 🛠️ Configuración adicional

El archivo `BaseAPITest.java` configura automáticamente el `requestSpec` para todos los tests usando `@BeforeMethod`, asegurando una configuración común de headers y URI base. También incluye protección contra `null` con un getter `getRequestSpec()` reutilizable.

---

## ✨ Ejemplo de anotaciones

```java
@Test(groups = {"auth", "positiva"})
@Epic("Reqres Auth API")
@Feature("Login")
@Story("Login exitoso")
@Severity(SeverityLevel.CRITICAL)
@Owner("Juan Martin Ruiz")
@Description("Login exitoso con email y password válidos")
public void loginExitoso() {
    ...
}
```

---

## 🧑‍💻 Autor

**Juan Martín Ruiz**  
📧 [LinkedIn](https://www.linkedin.com/in/juanmartinruiz/)  

---

## 📝 Licencia

Este proyecto está licenciado bajo MIT. Ver [LICENSE](LICENSE) para más información.