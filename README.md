# OctoberServer
A second Java HTTP server implementing https://github.com/8thlight/cob_spec

Check out [the Design Decisions](https://github.com/IanDCarroll/OctoberServer/wiki/Design-Decisions) for an overview on how this server is constructed

### Dependencies:

- Java 8
- SnakeYaml (for routing; set in the maven build)

### Setup:

`$ cd` (`routes_config` is only accessible if the repo's root is a direct child of home)<br>
`git clone git@github.com:IanDCarroll/OctoberServer.git`<br>
`$ mvn clean compile assembly:single`

### Run Server:
`$ java -jar target/OctoberServer.jar [-p PORT] [-d DIRECTORY_TO_SERVE]`

### Run Unit Tests:

`$ mvn test`

