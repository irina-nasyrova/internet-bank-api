#API для Интернет-банка

Сервис предназначен для работы клиентов банка со своими банковскими счетами.


###API реализует следующий функционал:

* Получение информации о балансе по ID пользователя;
* Снятие заданной суммы с баланса пользователя;
* Пополнение баланса на заданную сумму;
* Получение списка операций за выбранный период;
* Перевод заданной суммы другому пользователю.

### Сборка и запуск приложения
Для сборки данного проекта необходимо в корневой директории выполнить следующую команду:
~~~
mvn clean package
~~~
Для запуска собранного проекта, находясь в директории target, необходимо выполнить команду:
~~~
java -jar internet-bank-api-0.0.1-SNAPSHOT.jar
~~~
По выполнению данной команды приложение будет запущено на порту 8080

Для изменения параметров приложения необходимо изменить значения в файле src/main/resources/application.yml

Для запуска базы данных Postgres используется файл docker/docker-compose.yaml. Находясь в директории docker, необходимо выполнить команду:
~~~
docker-compose up -d
~~~
Для внесения изменения в базу данных используется приложение Liquibase

#### Версии используемых продуктов
1. Java - 11
2. Maven - 3.6.3
3. Docker - 20.10
4. PostgreSQL - 13.3