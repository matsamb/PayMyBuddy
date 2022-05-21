INSERT IGNORE INTO users (username,password,enabled) VALUES ("user","$2y$10$ujB1dszKMoAfo8XxUiyQZu2PRmAIVzHIB9bMh7onNdN.Hf2vzHiOm",true);
INSERT IGNORE INTO users (username,password,enabled) VALUES ("admin","$2y$10$2xknJbWkHatoQgPdao9KbeB03ZqFDEixx375DC1abfUNafMfasHgW",true);

INSERT IGNORE INTO authorities (fk_username,authority) VALUES ("user","ROLE_USER");
INSERT IGNORE INTO authorities (fk_username,authority) VALUES ("admin","ROLE_ADMIN");