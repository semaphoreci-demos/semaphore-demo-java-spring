# Semaphore demo CI/CD pipeline using Java Spring

Example Spring Boot application and CI/CD pipeline showing how to run a Java
project on [Semaphore 2.0](https://semaphoreci.com).


##### Features:
 - Simple login screen
 - User registration
 - An endpoint secured with `Basic` authentification layer
 - Persistence layer for storing users in database
 
##### Endpoints:
 - `"/admin/home"` a secured endpoint that returns a webpage in a form of `text/html`, generated with thymeleaf. [AdminController](src/main/java/com/example/springpipelinedemo/controller/AdminController.java)
 - `"/login"` standard spring login endpoint 
 - `"/logout"` rest endpoint, ends user session, redirects to `"/login"` 
 - `"/users/signup"` rest endpoint, adds a new user to the system. [UserController](src/main/java/com/example/springpipelinedemo/controller/UserController.java)

##### Persistence:
 
 Persistence for this project is set up using spring jpa, and utilizes `m2` database,
 which is a runtime database for the ease of testing and continuous integration, however is fully compatible with many 
 standard database technologies like `postgres`.
 
 There is a single database entity [User](src/main/java/com/example/springpipelinedemo/model/User.java)
  and a corresponding repository [UserRepository](src/main/java/com/example/springpipelinedemo/repository/UserRepository.java)

##### Tests:

  Tests are separated into two classpaths (in order to run them as separate tasks): 
   - [src/test](src/test) holds the unit tests
   - [src/it](src/it) holds the integration tests, in this case repository and rest endpoint tests.

--------------------------------

### Semaphore pipeline

Current semaphore pipeline is set up to:
  1. Build the project
  2. Run tests
  3. Build docker image
  4. Push image to `hub.docker.com`

Semaphore pipeline configuration is located at `.semaphore/semaphore.yml`

----------------------------

#### Setting up

To set up this pipeline on your semaphore account :

  1. If you don't have `sem` command line tool installed, do so using `curl https://storage.googleapis.com/sem-cli-releases/get.sh | bash`
     and then connect to your account using `sem connect <your organisation>.semaphoreci.com <your private key>`, you can get the private key from your account dashboard at `semaphoreci.com`.
  2. Add project to semaphore using `sem init`
  3. This pipeline relies on public docker repository to push artifacts of successful builds. Create an account on `https://hub.docker.com/` if you don't have one.
  4. Add your `hub.docker.com` credentials to `./docker-hub-secret.yml`. The credentials should remain private, so don't publish them to your git repository by mistake.
  5. Add your `./docker-hub-secret.yml` credentials to _semaphore_ using `sem create -f docker-hub-secret.yml`
  
  
At this point the pipeline should be functional, and after pushing a new commit to master a build should initiate on _semaphore_

![alt text](assets/pipeline-result.png)

-------------------------------

### Build configuration

This project is set up using `maven`, maven configuration can be found at `pom.xml`

##### Running the project
  `mvn spring-boot:run`

##### Running tests
Tests are separated into two classpaths: `src/test` for unit tests, and `src/it` for integration tests. 

To run unit tests:

  `mvn clean test`
  
To run integration tests

   `mvn clean test -Pintegration-testing`
   
To run performance tests 

   `mvn clean jmeter:jmeter`
  
##### JMeter gui
  `mvn jmeter:gui`
