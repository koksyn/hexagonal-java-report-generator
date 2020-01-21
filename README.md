# Hexagonal Java Report Generator

## Introduction

Recruitment task, which is an REST API application written in Java 8, Spring, Hibernate. 

It:
- has Hexagonal architecture (may be used as an example for *educational purposes*)
- uses building blocks from tactical *Domain-Driven Design*

Application also uses:
- in-memory *H2 database*
- *Maven* to handle dependencies
- *OkHttp* library to crawl the *Star Wars API*
- *concurrent* processing

## Documentation

This document is divided into four parts:

1. **Recruitment task description** - the original task, which I received from company
2. **Solution details** - describes how it was implemented and why I made such decisions
3. **Compilation and running** - technical manual to run the application
4. **Testing application manually** - tips & tricks to test the API manually

---

### 1. Recruitment task description

The purpose of this test is to see how you approach a problem and what your solutions look
like. The requirements for this test should be straightforward to grasp. When implementing a
solution please keep things simple but well engineered.

#### > Task Content

Implement an API query and transform this data into report available via REST API. Create a
Java web application that provides ​/**report**​ service handling PUT, GET and DELETE
requests.

- PUT on **/report/{report_id}** - generates report of *report_id* and saves it in database table.
    - If report of given *report_id* does not exist, then new report is created and stored in database. 
      Otherwise, existing report is updated.
    - Successfull PUT request should return 204 and no data.
    - PUT request body JSON - *query criteria*:
        ```lang=json
        {
          “query_criteria_character_phrase”: “CHARACTER_PHRASE”,
          “query_criteria_planet_name”: “PLANET_NAME”
        }
        ```
- DELETE
    - on **/report/{report_id}** - deletes report of *report_id* from database.
    - on **/report** - deletes all reports from database
- GET
    - on **/report** returns all report data as JSON:
        ```lang=json
        [{
            “report_id”: “{report_id}",
            “query_criteria_character_phrase”: “CHARACTER_PHRASE”,
            “query_criteria_planet_name”: “PLANET_NAME”,
            [{
                “film_id”: “FILM_ID”,
                “film_name”: “FILM_NAME”,
                “character_id”: “CHARACTER_ID”,
                “character_name”: “CHARACTER_NAME”,
                “planet_id”: “PLANET_ID”,
                “planet_name”: “PLANET_NAME”
            },...]
        },...]
        ```
    - on **/report/{report_id}** returns *report_id* data as JSON:
        ```lang=json
        {
            “report_id”: “{report_id}",
            “query_criteria_character_phrase”: “CHARACTER_PHRASE”,
            “query_criteria_planet_name”: “PLANET_NAME”,
            [{
                “film_id”: “FILM_ID”,
                “film_name”: “FILM_NAME”,
                “character_id”: “CHARACTER_ID”,
                “character_name”: “CHARACTER_NAME”,
                “planet_id”: “PLANET_ID”,
                “planet_name”: “PLANET_NAME”
            },...]
        }
        ```

#### > How report is generated?

The application takes ​*query criteria* and queries following services:
- https://swapi.co/api/films/
- https://swapi.co/api/people/
- https://swapi.co/api/planets/

to obtain list of films in which appeared characters who contains given *CHARACTER_PHRASE*
in their name and whose homeworld planet is *PLANET_NAME*.

The application queries API with user input and stores transformed result in database.

#### > Technical requirements

1. Java 8 or higher.
2. Maven or Gradle for building application.
3. You may use any java library eg.: guava.
4. Hibernate with in memory database.
5. Spring, eg.: DI

#### > Verification criteria

1. Does it run.
2. Unit tests run in building cycle.
3. Error handling.
4. Validity and esthetic of querying the data.
5. Validity and esthetic of writing REST API.
6. Performance.
7. Application of software design patterns.
8. Application of Clean Code SOLID principles.

Send us a fat *.war with all dependencies. Share your source code via GitHub.

---

### 2. Solution details

#### > Application architecture

Because it's needed to fetch the data from multiple places (Star Wars API endpoints) and then to process them into different format,
it can be said that this is a **complicated** problem (based on **Cynefin** model announced by *Dave Snowden* in 1999).

Recognizing it helps to understand a problem from strategical *Domain-Driven Design* point of view.

Those *drivers* (factors) convinced me to implement an **Hexagonal architecture** (so-called *Ports-And-Adapters*, *Onion*, *Clean* etc.).

I think this is a good choice when it comes to ensuring **business domain** consistency, independence and testability.

#### Cache for data from Star Wars API

#### > Changes in the API endpoint

There was no guidance in the task description whether to generate a report **synchronously** or **asynchronously**.

I decided to make it **asynchronously**. Of course this leads to the **eventual consistency**.
That's why I've changed the HTTP response code of the endpoint: 

PUT **/report/{report_id}**

from *204 (No content)* to the *202 (Accepted)*.

This way I assure the user of our API that the request to create a report 
has been successfully accepted, but may not yet be processed (and visible).

This has a good performance side effect, where the user does not have to wait long for the response of PUT HTTP request.

#### > Cache inside database

Instead of crawling the Star Wars API every time, when someone requests a report I made a cache for this data.
The result of "*create/update report*" HTTP request is a Report entity marked as **incomplete**.

**Incomplete** report means that it isn't visible yet for the API users, but it will be processed soon.

By using a **Spring Scheduler** the application is looking for incomplete reports every 5 seconds (period could be changed).

Let's assume for instance that in between these 5 seconds an 1000 users requested to create an 1000 reports.

Then the application will start to crawl data from Star Wars API and then it will be saved inside our database.
Of course, every time it starts this process (only when there are some new incomplete reports), the cache is cleaned before crawling.

The next step will be to create 1000 reports from data stored in our database.

It is worth mentioning that downloading data from Star Wars API is done **only once** instead of doing it 1000 times 
(like in a **synchronously** way without cache).

#### > Concurrency

Crawling data for future processing (from Star Wars API) uses 3 independent endpoints.

Because there are I/O operations that take some time (ca. 1 minute) I decided to divide them into three **Tasks**,
whose are executed simultaneously inside a Thread Pool (for planets, characters and films).
 
Thanks that it's faster in terms of performance.

#### > Database (DDL)

I added **single-field** and **composite indexes** to improve the performance of database queries made by application.  

#### > Minor changes inside JSON response

Because there was an error in JSON inside task description (invalid format, where the film list field had no name)
I added the name **"results"** to the Report object, which describes the list of films.

JSON before (from description):

![Invalid JSON](docs/incorrect_json_format.png?raw=true "Invalid JSON")

JSON after:

![Correct JSON proposal](docs/correct_json_proposal.png?raw=true "Correct JSON proposal")

---

### 3. Compilation and running

#### > Environment requirements

You should have installed:
- maven
- Java JDK 1.8

#### > Building cycle with unit tests.

Please open terminal/console inside **Project directory** and execute:

```lang=bash
mvn clean install
```

#### Running API

#### > From compiled sources

After successful building, you should be able to run the application (API).

By **-pl** option you are choosing the module, which consist of all other application modules.
This module (called "**configuration**") is a starting point.

```lang=bash
mvn spring-boot:run -pl configuration
```

The API should be accessible under: http://localhost:8081/api/

Please read the **4.** (point) description below to learn how to test endpoints.

#### > From *.war file

Example:

```lang=bash
java -jar "report.configuration-0.0.1-SNAPSHOT.war"
```

---

### 4. Testing application manually

#### > Swagger 2

**Swagger 2** is attached and enabled in this project. 

It's an additional API endpoint, which scans the whole project and collect all information about REST API endpoints (URLs, models, parameters, responses, etc.).
These data will be returned as JSON.

**Swagger endpoint URL**: http://localhost:8081/api/v2/api-docs

#### > User interface to test the API manually

You can use plugins/add-ons inside your Web browser to visualize this JSON as HTML (to be human-readable).

URLs to the plugins (you can install them in your browser):
- Google Chrome: https://chrome.google.com/webstore/detail/swagger-ui-console/ljlmonadebogfjabhkppkoohjkjclfai
- Mozilla Firefox: https://addons.mozilla.org/pl/firefox/addon/swagger-ui-ff/

Then you will be able to made some HTTP requests easily and read responses from the API. Thanks to the auto-generated UI.
