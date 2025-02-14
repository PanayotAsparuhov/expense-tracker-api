Expense Tracker API

Описание

Expense Tracker API е RESTful услуга, разработена със Spring Boot, която позволява управление на разходи. API-то предоставя възможност за регистрация на потребители, автентикация чрез JWT, както и CRUD операции върху разходи и категории.

Какъв проблем решава този проект?

Много хора изпитват затруднение в проследяването и управлението на своите разходи. Основните проблеми включват:

Липса на организация – хората често не знаят колко пари харчат и къде.

Трудност при анализиране на разходите – без ясна система за управление на приходите и разходите, е трудно да се правят финансови прогнози.

Неавтоматизирано управление – липсата на дигитално решение означава, че потребителите често разчитат на ръчни методи за водене на бюджет.

Този проект решава тези проблеми чрез:

Централизирана система за управление на разходи – всички финансови данни са на едно място.

Категоризиране на разходите – потребителите могат да разделят разходите си на различни категории (напр. „Храна“, „Транспорт“, „Забавления“).

Проследяване на транзакции – CRUD операции за добавяне, редактиране и изтриване на разходи.

Автентикация и сигурност – използване на JWT за удостоверяване на потребителите и защита на личните им данни.

Интеграция с база данни – PostgreSQL гарантира надеждно съхранение на данните, като използва Docker Desktop за лесно управление.

Технологии

Java 17 (Spring Boot)

Gradle/Maven (за управление на зависимостите)

PostgreSQL (като база данни, използвана с Docker Desktop)

JWT (за удостоверяване на потребителите)

Spring Security (за сигурност)

Lombok (за улеснена работа с Java класове)

Postman (за тестове на API заявките)

Инсталация и конфигурация

1. Клониране на репозитория

git clone <репо-линк>
cd expense-tracker-api

2. Настройка на база данни с Docker Desktop

Ако използвате Docker Desktop, стартирайте PostgreSQL контейнер със следната команда:

docker container run --name postgresdb -e POSTGRES_PASSWORD=admin -d -p 5432:5432 postgres

3. Създаване на базата данни

В основната директория на проекта (expense-tracker-api) се намира SQL скрипт expensetracker_db.sql, който съдържа необходимите структури за базата данни.

Ако използвате Docker:

Копирайте SQL скрипта в контейнера:

docker container cp expensetracker_db.sql postgresdb:/

След това влезте в контейнера:

docker container exec -it postgresdb bash

И изпълнете SQL скрипта чрез psql клиента:

psql -U postgres --file expensetracker_db.sql

4. (Опционално) Актуализиране на конфигурацията в application.properties

Ако базата данни е разположена в облачна платформа или сте променили потребителското име и парола, актуализирайте файла src/main/resources/application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/expensetrackerdb
spring.datasource.username=expensetracker
spring.datasource.password=password

5. Стартиране на Spring Boot приложението

Ако използвате Maven:

./mvnw spring-boot:run

Приложението ще стартира на порт 8080, така че всички API заявки ще бъдат достъпни чрез http://localhost:8080.

API Документация

1. Потребители (/api/users)

Регистрация (POST /api/users/register)

Логин (POST /api/users/login)

2. Категории (/api/categories)

Създаване на категория (POST /api/categories)

Преглед на всички категории (GET /api/categories)

Преглед на категория по ID (GET /api/categories/{categoryId})

Обновяване на категория (PUT /api/categories/{categoryId})

Изтриване на категория (DELETE /api/categories/{categoryId})

3. Транзакции (/api/categories/{categoryId}/transactions)

Създаване на транзакция (POST /api/categories/{categoryId}/transactions)

Преглед на всички транзакции в категория (GET /api/categories/{categoryId}/transactions)

Преглед на транзакция по ID (GET /api/categories/{categoryId}/transactions/{transactionId})

Обновяване на транзакция (PUT /api/categories/{categoryId}/transactions/{transactionId})

Изтриване на транзакция (DELETE /api/categories/{categoryId}/transactions/{transactionId})

Postman Колекция

Можеш да импортираш Postman колекцията от postman_collection.json, за да тестваш заявките.
