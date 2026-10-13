[![CI/CD Pipeline](https://github.com/FSPOISON/NOVABANK/actions/workflows/build.yml/badge.svg)](https://github.com/FSPOISON/NOVABANK/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=FSPOISON_novabank&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=FSPOISON_novabank)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=FSPOISON_novabank&metric=bugs)](https://sonarcloud.io/summary/new_code?id=FSPOISON_novabank)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=FSPOISON_novabank&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=FSPOISON_novabank)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=FSPOISON_novabank&metric=coverage)](https://sonarcloud.io/summary/new_code?id=FSPOISON_novabank)

# NovaBank Application

Implementation of a Simple Spring Boot App for NovaBank including integration with GitHub Actions and SonarCloud.

### Folders Structure
* **`novabank/`**: Spring Boot backend application (Java 17, Maven).
* **`novabank-frontend/`**: Frontend application.
* **`.github/workflows/`**: CI/CD Pipeline definitions.

### How to install and run locally
shell
cd novabank
./mvnw spring-boot:run

### How to test

shell
cd novabank
./mvnw clean test
