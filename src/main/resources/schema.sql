/*DROP database IF EXISTS prodPaymybuddy;
DROP database IF EXISTS testPaymybuddy;

CREATE DATABASE IF NOT EXISTS prodPaymybuddy;
CREATE DATABASE IF NOT EXISTS testPaymybuddy; */

/*USE testpaymybuddy;
/*

CREATE TABLE IF NOT EXISTS users(
	username VARCHAR (50) NOT NULL,
	password VARCHAR (64) NOT NULL,
	enabled BOOLEAN NOT NULL,
	PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS authorities(
	fk_username VARCHAR (50) NOT NULL,
	authority VARCHAR (50) NOT NULL, 
	
	CONSTRAINT fk_authorities_users FOREIGN KEY (fk_username) REFERENCES users (username)
		
); */
