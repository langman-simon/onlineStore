-- =========================================================
-- USERS
-- =========================================================

INSERT INTO users (
    last_name,
    first_name,
    delivery_address,
    email,
    phone,
    login,
    password,
    secondary_phone,
    is_admin
)
VALUES
    (
        'user',
        'user',
        '12 rue de Bruxelles, 1000 Bruxelles',
        'user@user.com',
        '0569368522',
        'user',
        '$2a$10$96j7flItAKcO2OK6nBAfOuhGPMc47VnwnI4ZXalb0Yx7rwOP06xB6',
        NULL,
        FALSE
    ),
    (
        'admin',
        'admin',
        '1 avenue Hyperion, 1000 Bruxelles',
        'admin@admin.com',
        '0523524785',
        'admin',
        '$2a$10$96j7flItAKcO2OK6nBAfOuhGPMc47VnwnI4ZXalb0Yx7rwOP06xB6',
        '0369619732',
        TRUE
    )
ON CONFLICT (login) DO UPDATE
    SET
        last_name = EXCLUDED.last_name,
        first_name = EXCLUDED.first_name,
        delivery_address = EXCLUDED.delivery_address,
        email = EXCLUDED.email,
        phone = EXCLUDED.phone,
        password = EXCLUDED.password,
        secondary_phone = EXCLUDED.secondary_phone,
        is_admin = EXCLUDED.is_admin;

-- =========================================================
-- CATÉGORIES
-- =========================================================

INSERT INTO categories (name, description)
VALUES
    ('Armes de poing', 'Pistolets et armes compactes'),
    ('Fusils', 'Fusils classiques, automatiques et armes longues'),
    ('Pistolets-mitrailleurs', 'Armes automatiques compactes'),
    ('Armes de précision', 'Fusils de précision et armes à longue portée'),
    ('Lanceurs', 'Lance-roquettes et lance-missiles'),
    ('Munitions et explosifs', 'Munitions, grenades et charges explosives'),
    ('Protections', 'Équipements de protection individuelle'),
    ('Équipements maritimes', 'Sous-marins, croiseurs et bâtiments militaires')
ON CONFLICT (name) DO UPDATE SET description = EXCLUDED.description;


-- =========================================================
-- PRODUITS
-- =========================================================

INSERT INTO weapons (
    name,
    description,
    price,
    stock,
    category_id,
    manufacturer,
    reference,
    image_url
)
VALUES
    (
        'Pistolet 1884',
        'Pistolet historique fabriqué selon un modèle datant de 1884.',
        649.99,
        8,
        (SELECT id FROM categories WHERE name = 'Armes de poing'),
        'Hyperion Heritage',
        'HYP-001',
        '/images/1884_pistol.webp'
    ),
    (
        'Fusil à poudre',
        'Fusil traditionnel utilisant une charge de poudre.',
        899.99,
        6,
        (SELECT id FROM categories WHERE name = 'Fusils'),
        'Hyperion Heritage',
        'HYP-002',
        '/images/powder_rifle.webp'
    ),
    (
        'Fusil automatique moderne',
        'Fusil automatique moderne destiné aux opérations terrestres.',
        2199.00,
        12,
        (SELECT id FROM categories WHERE name = 'Fusils'),
        'Hyperion Defence',
        'HYP-003',
        '/images/modern_automatic_rifle.webp'
    ),
    (
        'AK-47',
        'Fusil automatique robuste et polyvalent.',
        1899.00,
        15,
        (SELECT id FROM categories WHERE name = 'Fusils'),
        'Hyperion Defence',
        'HYP-004',
        '/images/ak_47.webp'
    ),
    (
        'Pistolet-mitrailleur',
        'Arme automatique compacte adaptée aux combats rapprochés.',
        1499.00,
        10,
        (SELECT id FROM categories WHERE name = 'Pistolets-mitrailleurs'),
        'Hyperion Tactical',
        'HYP-005',
        '/images/submachinesgun.webp'
    ),
    (
        'Fusil de précision',
        'Fusil de précision conçu pour les tirs à longue distance.',
        3299.00,
        5,
        (SELECT id FROM categories WHERE name = 'Armes de précision'),
        'Hyperion Precision',
        'HYP-006',
        '/images/sniper.jpg'
    ),
    (
        'RPG-47',
        'Lance-roquettes portable destiné aux cibles blindées.',
        4499.00,
        4,
        (SELECT id FROM categories WHERE name = 'Lanceurs'),
        'Hyperion Heavy Weapons',
        'HYP-007',
        '/images/rpg_47.webp'
    ),
    (
        'Lance-missiles',
        'Système de lancement de missiles à longue portée.',
        12500.00,
        3,
        (SELECT id FROM categories WHERE name = 'Lanceurs'),
        'Hyperion Heavy Weapons',
        'HYP-008',
        '/images/missile_launcher.webp'
    ),
    (
        'Munitions AK-47',
        'Caisse de munitions compatible avec le fusil AK-47.',
        199.99,
        50,
        (SELECT id FROM categories WHERE name = 'Munitions et explosifs'),
        'Hyperion Ammunition',
        'HYP-009',
        '/images/ak_47_ammo.webp'
    ),
    (
        'Charge C4',
        'Charge explosive compacte à déclenchement contrôlé.',
        799.99,
        20,
        (SELECT id FROM categories WHERE name = 'Munitions et explosifs'),
        'Hyperion Explosives',
        'HYP-010',
        '/images/c4.webp'
    ),
    (
        'Grenamon',
        'Grenade expérimentale développée par Hyperion.',
        349.99,
        30,
        (SELECT id FROM categories WHERE name = 'Munitions et explosifs'),
        'Hyperion Explosives',
        'HYP-011',
        '/images/grenamon.webp'
    ),
    (
        'Protection pare-balles lourde',
        'Protection renforcée conçue pour résister aux impacts lourds.',
        1299.00,
        14,
        (SELECT id FROM categories WHERE name = 'Protections'),
        'Hyperion Armour',
        'HYP-012',
        '/images/heavy_bulletproof.webp'
    ),
    (
        'Sous-marin américain',
        'Sous-marin militaire destiné aux opérations en eaux profondes.',
        2500000.00,
        2,
        (SELECT id FROM categories WHERE name = 'Équipements maritimes'),
        'Hyperion Naval Systems',
        'HYP-013',
        '/images/usa_submarines.webp'
    ),
    (
        'Croiseur moderne',
        'Croiseur militaire moderne équipé de systèmes avancés.',
        4750000.00,
        3,
        (SELECT id FROM categories WHERE name = 'Équipements maritimes'),
        'Hyperion Naval Systems',
        'HYP-014',
        '/images/modern_cruiser.webp'
    ),
    (
        'Croiseur de bataille moderne',
        'Croiseur lourd conçu pour les affrontements navals.',
        6250000.00,
        2,
        (SELECT id FROM categories WHERE name = 'Équipements maritimes'),
        'Hyperion Naval Systems',
        'HYP-015',
        '/images/modern_battle_cruiser.webp'
    ),
    (
        'Croiseur futuriste',
        'Bâtiment militaire expérimental équipé de technologies avancées.',
        8500000.00,
        1,
        (SELECT id FROM categories WHERE name = 'Équipements maritimes'),
        'Hyperion Future Systems',
        'HYP-016',
        '/images/futurist_cruiser.webp'
    ),
    (
        'Dreadnought',
        'Navire de guerre lourd disposant d’une puissance de feu considérable.',
        10000000.00,
        1,
        (SELECT id FROM categories WHERE name = 'Équipements maritimes'),
        'Hyperion Naval Systems',
        'HYP-017',
        '/images/dreadnought.webp'
    ),
    (
        'explosif humain',
        'Petite unité explosive mobile à usage unique.',
        1499.00,
        7,
        (SELECT id FROM categories WHERE name = 'Munitions et explosifs'),
        'Hyperion Experimental',
        'HYP-018',
        '/images/human_bomb_2.webp'
    ),
    (
        'Bombe humaine',
        'Unité explosive mobile lourde à usage unique.',
        2999.00,
        4,
        (SELECT id FROM categories WHERE name = 'Munitions et explosifs'),
        'Hyperion Experimental',
        'HYP-019',
        '/images/human_bomb.webp'
    )
ON CONFLICT (reference) DO UPDATE
    SET
        name = EXCLUDED.name,
        description = EXCLUDED.description,
        price = EXCLUDED.price,
        category_id = EXCLUDED.category_id,
        manufacturer = EXCLUDED.manufacturer,
        image_url = EXCLUDED.image_url;

-- =========================================================
-- RÉINITIALISATION DES STOCKS AU DÉMARRAGE
-- =========================================================

UPDATE weapons SET stock = 8  WHERE reference = 'HYP-001';
UPDATE weapons SET stock = 6  WHERE reference = 'HYP-002';
UPDATE weapons SET stock = 12 WHERE reference = 'HYP-003';
UPDATE weapons SET stock = 15 WHERE reference = 'HYP-004';
UPDATE weapons SET stock = 10 WHERE reference = 'HYP-005';
UPDATE weapons SET stock = 5  WHERE reference = 'HYP-006';
UPDATE weapons SET stock = 4  WHERE reference = 'HYP-007';
UPDATE weapons SET stock = 3  WHERE reference = 'HYP-008';
UPDATE weapons SET stock = 50 WHERE reference = 'HYP-009';
UPDATE weapons SET stock = 20 WHERE reference = 'HYP-010';
UPDATE weapons SET stock = 30 WHERE reference = 'HYP-011';
UPDATE weapons SET stock = 14 WHERE reference = 'HYP-012';
UPDATE weapons SET stock = 2  WHERE reference = 'HYP-013';
UPDATE weapons SET stock = 3  WHERE reference = 'HYP-014';
UPDATE weapons SET stock = 2  WHERE reference = 'HYP-015';
UPDATE weapons SET stock = 1  WHERE reference = 'HYP-016';
UPDATE weapons SET stock = 1  WHERE reference = 'HYP-017';
UPDATE weapons SET stock = 7  WHERE reference = 'HYP-018';
UPDATE weapons SET stock = 4  WHERE reference = 'HYP-019';