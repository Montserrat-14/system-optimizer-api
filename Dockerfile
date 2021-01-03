#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY ADS.owl /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
ENV RESULTSPATH="resources"
ENV RESULTSEXTENSION=".csv"
COPY --from=build /home/app/ADS.owl ADS.owl
COPY --from=build /home/app/target/systemoptimizer-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]