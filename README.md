# OctoberServer [![Build Status](https://travis-ci.org/IanDCarroll/OctoberServer.svg?branch=master)](https://travis-ci.org/IanDCarroll/OctoberServer)
A Reactive Java HTTP server implementing https://github.com/8thlight/cob_spec


Check out [the Design Decisions](https://github.com/IanDCarroll/OctoberServer/wiki/Design-Decisions) for an overview on how this server is constructed

### Dependencies:

- Java 8
- SnakeYaml (for routing; set in the maven build)

### Setup:

clone this repo.<br>
`$ cd LOCAL/REPO/ROOT`<br>
`$ mvn clean compile assembly:single`

### Run Server:
`$ java -jar target/OctoberServer.jar [-p PORT] [-d /PATH/TO/DIRECTORY/TO/SERVE/FROM] [-c /PATH/TO/SPECIAL/ROUTES/CONFIG.yml]`<br>
When configuring this with cob_spec, be sure to specify `-c` or it won't run.<br>
Cob_spec runs your server from the home directory, not the project's root.<br>
This will break a server that relies on files and expects to run in the project's root.

### Run Unit Tests:

`$ mvn test`

### Run Cob-Spec Tests:
- __in a terminal:__
- clone [cob-spec](https://github.com/8thlight/cob_spec) and follow setup instructions.
- Run cob-spec on port `9090`<br> 
- __in the browser:__
- Navigate to the [Server Start Edit Page](http://localhost:9090/HttpTestSuite?edit)<br>
in the `SERVER_START_COMMAND {` `java -jar /PATH/TO/OctoberServer/target/OctoberServer.jar -c /PATH/TO/OctoberServer/src/test/java/Mocks/cob_spec_routes_config.yml -d PATH/TO/cob_spec/public` `}`<br>
Note: cob-spec starts the server from the user's home directory, not the repo's root.</br>
Make sure to specify the path to the `.jar` and the `cob_spec_config.yml` from that reference.<br>
- Save the page.<br>
- __in a separate terminal:__
- `$ cd LOCAL/REPO/ROOT`
- `$ mvn clean compile assembly:single`
- `java -jar /PATH/TO/OctoberServer/target/OctoberServer.jar -c /PATH/TO/OctoberServer/src/test/java/Mocks/cob_spec_routes_config.yml -d PATH/TO/cob_spec/public`
- __in the browser:__
- Load [this link](http://localhost:9090/HttpTestSuite?suite) to start the test, or just click the `suite` link at the top of the page.