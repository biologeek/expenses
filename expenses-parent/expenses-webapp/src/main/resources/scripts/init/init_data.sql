INSERT INTO person VALUES (1, 'Xavier', 27, 'Xavier', 'Caron', 'bob57050@hotmail.fr', null);


INSERT INTO organization VALUES (2, 'Match', true, null, 3);
INSERT INTO organization VALUES (3, 'BBG', true, null, 2);


INSERT INTO category VALUES (1, "", 0, 'Alimentation', '001', null);
INSERT INTO category VALUES (2, null, 1, 'Alimentation Biologique', '001-001', 1);
INSERT INTO category VALUES (3, 'food.png', 1, 'Alimentation Générale', '001-002', 1);
INSERT INTO category VALUES (4, 'midday-meal.png', 2, 'Repas midi', '001-002-001', 3);
INSERT INTO category VALUES (5, 'drinks.png', 1, 'Boissons', '001-003', 1);

INSERT INTO category VALUES (6, null, 0, 'Loisirs', '002', null);
INSERT INTO category VALUES (7, 'brewery.png', 1, 'Brasserie', '002-001', 6);
INSERT INTO category VALUES (8, 'bike.png', 1, 'Cyclisme', '002-002', 6);
INSERT INTO category VALUES (9, 'culture.png', 1, 'Culture', '001-003', 6);
INSERT INTO category VALUES (10, null, 1, 'Bricolage', '001-004', 6);

INSERT INTO category VALUES (11, null, 0, 'Transports', '003', null);
INSERT INTO category VALUES (12, null, 1, 'Transports en commun', '003-001', 11);
INSERT INTO category VALUES (13, 'train.png', 2, 'Train', '003-001-001', 12);
INSERT INTO category VALUES (14, 'car-share.png', 2, 'Covoiturage', '003-001-002', 12);
INSERT INTO category VALUES (15, 'car.png', 1, 'Voiture', '003-002', 11);
INSERT INTO category VALUES (16, 'car-oil.png', 2, 'Essence', '003-002-001', 15);
INSERT INTO category VALUES (17, 'car-toll.png', 2, 'Péage', '003-002-002', 15);
INSERT INTO category VALUES (18, 'car-maintainance.png', 2, 'Entretien', '003-002-003', 15);
