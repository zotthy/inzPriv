INSERT INTO application_user (first_name, last_name, email, password) VALUES ('Jan', 'Kowalski', 'superadmin@example.com', '{bcrypt}$2a$10$Ruu5GtmSVkfLeuGfz/wHUuzflCcMbwJHSBo/.Wui0EM0KIM52Gs2S');
INSERT INTO application_user (first_name, last_name, email, password) VALUES ('John', 'Abacki', 'john@example.com', '{MD5}{AlZCLSQMMNLBS5mEO0kSem9V3mxplC6cTjWy9Kj/Gxs=}d9007147eb3a5f727b2665d647d36e35');
INSERT INTO application_user (first_name, last_name, email, password) VALUES ('Marek', 'Zalewski', 'java_lover@example.com', '{argon2}$argon2id$v=19$m=4096,t=3,p=1$YBBBwx+kfrNgczYDcLlWYA$LEPgdtfskoobyFtUWTMejaE5SBRyieHYbiE5ZmFKE7I');
INSERT INTO user_role (name, description) VALUES ('ADMIN', 'Ma dostęp do wszystkiego');
INSERT INTO user_role (name, description) VALUES ('USER', 'Dostęp tylko do odczytu');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);