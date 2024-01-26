# Github API

Github API is used to hold/gather related information of github repositories.
It holds values of repository id, languages used in the repo, visibility, repository ownership details.

## Installation

Use Maven to build the application

```bash
mvn clean install
```
Upon successfully packaging the app, Use the command to start the application

```bash
mvn spring-boot:run
```
Alternatively We can use Docker to set up and run the application

```bash
docker build -t image:0.0.1 .
```
Create docker container from the image created

```bash
docker run -d -it --name testcontainer image:0.0.1
```

* **Health** :
  Endpoint used to determine whether the service is Up or Not

  [http://localhost:8080/githubapi/actuator/health](http://localhost:8080/githubapi/actuator/health)

* **Swagger**:
  Endpoint used to determine the API endpoint and the response and error models
  You can execute the application from within the swagger endpoint by clicking on "Try It Out".

  [http://localhost:8080/githubapi/swagger-ui.html](http://localhost:8080/githubapi/swagger-ui.html)

* **Metrics**:
  Endpoint used to determine the metrics of the application and the environment statistics assosiated with it.

  [http://localhost:8080/githubapi/actuator/metrics](http://localhost:8080/githubapi/actuator/metrics)

* For Specific metrics, like http.server.requests, append the same to the url above.

* API Endpoint:
  The API endpoint can be found in Swagger documentation.
  There is two endpoints as it is still in development phase. But feel free to try it out.
1. [http://localhost:8080/githubapi/v1/search/repositories?query=created:2019-12-10,language:java&sort=stars&order=desc&perPage=5&page=1](http://localhost:8080/v1/search/repositories?query=created:2019-12-10,language:java&sort=stars&order=desc&perPage=5&page=1)
  The accepted values of the query params q, sort and order query keys are documented in swagger. However, for readability purposes those are defined here.
   - q : created(date format of yyyy-MM-dd), updated(date format of yyyy-MM-dd), language(string format).
   - sort : stars, forks, help-wanted-issues, updated.
   - order : asc, desc

   The following values can be configured in the application.yaml file located at project root under the app tag.
## Support & Ownership

Feel free to ask [Sijumon Karyil Raju](sijuthomas1988@gmail.com) if you need some support when there are any questions left or if you need some support.