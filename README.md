# OctoberServer
A second Java HTTP server implementing https://github.com/8thlight/cob_spec

Check out [the Design Decisions](https://github.com/IanDCarroll/OctoberServer/wiki/Design-Decisions) for an overview on how this server is constructed

### Dependencies:

- Java 8
- SnakeYaml (for routing; set in the maven build)

### Setup:

`$ mvn clean compile assembly:single`

### Run Server:
`$ java -jar target/OctoberServer.jar [-p PORT] [-d /PATH/TO/DIRECTORY/TO/SERVE/FROM] [-c /PATH/TO/SPECIAL/ROUTES/CONFIG.yml]`<br>
When configuring this with cob_spec, be sure to specify `-c` or it won't run.<br>
Cob_spec runs your server from the home directory, not the project's root.<br>
This will break a server that relies on files and expects to run in the project's root.

### Run Unit Tests:

`$ mvn test`

