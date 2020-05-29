## `HackerNews API Client`

### Functional Requirements
- /top-stories — returns the top 10 stories ranked by score in the last 10 minutes (see instructions). Each story will have the title, url, score, time of submission, and the user who submitted it.
- /comments — returns the top 10 parent comments on a given story, sorted by the total number of comments (including child comments) per thread. Each comment will have the comment's text, the user’s HN handle, and their HN age. The HN age of a user is basically how old their Hacker News profile is in years.
- /past-stories — returns all the past top stories that were served previously.

### Non-Functional Requirements
- To prevent overloading the HN APIs — and from getting your server rate-limited and blocked by Firebase — implement appropriate caching so that all clients connecting to your server will see the same cached data for up to 10 minutes.

### System Architecture



### Project Setup
- Set the following values
     - `DB-HOST`
     - `DB-DATABASE`
     - `DB-PORT`
     - `DB-USER`
     - `DB-PASSWORD` 
- In the following files
    - hackernews-api/src/main/resources/application.yml
    - hackernews-db/src/main/resources/application.yml
    - hackernews-db/pom.xml
    - hackernews-service/src/main/resources/application.yml
- Then do `mvn clean install -DskipTests` in parent directory
- After this `cd hackernews-db` and do
    - `mvn flyway:migrate` (Migrations build)
    - `mvn clean install -Psetup` (Dummy data Build)
- Then move to `hackernews-api` and finally run the application using `mvn spring-boot:run`

### Project testing
- Calling the API's in your prefered console
- [![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/6ff56ec596372c23ccd1)
- `http://localhost:8080/api/v1/swagger-ui.html` (Swagger)

    
    