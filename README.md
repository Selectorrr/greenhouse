Проект нанотеплица
==========================
<b>Требования:</b> <br/>
* Java 1.8.0 <br/>
* mongodb <br/>
<br/>
<b>Запуск:</b> <br/>
```
java -jar greenhouse-0.0.1-SNAPSHOT.war --spring.profiles.active=prod
``` <br/>
После запуска графики будут доступны на порту 8080. Сейчас отображаются графики за последние 24 часа.
<br/>
<br/>
<b>Api:</b> <br/>
```
curl -v -X POST -H "Content-Type:application/json" --data
'{"name":"DHT22_1","type":"temperature","dateTime":"2015-03-18T11:00:42.389Z","value":"18.0"}'
```<br/>
<b>type</b> может быть <b>temperature</b>, <b>wetness</b> или <b>wetnessGround</b> <br/>
<b>url</b> может быть например http://localhost:8080/api/rows
