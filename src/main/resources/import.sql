-- Insertion des rôles
INSERT INTO role (role_name) VALUES ('VOLUNTEER');
INSERT INTO role (role_name) VALUES ('ORGANIZATION');
INSERT INTO role (role_name) VALUES ('ADMIN');

-- Insertion des types de compétences
INSERT INTO skill_types (label) VALUES ('Service à la personne et accompagnement');
INSERT INTO skill_types (label) VALUES ('Conduite et transport');
INSERT INTO skill_types (label) VALUES ('Langues et traduction');
INSERT INTO skill_types (label) VALUES ('Restauration et cuisine');
INSERT INTO skill_types (label) VALUES ('Prévention et sécurité');
INSERT INTO skill_types (label) VALUES ('Assistance médicale et soins');
INSERT INTO skill_types (label) VALUES ('Communication et marketing');
INSERT INTO skill_types (label) VALUES ('Informatique et numérique');
INSERT INTO skill_types (label) VALUES ('Comptabilité et gestion');
INSERT INTO skill_types (label) VALUES ('Juridique et administratif');
INSERT INTO skill_types (label) VALUES ('Éducation et formation');
INSERT INTO skill_types (label) VALUES ('Animation et loisirs');
INSERT INTO skill_types (label) VALUES ('Arts et créativité');
INSERT INTO skill_types (label) VALUES ('Bricolage et maintenance');
INSERT INTO skill_types (label) VALUES ('Événementiel et logistique');
INSERT INTO skill_types (label) VALUES ('Collecte de fonds');
INSERT INTO skill_types (label) VALUES ('Environnement et jardinage');
INSERT INTO skill_types (label) VALUES ('Sport et bien-être');
INSERT INTO skill_types (label) VALUES ('Musique et spectacle');
INSERT INTO skill_types (label) VALUES ('Accueil et relations publiques');

-- Insertion des adresses
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('10', 'Rue de la Paix', '75002', 'Paris', 48.866667, 2.333333);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('25', 'Avenue des Champs-Élysées', '75008', 'Paris', 48.873792, 2.295028);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('5', 'Place de la Bastille', '75011', 'Paris', 48.853000, 2.369722);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('5', 'Rue de Versailles', '78000', 'Versailles', 48.853000, 2.369722);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('15', 'Rue du Commerce', '69002', 'Lyon', 45.764043, 4.835659);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('8', 'Boulevard de la République', '13001', 'Marseille', 43.296482, 5.369780);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('12', 'Avenue Jean Jaurès', '31000', 'Toulouse', 43.604652, 1.444209);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('20', 'Rue de la Liberté', '67000', 'Strasbourg', 48.573405, 7.752111);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('7', 'Place Wilson', '35000', 'Rennes', 48.117266, -1.677793);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('3', 'Rue Victor Hugo', '44000', 'Nantes', 47.218371, -1.553621);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('18', 'Avenue de la Gare', '21000', 'Dijon', 47.322047, 5.041480);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('9', 'Rue de l\'Église', '80000', 'Amiens', 49.894067, 2.295753);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('22', 'Boulevard Carnot', '59000', 'Lille', 50.629250, 3.057256);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('14', 'Place de la Mairie', '06000', 'Nice', 43.710173, 7.261953);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('6', 'Rue des Écoles', '38000', 'Grenoble', 45.188529, 5.724524);
INSERT INTO address (street_number, street_name, postal_code, city, latitude, longitude) VALUES ('11', 'Avenue du Général de Gaulle', '51100', 'Reims', 49.258329, 4.031696);

-- Insertion des utilisateurs
INSERT INTO user (email, password, registration_date, name, phone_number, role_id) VALUES ('volunteer1@example.com', '$2y$10$8kribT6h0w4qOWnb.QTjee.EdsiHuwZWTcJGtkUZXbnPbIE7Bqhwa', CURRENT_DATE, 'Alice Dubois', '0612345678', 1);
INSERT INTO user (email, password, registration_date, name, phone_number, role_id) VALUES ('volunteer2@example.com', '$2y$10$frros9R/i7hxa3VKc9uTfeDZIWNyxlSEJ0j15TawII6O6ccauWd4W', CURRENT_DATE, 'Bob Martin', '0798765432', 1);
INSERT INTO user (email, password, registration_date, name, phone_number, role_id) VALUES ('association1@example.com', '$2y$10$sBw5njBg2QvlF/XYkQMxb.qjqi2RNIpb1Y1yzYaEMuFpKuzX3aly6', CURRENT_DATE, 'Les Amis de la Nature', '0123456789', 2);
INSERT INTO user (email, password, registration_date, name, phone_number, role_id) VALUES ('entraide@example.org', '$2y$10$Cg7lXr2Oxh2ByISkYmP6U.1Q04NoTdZJLmJbzwUiRGa87hf3dijee', CURRENT_DATE, 'Solidarité Sans Frontières', '0987654321', 2);

-- Insertion des bénévoles
INSERT INTO volunteer (user_id, birthdate) VALUES (1, '2000-05-15');
INSERT INTO volunteer (user_id, birthdate) VALUES (2, '1998-11-20');

-- Insertion des organisations
INSERT INTO organization (user_id, rna) VALUES (3, 'W1234567890');
INSERT INTO organization (user_id, rna) VALUES (4, 'W9876543210');

-- Association utilisateurs-adresses
INSERT INTO user_address (user_id, address_id) VALUES (1, 1);
INSERT INTO user_address (user_id, address_id) VALUES (2, 2);
INSERT INTO user_address (user_id, address_id) VALUES (3, 3);
INSERT INTO user_address (user_id, address_id) VALUES (4, 4);

-- Insertion des périodes
INSERT INTO period (start_date, end_date) VALUES ('2025-06-15 09:00:00', '2025-06-15 17:00:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-06-20 14:00:00', '2025-06-20 18:00:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-06-25 08:00:00', '2025-06-25 12:00:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-07-01 10:00:00', '2025-07-01 16:00:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-07-05 13:00:00', '2025-07-05 19:00:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-07-10 09:30:00', '2025-07-10 15:30:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-07-15 08:00:00', '2025-07-15 18:00:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-07-20 14:00:00', '2025-07-20 17:00:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-07-25 10:00:00', '2025-07-25 14:00:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-08-01 09:00:00', '2025-08-01 13:00:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-08-05 15:00:00', '2025-08-05 20:00:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-08-10 08:30:00', '2025-08-10 12:30:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-08-15 11:00:00', '2025-08-15 17:00:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-08-20 09:00:00', '2025-08-20 16:00:00');
INSERT INTO period (start_date, end_date) VALUES ('2025-08-25 14:00:00', '2025-08-25 18:00:00');

-- Insertion des missions
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Distribution alimentaire', 'Aide à la distribution de denrées alimentaires pour les familles en difficulté. Accueil, tri et remise des colis alimentaires.', 8, '2025-06-09', NULL, 3, 5, 1);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Accompagnement personnes âgées', 'Visite et accompagnement de personnes âgées isolées. Discussions, jeux de société, aide aux courses légères.', 4, '2025-06-09', NULL, 4, 6, 2);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Nettoyage parc municipal', 'Opération de nettoyage et d\'embellissement du parc municipal. Ramassage de déchets, plantation de fleurs.', 12, '2025-06-09', NULL, 3, 7, 3);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Atelier informatique seniors', 'Animation d\'ateliers d\'initiation à l\'informatique pour les personnes âgées. Apprentissage des bases internet et email.', 3, '2025-06-09', NULL, 4, 8, 4);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Collecte vêtements hiver', 'Organisation d\'une collecte de vêtements chauds pour les sans-abri. Tri, conditionnement et distribution.', 6, '2025-06-09', NULL, 3, 9, 5);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Soutien scolaire enfants', 'Aide aux devoirs et soutien scolaire pour enfants de primaire en difficulté. Mathématiques et français principalement.', 5, '2025-06-09', NULL, 4, 10, 6);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Festival solidaire', 'Aide à l\'organisation du festival annuel de l\'association. Montage stands, accueil du public, logistique générale.', 15, '2025-06-09', NULL, 3, 11, 7);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Maraude nocturne', 'Participation à la maraude nocturne pour apporter aide et réconfort aux personnes sans domicile. Distribution de repas chauds.', 4, '2025-06-09', NULL, 4, 12, 8);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Atelier cuisine solidaire', 'Animation d\'ateliers de cuisine économique et équilibrée. Apprentissage de recettes simples et nutritives.', 3, '2025-06-09', NULL, 3, 13, 9);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Visite hôpital enfants', 'Visite et animation pour les enfants hospitalisés. Jeux, lectures, activités créatives pour égayer leur séjour.', 6, '2025-06-09', NULL, 4, 14, 10);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Collecte jouets Noël', 'Organisation de la collecte annuelle de jouets pour Noël. Récupération, tri et conditionnement des dons.', 8, '2025-06-09', NULL, 3, 15, 11);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Transport solidaire', 'Accompagnement de personnes à mobilité réduite pour leurs rendez-vous médicaux. Conduite et assistance.', 2, '2025-06-09', NULL, 4, 16, 12);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Rénovation local associatif', 'Aide à la rénovation du local de l\'association. Peinture, petit bricolage, aménagement des espaces.', 10, '2025-06-09', NULL, 3, 5, 13);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Alphabétisation adultes', 'Cours d\'alphabétisation pour adultes migrants. Apprentissage du français écrit et oral, aide administrative.', 4, '2025-06-09', NULL, 4, 6, 14);
INSERT INTO mission (title, description, number_volunteer_search, publication_date, publication_closing_date, user_id, address_id, period_id) VALUES ('Jardinage thérapeutique', 'Animation d\'ateliers de jardinage dans un EHPAD. Activités de plantation et d\'entretien avec les résidents.', 5, '2025-06-09', NULL, 3, 7, 15);

-- Association des compétences aux missions
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (1, 1);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (1, 15);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (2, 1);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (3, 17);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (4, 8);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (4, 11);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (5, 15);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (5, 1);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (6, 11);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (7, 15);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (7, 12);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (8, 1);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (8, 5);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (9, 4);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (9, 11);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (10, 12);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (10, 1);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (11, 15);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (11, 12);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (12, 2);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (13, 14);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (14, 11);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (14, 3);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (15, 17);
INSERT INTO missions_skill_type (mission_id, id_skill_type) VALUES (15, 1);