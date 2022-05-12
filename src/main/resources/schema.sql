DROP database prodPaymybuddy;

CREATE DATABASE IF NOT EXISTS prodPaymybuddy;

USE prodPaymybuddy;

CREATE TABLE IF NOT EXISTS utilisateur(
	email VARCHAR (50) NOT NULL,
	first_name VARCHAR (50),
	last_name VARCHAR (50),
	balance FLOAT NOT NULL,
	password VARCHAR (50) NOT NULL,
	PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS connection(
	id INTEGER NOT NULL AUTO_INCREMENT,
	fk_payer_email VARCHAR (50) NOT NULL,
	fk_payee_email VARCHAR (50) NOT NULL,
	
	PRIMARY KEY (id),
	/*INDEX utilisateur_connection_fk, 
	INDEX utilisateur_connection_fk, */
	
	FOREIGN KEY (fk_payer_email) REFERENCES utilisateur (email) ,
	FOREIGN KEY (fk_payee_email) REFERENCES utilisateur (email) 
);

CREATE TABLE IF NOT EXISTS payment(
	id INTEGER NOT NULL AUTO_INCREMENT,
	fk_connection_id INTEGER NOT NULL,
	amount FLOAT,
	payment_date DATE,
	PRIMARY KEY (id),
	/*INDEX connection_payment_fk, */
	FOREIGN KEY (fk_connection_id) REFERENCES connection (id)
);

CREATE TABLE IF NOT EXISTS bank_account(
	iban INTEGER NOT NULL AUTO_INCREMENT,
	fk_utilisateur_email VARCHAR (50) NOT NULL,
	balance FLOAT,
	PRIMARY KEY (iban),
	/*INDEX utilisateur_bank_account_fk,*/
	FOREIGN KEY (fk_utilisateur_email) REFERENCES utilisateur (email)
);

CREATE TABLE IF NOT EXISTS transaction(
	id INTEGER NOT NULL AUTO_INCREMENT,
	fk_iban INTEGER NOT NULL,
	amount FLOAT,
	transaction_date DATE,
	from_bank BOOLEAN,
	PRIMARY KEY (id),
	/*INDEX bank_account_transaction_fk,*/
	FOREIGN KEY (fk_iban) REFERENCES bank_account (iban)
);