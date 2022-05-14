DROP database IF EXISTS prodPaymybuddy;

CREATE DATABASE IF NOT EXISTS prodPaymybuddy;

USE prodPaymybuddy;

CREATE TABLE IF NOT EXISTS users(
	email VARCHAR (50) NOT NULL,
/*	first_name VARCHAR (50),
	last_name VARCHAR (50),
	balance FLOAT,*/
	password VARCHAR (64) NOT NULL,
	enabled BOOLEAN NOT NULL,
	PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS authorities(
	fk_email VARCHAR (50) NOT NULL,
	authority VARCHAR (50) NOT NULL, 
	
	CONSTRAINT fk_authorities_users FOREIGN KEY (fk_email) REFERENCES users (email)
		
);

CREATE UNIQUE INDEX ix_auth_fkemail ON authorities (fk_email, authority);

CREATE TABLE IF NOT EXISTS connection(
	id INTEGER NOT NULL AUTO_INCREMENT,
	fk_payer_email VARCHAR (50) NOT NULL,
	fk_payee_email VARCHAR (50) NOT NULL,
	
	PRIMARY KEY (id),
	/*INDEX utilisateur_connection_fk, 
	INDEX utilisateur_connection_fk, */
	
	CONSTRAINT fk_connection_payers FOREIGN KEY (fk_payer_email) REFERENCES users (email) ,
	CONSTRAINT fk_connection_payees FOREIGN KEY (fk_payee_email) REFERENCES users (email) 
);

CREATE TABLE IF NOT EXISTS payment(
	id INTEGER NOT NULL AUTO_INCREMENT,
	fk_connection_id INTEGER NOT NULL,
	amount FLOAT,
	payment_date DATE,
	PRIMARY KEY (id),
	/*INDEX connection_payment_fk, */
	CONSTRAINT fk_payment_connection FOREIGN KEY (fk_connection_id) REFERENCES connection (id)
);

CREATE TABLE IF NOT EXISTS bank_account(
	iban INTEGER NOT NULL AUTO_INCREMENT,
	fk_users_email VARCHAR (50) NOT NULL,
	balance FLOAT,
	PRIMARY KEY (iban),
	/*INDEX utilisateur_bank_account_fk,*/
	CONSTRAINT fk_bank_users FOREIGN KEY (fk_users_email) REFERENCES users (email)
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