# Проект нанотеплица
### Требования
* Java 1.8.0
* mongodb 

### Запуск
```sh
java -jar greenhouse-0.0.1-SNAPSHOT.war --spring.profiles.active=prod
```
После запуска графики будут доступны на порту 8080. Сейчас отображаются графики за последние 24 часа.

### Api
```sh
curl -v -X POST -H "Content-Type:application/json" --data '{"name":"DHT22_1","type":"temperature","dateTime":"2015-03-18T11:00:42.389Z","value":"18.0"}' http://192.168.2.108:8080/api/rows
```
* type - может быть temperature или wetness
* url - может быть например http://localhost:8080/api/rows
