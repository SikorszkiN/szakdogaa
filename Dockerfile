FROM maven:3.8.3-jdk-11

WORKDIR /
COPY . .
RUN mvn clean install -DskipTests

CMD mvn spring-boot:run