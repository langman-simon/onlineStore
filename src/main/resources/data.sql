INSERT INTO users (last_name, first_name, delivery_address, email, phone, login, password, secondary_phone, is_admin)
VALUES
    ('user', 'user', '12 rue de Bruxelles, 1000 Bruxelles', 'user@user.com', '0569368522', 'user', '$2a$10$96j7flItAKcO2OK6nBAfOuhGPMc47VnwnI4ZXalb0Yx7rwOP06xB6', NULL, FALSE),
    ('admin', 'admin', '1 avenue Hyperion, 1000 Bruxelles', 'admin@admin.com', '0523524785', 'admin', '$2a$10$96j7flItAKcO2OK6nBAfOuhGPMc47VnwnI4ZXalb0Yx7rwOP06xB6', '0369619732', TRUE)
ON CONFLICT (login) DO UPDATE
    SET last_name = EXCLUDED.last_name,
        first_name = EXCLUDED.first_name,
        delivery_address = EXCLUDED.delivery_address,
        email = EXCLUDED.email,
        phone = EXCLUDED.phone,
        password = EXCLUDED.password,
        secondary_phone = EXCLUDED.secondary_phone,
        is_admin = EXCLUDED.is_admin;

INSERT INTO categories (name, description)
VALUES
    ('category.aeg', 'category.aeg.description'),
    ('category.gbb', 'category.gbb.description'),
    ('category.sniper', 'category.sniper.description'),
    ('category.protection', 'category.protection.description'),
    ('category.ammo', 'category.ammo.description'),
    ('category.accessories', 'category.accessories.description')
ON CONFLICT (name) DO UPDATE
    SET description = EXCLUDED.description;

INSERT INTO weapons (name, description, price, stock, category_id, manufacturer, reference, image_url)
VALUES
    ('Réplique M4 AEG', 'Réplique électrique polyvalente, idéale pour débuter en extérieur comme en CQB.', 249.99, 12, (SELECT id FROM categories WHERE name = 'category.aeg'), 'Hyperion Airsoft', 'HYP-001', '/images/products/m4-aeg.webp'),
    ('Réplique AK AEG', 'Réplique électrique robuste au look classique, corps en métal.', 229.99, 10, (SELECT id FROM categories WHERE name = 'category.aeg'), 'Hyperion Airsoft', 'HYP-002', '/images/products/ak-aeg.webp'),
    ('Réplique SMG compacte AEG', 'Réplique électrique compacte, parfaite pour les parties en intérieur.', 199.99, 8, (SELECT id FROM categories WHERE name = 'category.aeg'), 'Hyperion Airsoft', 'HYP-003', '/images/products/smg-aeg.webp'),
    ('Pistolet GBB compact', 'Pistolet à gaz avec culasse mobile, léger et fiable.', 129.99, 15, (SELECT id FROM categories WHERE name = 'category.gbb'), 'Hyperion Airsoft', 'HYP-004', '/images/products/pistol-compact-gbb.webp'),
    ('Pistolet GBB 1911', 'Pistolet à gaz au design classique, culasse en métal.', 119.99, 9, (SELECT id FROM categories WHERE name = 'category.gbb'), 'Hyperion Airsoft', 'HYP-005', '/images/products/pistol-1911-gbb.webp'),
    ('Pistolet GBB Magnum', 'Pistolet à gaz imposant avec un fort recul.', 149.99, 5, (SELECT id FROM categories WHERE name = 'category.gbb'), 'Hyperion Airsoft', 'HYP-006', '/images/products/pistol-magnum-gbb.webp'),
    ('Sniper spring L96', 'Réplique de précision à ressort, réarmement manuel.', 189.99, 6, (SELECT id FROM categories WHERE name = 'category.sniper'), 'Hyperion Airsoft', 'HYP-007', '/images/products/sniper-l96.webp'),
    ('Sniper spring bolt-action', 'Réplique de précision légère, facile à améliorer.', 219.99, 4, (SELECT id FROM categories WHERE name = 'category.sniper'), 'Hyperion Airsoft', 'HYP-008', '/images/products/sniper-bolt.webp'),
    ('Masque grillagé', 'Masque intégral avec grille métallique pour protéger le visage.', 34.99, 25, (SELECT id FROM categories WHERE name = 'category.protection'), 'Hyperion Gear', 'HYP-009', '/images/products/mesh-mask.webp'),
    ('Lunettes de protection', 'Lunettes homologuées résistantes aux impacts de billes.', 24.99, 30, (SELECT id FROM categories WHERE name = 'category.protection'), 'Hyperion Gear', 'HYP-010', '/images/products/goggles.webp'),
    ('Gants tactiques', 'Gants renforcés pour protéger les mains sans perdre en dextérité.', 19.99, 20, (SELECT id FROM categories WHERE name = 'category.protection'), 'Hyperion Gear', 'HYP-011', '/images/products/gloves.webp'),
    ('Billes 0,20 g (4000)', 'Sachet de 4000 billes biodégradables de 0,20 g.', 9.99, 100, (SELECT id FROM categories WHERE name = 'category.ammo'), 'Hyperion Ammo', 'HYP-012', '/images/products/bbs-020.webp'),
    ('Billes 0,25 g (4000)', 'Sachet de 4000 billes biodégradables de 0,25 g pour plus de précision.', 12.99, 80, (SELECT id FROM categories WHERE name = 'category.ammo'), 'Hyperion Ammo', 'HYP-013', '/images/products/bbs-025.webp'),
    ('Green gas', 'Bonbonne de gaz pour répliques GBB.', 11.99, 40, (SELECT id FROM categories WHERE name = 'category.ammo'), 'Hyperion Ammo', 'HYP-014', '/images/products/green-gas.webp'),
    ('Chargeur mid-cap', 'Chargeur de 120 billes compatible avec les répliques M4.', 14.99, 35, (SELECT id FROM categories WHERE name = 'category.accessories'), 'Hyperion Gear', 'HYP-015', '/images/products/midcap-mag.webp'),
    ('Batterie LiPo 7,4 V', 'Batterie compacte et puissante pour répliques AEG.', 24.99, 18, (SELECT id FROM categories WHERE name = 'category.accessories'), 'Hyperion Gear', 'HYP-016', '/images/products/lipo-battery.webp'),
    ('Chargeur de batterie', 'Chargeur équilibreur pour batteries LiPo.', 29.99, 0, (SELECT id FROM categories WHERE name = 'category.accessories'), 'Hyperion Gear', 'HYP-017', '/images/products/battery-charger.webp')
ON CONFLICT (reference) DO UPDATE
    SET name = EXCLUDED.name,
        description = EXCLUDED.description,
        price = EXCLUDED.price,
        stock = EXCLUDED.stock,
        category_id = EXCLUDED.category_id,
        manufacturer = EXCLUDED.manufacturer,
        image_url = EXCLUDED.image_url;

INSERT INTO customer_orders (id, created_at, original_price, discount_amount, total_price, status, payment_reference, user_id)
VALUES
    (-1, TIMESTAMP '2026-07-20 14:30:00', 538.95, 0.00, 538.95, 'PAID', 'DEMO-PAYPAL-ORDER-1', (SELECT id FROM users WHERE login = 'user')),
    (-2, TIMESTAMP '2026-07-25 10:15:00', 189.99, 0.00, 189.99, 'PENDING_PAYMENT', NULL, (SELECT id FROM users WHERE login = 'user'))
ON CONFLICT (id) DO UPDATE
    SET created_at = EXCLUDED.created_at,
        original_price = EXCLUDED.original_price,
        discount_amount = EXCLUDED.discount_amount,
        total_price = EXCLUDED.total_price,
        status = EXCLUDED.status,
        payment_reference = EXCLUDED.payment_reference,
        user_id = EXCLUDED.user_id;

INSERT INTO order_items (id, order_id, weapon_id, quantity, unit_price, subtotal)
VALUES
    (-1, -1, (SELECT id FROM weapons WHERE reference = 'HYP-001'), 2, 249.99, 499.98),
    (-2, -1, (SELECT id FROM weapons WHERE reference = 'HYP-013'), 3, 12.99, 38.97),
    (-3, -2, (SELECT id FROM weapons WHERE reference = 'HYP-007'), 1, 189.99, 189.99)
ON CONFLICT (id) DO UPDATE
    SET order_id = EXCLUDED.order_id,
        weapon_id = EXCLUDED.weapon_id,
        quantity = EXCLUDED.quantity,
        unit_price = EXCLUDED.unit_price,
        subtotal = EXCLUDED.subtotal;