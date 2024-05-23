-- Создание таблиц
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT PRIMARY KEY,
                                     first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    hash_password VARCHAR(255),
    phone_number VARCHAR(255),
    role VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS products (
                                        id BIGINT PRIMARY KEY,
                                        name VARCHAR(255),
    price DECIMAL
    );

CREATE TABLE IF NOT EXISTS carts (
                                     id BIGINT PRIMARY KEY,
                                     user_id BIGINT,
                                     FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS cart_products (
                                             id BIGINT PRIMARY KEY,
                                             cart_id BIGINT,
                                             product_id BIGINT,
                                             quantity INT,
                                             FOREIGN KEY (cart_id) REFERENCES carts(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
    );

-- Данные пользователей
INSERT INTO users (id, first_name, last_name, email, hash_password, phone_number, role)
VALUES
    (1, 'Kirill', 'Topolcean', 'user@mail.com', 'Qwerty007!', '+7 952 889 01 88', 'USER'),
    (2, 'Kir', 'Top', 'kim@mail.com', 'Qwerty007!', '+7 952 889 01 00', 'ADMIN');

-- Данные продуктов
INSERT INTO products (id, name, price) VALUES
                                           (1, 'Product1', 10.0),
                                           (2, 'Product2', 20.0);

-- Данные корзин
INSERT INTO carts (id, user_id) VALUES (1, 1);

-- Данные продуктов в корзинах
INSERT INTO cart_products (id, cart_id, product_id, quantity) VALUES (1, 1, 1, 2);