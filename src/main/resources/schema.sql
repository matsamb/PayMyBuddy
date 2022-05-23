/* DROP database IF EXISTS prodPaymybuddy;
DROP database IF EXISTS testPaymybuddy;

CREATE DATABASE IF NOT EXISTS prodPaymybuddy;
CREATE DATABASE IF NOT EXISTS testPaymybuddy;  */

USE testPaymybuddy;

CREATE TABLE IF NOT EXISTS users(
	username VARCHAR (50) NOT NULL,
/*	first_name VARCHAR (50),
	last_name VARCHAR (50),
	balance FLOAT,*/
	password VARCHAR (64) NOT NULL,
	enabled BOOLEAN NOT NULL,
	PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS authorities(
	fk_username VARCHAR (50) NOT NULL,
	authority VARCHAR (50) NOT NULL, 
	
	CONSTRAINT fk_authorities_users FOREIGN KEY (fk_username) REFERENCES users (username)
		
);
/*
CREATE UNIQUE INDEX ix_auth_fkusername ON authorities (fk_username, authority);
*/
CREATE TABLE IF NOT EXISTS econnection(
	id INTEGER NOT NULL AUTO_INCREMENT,
	fk_payer_username VARCHAR (50) NOT NULL,
	fk_payee_username VARCHAR (50) NOT NULL,
	
	PRIMARY KEY (id),
	/*INDEX utilisateur_connection_fk, 
	INDEX utilisateur_connection_fk, */
	
	CONSTRAINT fk_econnection_payers FOREIGN KEY (fk_payer_username) REFERENCES users (username) ,
	CONSTRAINT fk_econnection_payees FOREIGN KEY (fk_payee_username) REFERENCES users (username) 
);



CREATE TABLE IF NOT EXISTS payment(
	id INTEGER NOT NULL AUTO_INCREMENT,
	fk_econnection_id INTEGER NOT NULL,
	amount FLOAT,
	payment_date DATE,
	PRIMARY KEY (id),
	/*INDEX connection_payment_fk, */
	CONSTRAINT fk_payment_econnection FOREIGN KEY (fk_econnection_id) REFERENCES econnection (id)
);

CREATE TABLE IF NOT EXISTS bank_account(
	iban INTEGER NOT NULL AUTO_INCREMENT,
	fk_users_username VARCHAR (50) NOT NULL,
	balance FLOAT,
	PRIMARY KEY (iban),
	/*INDEX utilisateur_bank_account_fk,*/
	CONSTRAINT fk_bank_users FOREIGN KEY (fk_users_username) REFERENCES users (username)
);

CREATE TABLE IF NOT EXISTS transaction(
	id INTEGER NOT NULL AUTO_INCREMENT,
	fk_iban INTEGER NOT NULL,
	amount FLOAT,
	transaction_date DATE,
	from_bank BOOLEAN,
	PRIMARY KEY (id),
	/*INDEX bank_account_transaction_fk,*/
	CONSTRAINT fk_transaction_bank FOREIGN KEY (fk_iban) REFERENCES bank_account (iban)
);

CREATE TABLE IF NOT EXISTS persistent_logins(
	
	username VARCHAR (64) NOT NULL,
	series VARCHAR (64),
	token VARCHAR (64) NOT NULL,
	last_used TIMESTAMP NOT NULL,
	PRIMARY KEY (series)

);