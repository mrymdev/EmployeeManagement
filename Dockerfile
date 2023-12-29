FROM openjdk:17

WORKDIR /app

RUN chmod 777 /app

ADD /target/EmployeeManagement-*.jar /app/EmployeeManagement.jar

CMD ["java", "-jar", "EmployeeManagement.jar"]