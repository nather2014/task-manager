run:
	./mvnw spring-boot:run

build:
	./mvnw clean package -DskipTests

clean:
	./mvnw clean

rebuild: clean build

jar:
	java -jar target/*.jar