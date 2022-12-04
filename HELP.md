# Getting Started
Before launching the project you must install maven, docker and docker compose on your machine.

To launch the project you must execute the following commands:
```
mvn clean package
docker-compose up --build
```

This will create the image and launch the application on port 8080. For monitoring purposes it also launches a prometheus instance on port 9090 and a grafana instance on port 3000
Assuming you have configured docker to launch on localhost, you can check the following urls:

* [Swagger with all the endpoints](http://localhost:8080/swagger-ui/index.html#/)
* [List of endpoints provided by spring actuator](http://localhost:8080/actuator/)
* [Server and embedded status for healthchecks](http://localhost:8080/actuator/health)
* [Endpoint with metrics in a format that can be consumed by the prometheus instance](http://localhost:8080/actuator/prometheus)
* [Prometheus instance url](http://localhost:9090/graph)
* [Grafana instance url](http://localhost:3000/)
* [H2 console to check the database.](http://localhost:8080/h2-console/) This should be used only in develop environments

For the grafana instance there is a provisioned monitoring dashboard based on [this one](https://grafana.com/grafana/dashboards/6756-spring-boot-statistics/).
There is a known bug with the prometheus data source the first time you launch the container (the dashboard doesn't detect the data source).
To solve this problem just edit the data source and click the "Save and test" button and you will be able to see the dashboard correctly.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Installing docker guide](https://docs.docker.com/engine/install/)
* [Installing docker compose](https://docs.docker.com/compose/install/)