# Market

> A Spring Boot application for managing products,price snapshots,shopping carts and price alets packaged with Docker for easy deployment. Includes Swagger UI for exploring the API.

---

## Quick Start (Docker)

Make sure you have it on your machine:

Java JDK 17+

Apache Maven

Docker

Docker Compose


### 1. Clone the repo

```bash
git clone https://github.com/ArdaiArtur/market_test.git
cd market_test
```

### 2. Build the project
```bash
mvn clean package
```

### 3. Build and run the containers
```bash
docker-compose up --build
```


## API Documentation

After you run the comand you can acces the documentation at:

http://localhost:8080/swagger-ui.html


## Running Tests

```bash
mvn test
```


## Database Managment

This project includes phpMyAdmin,you can access phpMyAdmin at:

http://localhost:8082

Login credentials:

Server: mysql

Username: root

Password: rootpass



## Database Seeding
The application supports automatic seeding of sample data using the SeederRunner class.

To seed the database, run the application with the appropriate --seed argument:

```bash
mvn clean spring-boot:run -Dspring-boot.run.arguments=seed-promotions
```
You can also choose to run specific seeders:

--seed-promotions	Generate promotions data
--seed-snapshots	Generate price history snapshots
--seed-promotionapplys	Apply promotions (discounts) to price snapshots

When using --seed-promotions,--seed-promotionapplys make sure to manually specify the promotion dates in PromotionSeeder and PromotionApplySeeder.
