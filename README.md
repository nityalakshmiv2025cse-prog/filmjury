# FilmJury

Spring Boot 4 + MySQL backend with a dashboard.

## Run
1. Start MySQL. The `filmjury` database is created automatically.
2. Open `src/main/resources/application.properties` and set your MySQL password.
3. In this folder (the one with pom.xml), run:  `.\mvnw spring-boot:run`
4. Open http://localhost:8080/

Demo data is added on the first start when the database is empty.
Set `filmjury.seed-demo-data=false` to turn it off.

## API
- POST/GET /api/entries, GET /api/entries/{id}
- POST/GET /api/judges, POST /api/judges/{id}/assignments
- POST/GET /api/criteria
- POST /api/entries/{id}/scores, GET /api/entries/{id}/average
- GET /api/leaderboard

Rules: one scorecard per judge per entry (409), judge must be assigned (409), score cannot exceed the criterion max (409), invalid input (400), unknown id (404).
