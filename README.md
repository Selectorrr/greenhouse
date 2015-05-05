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
Добавление графиков
```sh
curl -v -X POST -H "Content-Type:application/json" --data
'{"name":"DHT22_1","type":"temperature","dateTime":"2015-03-18T11:00:42.389Z","value":"18.0"}' http://localhost:8080/api/rows
```
* type - может быть temperature, wetness или wetnessGround

Добавление прогрессбара
```sh
curl -v -X PUT -H "Content-Type:application/json" --data
'{"type":"water","value":10,"max":50}' http://localhost:8080/api/values
```
* type - может быть water


