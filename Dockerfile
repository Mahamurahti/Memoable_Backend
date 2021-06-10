FROM openjdk:11

COPY . .

EXPOSE 8090

CMD ["./mvnw", "spring-boot:run"]