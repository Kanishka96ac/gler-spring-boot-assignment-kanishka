# Screen Test 

This assignment project is built using Spring Boot 3.5.5 and Java 17.
It includes two tasks designed to evaluate your ability to work with REST APIs, Spring Web, Spring Data JPA, H2 Database, and testing frameworks.

### Prerequisites

- Maven 3.x
- Java 17
- Any IDE

# Assignment Tasks
## Task 1: Text Replacement API

- Endpoint: GET /replace
- Controller: TextReplaceController.java

Description: Accepts a query parameter text and:

1. If length < 2: return 400 Bad Request
2. If length = 2: return 200 OK with empty body
3. If length > 2: replace first character with * and last character with $

### Example Input/Output:
- elephant => *lephan$
- home => *om$
- abc#20xyz => *bc#20xy$
- abc => *b$
- TetingCodeAssignmentProject -> *estingCodeAssignmentProjec$
- My Test Project => *y Test Projec$

### Validation Rules:

- Works with letters, numbers, and special characters.
- Handles edge cases for length < 2, = 2, and > 2.
- Tests:
  - Unit tests for:
    - Words of varying lengths
    - Edge cases (0–3 characters)
    - Mixed strings (letters, numbers, symbols)

## Task 2: Forecast API

- Endpoint: POST /forcast 
- Controller: ForcastController.java
- Request Body:
```
    {
        "date": <date to be used to filter data from the hourly time - accepted format should be year-month-date like YYYY-MM-DD>,
        "addTemprature": true/false,
        "addHumidity": true/false,
        "addWindSpeed": true/false
    }
```
- Behavior:
  - Calls external API: https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,wind_speed_10m&hourly=temperature_2m,relative_humidity_2m,wind_speed_10m
- Extracts the following values for the specified date:
  1. Maximum temperature
  2. Maximum humidity
  3. Maximum wind speed
- Stores the extracted values as a single record in the database for the specified date.
  ```
  Ex: 
    "date": "2026-05-04",
    "maxTemperature": 22.7,
    "maxHumidity": 69,
    "maxWindSpeed": 15.4
    ```
- If an input parameter is set to false, the corresponding field is stored as null. For example, if addHumidity is false, maxHumidity will be stored as null.
- Validation Rules:
  1. All request body parameters are mandatory and must be explicitly set to either true or false.
  2. If one or more parameters are missing, the API shall return 400 Bad Request.
  3. If the external API is unreachable, the API shall return 502 Bad Gateway.
  4. For any unexpected error or exception, the API shall return 500 Internal Server Error.
  ```
  Ex:
    {
      "timestamp": "2025-09-11T01:32:01.493523327",
      "status": 502,
      "error": "Upstream API Unreachable",
      "message": "Connection to the upstream is unreachable",
      "path": "/api/v1/forcast"
    }
  ```
- Tests:
  - Add unit tests for each layer (service, repository, and controller).
  - Add integration tests to cover end-to-end flows (particularly for controller endpoints). 
  - Use a WireMock server for integration testing. 
  - Add test cases to cover positive, negative, and edge-case scenarios. 
  - Test coverage should exceed 95%.


## 📌 Notes

- Follow clean code practices (naming conventions, comments where necessary).
- Ensure test coverage for both tasks.
- Database schema will be auto-created via JPA/Hibernate.
- **!IMPORTANT: Please do not create a pull request for this repository. Instead, create your own repository in your private GitHub account and share the repository URL with us**