INSERT INTO person VALUES (1, 'Xavier', 27, 'Xavier', 'Caron', 'bob57050@hotmail.fr', null);
INSERT INTO person VALUES (2, 'Marjo', 27, 'Marjolaine', null, null, null);
INSERT INTO person VALUES (3, 'Parents', 60, 'Famille', 'Caron', null, null);
INSERT INTO person VALUES (4, 'Guillaume', 29, 'Guillaume', 'Hocquet', null, null);
INSERT INTO person VALUES (5, 'Bobby', 22, 'Benoit', 'Martin', null, null);
INSERT INTO person VALUES (6, 'Anna', 27, 'Anna', 'Fichet', null, null);
INSERT INTO person VALUES (6, 'Proprio Lille', NULL, 'Ben Youssef', 'Echikr', null, null);


INSERT INTO organization VALUES (2, 'Match', true, null, 3);
INSERT INTO organization VALUES (4, 'Auchan', true, null, 3);
INSERT INTO organization VALUES (5, 'Carrefour', true, null, 3);
INSERT INTO organization VALUES (6, 'Biocoop', true, null, 2);
INSERT INTO organization VALUES (7, 'Autre magasin bio', true, null, 2);
INSERT INTO organization VALUES (8, 'Mie Caline', true, null, 4);
INSERT INTO organization VALUES (3, 'BBG', true, null, 2);
INSERT INTO organization VALUES (9, 'Crédit Mutuel', true, null, 27);
INSERT INTO organization VALUES (10, 'MACIF', true, null, 28);
INSERT INTO organization VALUES (11, 'Spartak Lillois', false, null, 8);

INSERT INTO category VALUES (1, null, 0, 'Alimentation', '001', null);
INSERT INTO category VALUES (2, 'organic-food.png', 1, 'Alimentation Biologique', '001-001', 1);
INSERT INTO category VALUES (3, 'food.png', 1, 'Alimentation Générale', '001-002', 1);
INSERT INTO category VALUES (4, 'midday-meal.png', 2, 'Repas midi', '001-002-001', 3);
INSERT INTO category VALUES (5, 'drinks.png', 1, 'Boissons', '001-003', 1);

INSERT INTO category VALUES (6, null, 0, 'Loisirs', '002', null);
INSERT INTO category VALUES (7, 'brewery.png', 1, 'Brasserie', '002-001', 6);
INSERT INTO category VALUES (8, null, 1, 'Sport', '002-002', 6);
INSERT INTO category VALUES (29, 'bike.png', 2, 'Cyclisme', '002-002-001', 6);
INSERT INTO category VALUES (30, null, 2, 'Muscu', '002-002-002', 6);
INSERT INTO category VALUES (9, 'culture.png', 1, 'Culture', '002-003', 6);
INSERT INTO category VALUES (10, null, 1, 'Bricolage', '002-004', 6);

INSERT INTO category VALUES (11, null, 0, 'Transports', '003', null);
INSERT INTO category VALUES (12, null, 1, 'Transports en commun', '003-001', 11);
INSERT INTO category VALUES (13, 'train.png', 2, 'Train', '003-001-001', 12);
INSERT INTO category VALUES (14, 'car-share.png', 2, 'Covoiturage', '003-001-002', 12);
INSERT INTO category VALUES (15, 'car.png', 1, 'Voiture', '003-002', 11);
INSERT INTO category VALUES (16, 'car-oil.png', 2, 'Essence', '003-002-001', 15);
INSERT INTO category VALUES (17, 'car-toll.png', 2, 'Péage', '003-002-002', 15);
INSERT INTO category VALUES (18, 'car-maintainance.png', 2, 'Entretien', '003-002-003', 15);


INSERT INTO category VALUES (19, null, 0, 'Revenus, impôts et frais financiers', '004', null);
INSERT INTO category VALUES (20, null, 1, 'Salaire', '004-001', 19);
INSERT INTO category VALUES (21, 'salary.png', 2, 'Base', '004-001-001', 20);
INSERT INTO category VALUES (22, 'astreinte.png', 2, 'Astreintes', '004-001-002', 20);
INSERT INTO category VALUES (23, null, 2, 'Avantages', '004-001-003', 20);
INSERT INTO category VALUES (24, null, 1, 'Autres activités', '004-002', 20);
INSERT INTO category VALUES (25, 'tax.png', 1, 'Impôts', '004-003', 19);
INSERT INTO category VALUES (26, null, 1, 'Frais bancaires et assurances', '004-004', 19);
INSERT INTO category VALUES (27, 'bank.png', 2, 'Banque', '004-004-001', 26);
INSERT INTO category VALUES (28, 'insurance.png', 2, 'Assurances', '004-004-002', 26);


