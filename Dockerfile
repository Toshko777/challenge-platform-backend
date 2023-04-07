FROM openjdk:17-slim

# not sure if that is needed ?
# RUN addgroup -S spring && adduser -S spring -G spring
# USER spring:spring
# ----------------

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]