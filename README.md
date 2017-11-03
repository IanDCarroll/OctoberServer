# OctoberServer
A second Java HTTP server implementing https://github.com/8thlight/cob_spec

Check out [the Design Decisions](https://github.com/IanDCarroll/OctoberServer/wiki/Design-Decisions) for an overview on how this server is constructed

### Setup:

`$ mvn clean compile assembly:single`

### Run Server:
`$ java -jar target/OctoberServer.jar [-p PORT] [-d DIRECTORY_TO_SERVE]`

### Run Tests:

`$ mvn test`


