
-- БАЗА ДАНИХ
-- Можливість створення БД з метою уникнення некваліфікованих
-- дій, краще залишити за розробником.
-- Тому такий функціонал у додатку не прописуємо.
-- Можемо створити БД через візуальний інструмент, наприклад,
-- MySQL Workbench.
CREATE DATABASE demo_db;

-- ТАБЛИЦІ
-- Можливість створення таблиць БД, з метою уникнення некваліфікованих
-- дій, краще залишити за розробником.
-- Тому такий функціонал у додатку не прописуємо.
-- Попередньо, необхідно спроектувати таблиці та їх зв'язки,
-- на основі сутностей реального світу.
-- Можемо створити таблиці БД через візуальний інструмент, наприклад,
-- MySQL Workbench.

CREATE TABLE IF NOT EXISTS contacts
( id INTEGER NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(128) NOT NULL,
  last_name VARCHAR(128) NOT NULL,
  phone VARCHAR(128) NOT NULL,
  PRIMARY KEY (id)
);

Отримання всіх даних
GET
http://localhost:8081/api/v1/contacts
FROM Contact

Створення даних
POST
http://localhost:8081/api/v1/contacts
SQL: "INSERT INTO Contact (firstName, lastName, phone) VALUES (:firstName, :lastName, :phone)"

Отримання даних за id
GET
http://localhost:8081/api/v1/contacts/2
If id does not exist
http://localhost:8081/api/v1/contacts/9
SQL: "FROM Contact WHERE id = :id"


Оновлення даних за id
PUT
http://localhost:8081/api/v1/contacts/2
If id does not exist
http://localhost:8081/api/v1/contacts/9
SQL: "UPDATE Contact SET firstName = :firstName, lastName = :lastName, phone = :phone WHERE id = :id"

Видалення даних за id
DELETE
http://localhost:8081/api/v1/contacts/5
If id does not exist
http://localhost:8081/api/v1/contacts/9
SQL: "DELETE FROM Contact WHERE id = :id"

Пошук за іменем або прізвищем
GET
http://localhost:8081/api/v1/contacts/query-by-firstname?firstName=Bart
http://localhost:8081/api/v1/contacts/query-by-lastname?lastName=Simpson
SQL: "FROM Contact WHERE firstName = :firstName"
SQL: "FROM Contact WHERE lastName = :lastName"

Групування за іменем або прізвищем у алфавітному порядку
GET
http://localhost:8081/api/v1/contacts/query-order-by?orderBy=firstName
http://localhost:8081/api/v1/contacts/query-order-by?orderBy=lastName
SQL: "FROM Contact ORDER BY firstName"
SQL: "FROM Contact ORDER BY lastName"

Пошук за прізвищем, групування за іншим полем
GET
http://localhost:8081/api/v1/contacts/query-by-lastname-order-by?lastName=Bright&orderBy=firstName
SQL (example): "FROM Contact WHERE lastName = :lastName ORDER BY firstName"

Пошук за id
GET
http://localhost:8081/api/v1/contacts/query-between-ids?from=1&to=7
SQL: "FROM Contact c WHERE c.id BETWEEN :from AND :to"

Пошук за декількома прізвищами
GET
http://localhost:8081/api/v1/contacts/query-lastname-in?lastName1=Lebowski&lastName2=Al-Arabi
SQL: "FROM Contact c WHERE c.lastName IN (:lastName1, :lastName2)"




