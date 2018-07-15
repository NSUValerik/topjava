DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (userId, dateTime, description, calories) VALUES
  (100000, '2017-07-15 09:00:00', 'завтрак', 500),
  (100000, '2017-07-15 14:00:00', 'обед', 1000),
  (100000, '2017-07-15 19:00:00', 'ужин', 1000),
  (100001, '2017-07-15 10:00:00', 'завтрак', 500),
  (100001, '2017-07-15 15:00:00', 'обед', 1000),
  (100001, '2017-07-15 20:00:00', 'ужин', 800),
  (100001, '2017-07-15 23:00:00', 'ночной перекус', 800);