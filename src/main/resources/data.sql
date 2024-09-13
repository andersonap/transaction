DROP TABLE IF EXISTS balance;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS merchant_dictionary;

CREATE TABLE account (id int not null auto_increment, PRIMARY KEY (id));

CREATE TABLE balance (id int not null auto_increment,
                      account_id int not null,
                      category varchar(50) not null,
                      amount decimal(50,2) not null,
                      PRIMARY KEY (id));

CREATE TABLE transaction (id int not null auto_increment,
                          amount decimal(50,2) not null,
                          account_id int not null,
                          mcc varchar(50) not null,
                          merchant varchar(250) not null,
                          PRIMARY KEY (id));

CREATE TABLE merchant_dictionary (id int not null auto_increment,
                                  word varchar(250) not null,
                                  category varchar(250) not null,
                                  PRIMARY KEY (id));

INSERT INTO account (id) VALUES (1);

INSERT INTO balance (id, account_id, category, amount) VALUES (1, 1, 'FOOD', 1000.00);
INSERT INTO balance (id, account_id, category, amount) VALUES (2, 1, 'MEAL', 1000.00);
INSERT INTO balance (id, account_id, category, amount) VALUES (3, 1, 'CASH', 1000.00);

INSERT INTO merchant_dictionary(word, category) VALUES ('TRIP', 'MOBILITY');
INSERT INTO merchant_dictionary(word, category) VALUES ('BILHETE', 'MOBILITY');
INSERT INTO merchant_dictionary(word, category) VALUES ('EATS', 'MEAL');
INSERT INTO merchant_dictionary(word, category) VALUES ('RESTAURANTE', 'MEAL');
INSERT INTO merchant_dictionary(word, category) VALUES ('BUFFET', 'MEAL');
INSERT INTO merchant_dictionary(word, category) VALUES ('MERCADO', 'FOOD');
INSERT INTO merchant_dictionary(word, category) VALUES ('PADARIA', 'FOOD');
INSERT INTO merchant_dictionary(word, category) VALUES ('FEIRA', 'FOOD');