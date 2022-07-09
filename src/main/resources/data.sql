use prodpaymybuddy;

INSERT IGNORE INTO users (username,password,enabled) VALUES ("matappmail@gmail.com","$2y$10$ujB1dszKMoAfo8XxUiyQZu2PRmAIVzHIB9bMh7onNdN.Hf2vzHiOm",true);
INSERT IGNORE INTO users (username,password,enabled) VALUES ("admin","$2y$10$2xknJbWkHatoQgPdao9KbeB03ZqFDEixx375DC1abfUNafMfasHgW",true);
/*INSERT IGNORE INTO users (username,password,enabled) VALUES ("man","$2y$10$sMSWa0oneVaNUhL6lKQhC.XnL.Ih63f3rJxSnPlz6LRGtGRC2GotC",true);
INSERT IGNORE INTO users (username,password,enabled) VALUES ("ywa","$2y$10$RjLHHiJxOJnMCECvOCcmA.92H/oyQ8XFGcDL8FinpkUHM12UnH3nq",true);
INSERT IGNORE INTO users (username,password,enabled) VALUES ("bowl","$2y$10$Ylwc2Vzh5fCVTECcXO2itOMlmSQb4Qwp8uqy6mhMceHYB4UBD7hiu",true);

INSERT IGNORE INTO econnection (fk_payer_username,fk_payee_username) VALUES ("ywa","bowl");
INSERT IGNORE INTO econnection (fk_payer_username,fk_payee_username) VALUES ("bowl","ywa");
*/
INSERT IGNORE INTO authorities (fk_username,authority) VALUES ("matappmail@gmail.com","ROLE_USER");
INSERT IGNORE INTO authorities (fk_username,authority) VALUES ("admin","{ROLE_USER,ROLE_ADMIN}");
/*INSERT IGNORE INTO authorities (fk_username,authority) VALUES ("man","ROLE_USER");
INSERT IGNORE INTO authorities (fk_username,authority) VALUES ("ywa","ROLE_USER");
INSERT IGNORE INTO authorities (fk_username,authority) VALUES ("bowl","ROLE_USER");
*/