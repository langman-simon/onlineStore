-- =========================================================
-- UTILISATEURS
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

INSERT INTO categories (
    name,
    description
)
VALUES
    (
        'category.handguns',
        'category.handguns.description'
    ),
    (
        'category.rifles',
        'category.rifles.description'
    ),
    (
        'category.submachineGuns',
        'category.submachineGuns.description'
    ),
    (
        'category.precisionWeapons',
        'category.precisionWeapons.description'
    ),
    (
        'category.launchers',
        'category.launchers.description'
    ),
    (
        'category.ammunitionExplosives',
        'category.ammunitionExplosives.description'
    ),
    (
        'category.protection',
        'category.protection.description'
    ),
    (
        'category.maritime',
        'category.maritime.description'
    ),
    (
        'category.oddities',
        'category.oddities.description'
    )
ON CONFLICT (name) DO UPDATE
    SET
        description = EXCLUDED.description;


-- Migrate legacy French category rows without violating the unique name constraint.
WITH category_mapping(old_name, new_name) AS (
    VALUES
        ('Armes de poing', 'category.handguns'),
        ('Fusils', 'category.rifles'),
        ('Pistolets-mitrailleurs', 'category.submachineGuns'),
        ('Armes de précision', 'category.precisionWeapons'),
        ('Lanceurs', 'category.launchers'),
        ('Munitions et explosifs', 'category.ammunitionExplosives'),
        ('Protections', 'category.protection'),
        ('Équipements maritimes', 'category.maritime'),
        ('Bizarrerie', 'category.oddities')
)
UPDATE weapons AS weapon
SET category_id = new_category.id
FROM categories AS old_category
JOIN category_mapping AS mapping
    ON mapping.old_name = old_category.name
JOIN categories AS new_category
    ON new_category.name = mapping.new_name
WHERE weapon.category_id = old_category.id;

-- Remove legacy rows after every weapon points to the canonical i18n category.
DELETE FROM categories
WHERE name IN (
    'Armes de poing',
    'Fusils',
    'Pistolets-mitrailleurs',
    'Armes de précision',
    'Lanceurs',
    'Munitions et explosifs',
    'Protections',
    'Équipements maritimes',
    'Bizarrerie'
);

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
        49.99,
        8,
        (SELECT id FROM categories WHERE name = 'category.handguns'),
        'Hyperion Heritage',
        'HYP-001',
        '/images/1884_pistol.webp'
    ),
    (
        'Fusil à poudre',
        'Fusil traditionnel utilisant une charge de poudre.',
        89.99,
        6,
        (SELECT id FROM categories WHERE name = 'category.rifles'),
        'Hyperion Heritage',
        'HYP-002',
        '/images/powder_rifle.webp'
    ),
    (
        'Fusil automatique moderne',
        'Fusil automatique moderne destiné aux opérations terrestres.',
        219.00,
        12,
        (SELECT id FROM categories WHERE name = 'category.rifles'),
        'Hyperion Defence',
        'HYP-003',
        '/images/modern_automatic_rifle.webp'
    ),
    (
        'AK-47',
        'Fusil automatique robuste et polyvalent.',
        189.00,
        15,
        (SELECT id FROM categories WHERE name = 'category.rifles'),
        'Hyperion Defence',
        'HYP-004',
        '/images/ak_47.webp'
    ),
    (
        'Pistolet-mitrailleur',
        'Arme automatique compacte adaptée aux combats rapprochés.',
        199.00,
        10,
        (
            SELECT id
            FROM categories
            WHERE name = 'category.submachineGuns'
        ),
        'Hyperion Tactical',
        'HYP-005',
        '/images/submachinesgun.webp'
    ),
    (
        'Fusil de précision',
        'Fusil de précision conçu pour les tirs à longue distance.',
        329.00,
        5,
        (
            SELECT id
            FROM categories
            WHERE name = 'category.precisionWeapons'
        ),
        'Hyperion Precision',
        'HYP-006',
        '/images/sniper.jpg'
    ),
    (
        'RPG-47',
        'Lance-roquettes portable destiné aux cibles blindées.',
        449.00,
        4,
        (SELECT id FROM categories WHERE name = 'category.launchers'),
        'Hyperion Heavy Weapons',
        'HYP-007',
        '/images/rpg_47.webp'
    ),
    (
        'Lance-missiles',
        'Système de lancement de missiles à longue portée.',
        1250.00,
        3,
        (SELECT id FROM categories WHERE name = 'category.launchers'),
        'Hyperion Heavy Weapons',
        'HYP-008',
        '/images/missile_launcher.webp'
    ),
    (
        'Munitions AK-47',
        'Caisse de munitions compatible avec le fusil AK-47.',
        199.99,
        50,
        (
            SELECT id
            FROM categories
            WHERE name = 'category.ammunitionExplosives'
        ),
        'Hyperion Ammunition',
        'HYP-009',
        '/images/ak_47_ammo.webp'
    ),
    (
        'Charge C4',
        'Charge explosive compacte à déclenchement contrôlé.',
        799.99,
        20,
        (
            SELECT id
            FROM categories
            WHERE name = 'category.ammunitionExplosives'
        ),
        'Hyperion Explosives',
        'HYP-010',
        '/images/c4.webp'
    ),
    (
        'Grenamon',
        'Grenade expérimentale développée par Hyperion.',
        349.99,
        30,
        (
            SELECT id
            FROM categories
            WHERE name = 'category.ammunitionExplosives'
        ),
        'Hyperion Explosives',
        'HYP-011',
        '/images/grenamon.webp'
    ),
    (
        'Protection pare-balles lourde',
        'Protection renforcée conçue pour résister aux impacts lourds.',
        1299.00,
        14,
        (SELECT id FROM categories WHERE name = 'category.protection'),
        'Hyperion Armour',
        'HYP-012',
        '/images/heavy_bulletproof.webp'
    ),
    (
        'Sous-marin américain',
        'Sous-marin militaire destiné aux opérations en eaux profondes.',
        2500.00,
        2,
        (
            SELECT id
            FROM categories
            WHERE name = 'category.maritime'
        ),
        'Hyperion Naval Systems',
        'HYP-013',
        '/images/usa_submarines.webp'
    ),
    (
        'Croiseur moderne',
        'Croiseur militaire moderne équipé de systèmes avancés.',
        4750.00,
        3,
        (
            SELECT id
            FROM categories
            WHERE name = 'category.maritime'
        ),
        'Hyperion Naval Systems',
        'HYP-014',
        '/images/modern_cruiser.webp'
    ),
    (
        'Croiseur de bataille moderne',
        'Croiseur lourd conçu pour les affrontements navals.',
        6200.00,
        2,
        (
            SELECT id
            FROM categories
            WHERE name = 'category.maritime'
        ),
        'Hyperion Naval Systems',
        'HYP-015',
        '/images/modern_battle_cruiser.webp'
    ),
    (
        'Croiseur futuriste',
        'Bâtiment militaire expérimental équipé de technologies avancées.',
        8500.00,
        0,
        (
            SELECT id
            FROM categories
            WHERE name = 'category.maritime'
        ),
        'Hyperion Future Systems',
        'HYP-016',
        '/images/futurist_cruiser.webp'
    ),
    (
        'Dreadnought',
        'Navire de guerre lourd disposant d’une puissance de feu considérable.',
        2000.00,
        1,
        (
            SELECT id
            FROM categories
            WHERE name = 'category.maritime'
        ),
        'Hyperion Naval Systems',
        'HYP-017',
        '/images/dreadnought.webp'
    ),
    (
        'Joel',
        'Tu ne sais pas ce que c''est de perdre quelqu un.',
        1499.00,
        2,
        (
            SELECT id
            FROM categories
            WHERE name = 'category.oddities'
        ),
        'Hyperion Experimental',
        'HYP-018',
        '/images/the-last-of-us-joel.webp'
    ),
    (
        'Negan',
        'Quel nom on lui donne ?',
        1444.00,
        4,
        (
            SELECT id
            FROM categories
            WHERE name = 'category.oddities'
        ),
        'The walking Dead',
        'HYP-019',
        '/images/negan.webp'
    ),
    (
    'Homme invisible',
    'On ne le voit pas, mais lui nous voit',
    789.56,
    0,
    (
    SELECT id FROM categories WHERE name = 'category.oddities'
    ),
    'Krupp',
    'HYP-020',
    NULL
)
ON CONFLICT (reference) DO UPDATE
    SET
        name = EXCLUDED.name,
        description = EXCLUDED.description,
        price = EXCLUDED.price,
        stock = EXCLUDED.stock,
        category_id = EXCLUDED.category_id,
        manufacturer = EXCLUDED.manufacturer,
        image_url = EXCLUDED.image_url;

-- =========================================================
-- COMMANDES DE DÉMONSTRATION
-- =========================================================

INSERT INTO customer_orders (
    id,
    created_at,
    original_price,
    discount_amount,
    total_price,
    status,
    payment_reference,
    user_id
)
VALUES
    (
        -1,
        TIMESTAMP '2026-07-20 14:30:00',
        1899.95,
        0.00,
        1899.95,
        'PAID',
        'DEMO-PAYPAL-ORDER-1',
        (SELECT id FROM users WHERE login = 'user')
    ),
    (
        -2,
        TIMESTAMP '2026-07-25 10:15:00',
        1299.00,
        0.00,
        1299.00,
        'PENDING_PAYMENT',
        NULL,
        (SELECT id FROM users WHERE login = 'user')
    )
ON CONFLICT (id) DO UPDATE
    SET
        created_at = EXCLUDED.created_at,
        original_price = EXCLUDED.original_price,
        discount_amount = EXCLUDED.discount_amount,
        total_price = EXCLUDED.total_price,
        status = EXCLUDED.status,
        payment_reference = EXCLUDED.payment_reference,
        user_id = EXCLUDED.user_id;

-- =========================================================
-- LIGNES DES COMMANDES DE DÉMONSTRATION
-- =========================================================

INSERT INTO order_items (
    id,
    order_id,
    weapon_id,
    quantity,
    unit_price,
    subtotal
)
VALUES
    (
        -1,
        -1,
        (SELECT id FROM weapons WHERE reference = 'HYP-001'),
        2,
        649.99,
        1299.98
    ),
    (
        -2,
        -1,
        (SELECT id FROM weapons WHERE reference = 'HYP-009'),
        3,
        199.99,
        599.97
    ),
    (
        -3,
        -2,
        (SELECT id FROM weapons WHERE reference = 'HYP-012'),
        1,
        1299.00,
        1299.00
    )
ON CONFLICT (id) DO UPDATE
    SET
        order_id = EXCLUDED.order_id,
        weapon_id = EXCLUDED.weapon_id,
        quantity = EXCLUDED.quantity,
        unit_price = EXCLUDED.unit_price,
        subtotal = EXCLUDED.subtotal;
