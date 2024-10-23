---

# Request solver

---

### Stack:
- **JDK**: 11
- **Spring Boot(JPA, WEB, Secirity, Test)**
- **Docker**
- **Maven**
- **Hibernate**
- **PostgreSQL**
- **Flyway**
- **Testcontainers**
- **Swagger/OpenAPI**
- **Lombok**

---

### Setup Instructions

1. Clone the repository:
    ```bash
    git clone https://github.com/Alexiusss/request-solver
    cd request-solver
    ```

2. Create a `.env` file in the project root with the following credentials:
   - `POSTGRES_USER`
   - `POSTGRES_PASSWORD`
   - `POSTGRES_DB`
   - `PGDATA_PATH`

3. Start the Docker container:
    ```bash
    docker compose up -d
    ```

4. Build the project using Maven:
    ```bash
    mvn clean install
    ```

5. Copy your `.env` file into the `/target/` folder:
    ```bash
    cp .env ./target/
    ```

6. Run the application:
    ```bash
    java -jar ./target/*.jar
    ```

7. Access the REST API documentation at:
   [Swagger UI - REST API Documentation](http://localhost:8787/swagger-ui/index.html)


9. Credentials for Testing

| Username | Password |
|---|---|
| `admin` | `adminpassword` |
| `operator` | `operatorpassword` |
| `user` | `userpassword` |