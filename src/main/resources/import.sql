INSERT INTO `role` (`role_name`) VALUES ('VOLUNTEER');
INSERT INTO `role` (`role_name`) VALUES ('ORGANIZATION');
INSERT INTO `role` (`role_name`) VALUES ('ADMIN');

INSERT INTO `skill_types` (`label`) VALUES ('Service a la personne');
INSERT INTO `skill_types` (`label`) VALUES ('Conduite');
INSERT INTO `skill_types` (`label`) VALUES ('Langues');

INSERT INTO adress (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('10', 'Rue de la Paix', '75002', 'Paris', 48.866667, 2.333333);
INSERT INTO adress (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('25', 'Avenue des Champs-Élysées', '75008', 'Paris', 48.873792, 2.295028);
INSERT INTO adress (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('5', 'Place de la Bastille', '75011', 'Paris', 48.853000, 2.369722);
INSERT INTO adress (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('5', 'Rue de Versailles', '78000', 'Versailles', 48.853000, 2.369722);


INSERT INTO user (email, password, registration_date, name, phone_number, role_id) VALUES ('volunteer1@example.com', '$2y$10$8kribT6h0w4qOWnb.QTjee.EdsiHuwZWTcJGtkUZXbnPbIE7Bqhwa', CURRENT_DATE, 'Alice Dubois', '0612345678', 1);
INSERT INTO volunteer (user_id, birthdate) VALUES (1, '2000-05-15');
INSERT INTO user_adress (user_id, adress_id) VALUES (1, 1);

INSERT INTO user (email, password, registration_date, name, phone_number, role_id) VALUES ('volunteer2@example.com', '$2y$10$frros9R/i7hxa3VKc9uTfeDZIWNyxlSEJ0j15TawII6O6ccauWd4W', CURRENT_DATE, 'Bob Martin', '0798765432', 1);
INSERT INTO volunteer (user_id, birthdate) VALUES (2, '1998-11-20');
INSERT INTO user_adress (user_id, adress_id) VALUES (2, 2);


INSERT INTO user (email, password, registration_date, name, phone_number, role_id) VALUES ('association1@example.com', '$2y$10$sBw5njBg2QvlF/XYkQMxb.qjqi2RNIpb1Y1yzYaEMuFpKuzX3aly6', CURRENT_DATE, 'Les Amis de la Nature', '0123456789', 2);
INSERT INTO organization (user_id, rna) VALUES (3, 'W1234567890');
INSERT INTO user_adress (user_id, adress_id) VALUES (3, 3);

INSERT INTO user (email, password, registration_date, name, phone_number, role_id) VALUES ('entraide@example.org', '$2y$10$Cg7lXr2Oxh2ByISkYmP6U.1Q04NoTdZJLmJbzwUiRGa87hf3dijee', CURRENT_DATE, 'Solidarité Sans Frontières', '0987654321', 2);
INSERT INTO organization (user_id, rna) VALUES (4, 'W9876543210');
INSERT INTO user_adress (user_id, adress_id) VALUES (4, 4);
