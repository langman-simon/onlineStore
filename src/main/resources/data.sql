INSERT INTO categories (name, description)
VALUES
    ('Armes de poing', 'Produits compacts'),
    ('Fusils', 'Produits longs'),
    ('Véhicules blindés', 'Véhicules terrestres blindés'),
    ('Protections', 'Équipements de protection'),
    ('Équipements maritimes', 'Équipements destinés au domaine maritime')
ON CONFLICT (name) DO NOTHING;

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
SELECT
    'Produit Alpha',
    'Produit de démonstration pour tester le catalogue.',
    499.99,
    12,
    id,
    'Hyperion Industries',
    'HYP-001',
    NULL
FROM categories
WHERE name = 'Armes de poing'
ON CONFLICT DO NOTHING;

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
SELECT
    'Produit Bravo',
    'Produit de démonstration avec un stock limité.',
    1299.00,
    4,
    id,
    'Hyperion Industries',
    'HYP-002',
    NULL
FROM categories
WHERE name = 'Fusils'
ON CONFLICT DO NOTHING;

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
SELECT
    'Protection Gamma',
    'Équipement de protection destiné aux tests du panier.',
    249.50,
    25,
    id,
    'Hyperion Defence',
    'HYP-003',
    NULL
FROM categories
WHERE name = 'Protections'
ON CONFLICT DO NOTHING;

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
SELECT
    'Véhicule Delta',
    'Produit de démonstration à stock très faible.',
    85000.00,
    2,
    id,
    'Hyperion Motors',
    'HYP-004',
    NULL
FROM categories
WHERE name = 'Véhicules blindés'
ON CONFLICT DO NOTHING;

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
SELECT
    'Équipement Echo',
    'Produit actuellement indisponible pour tester le stock nul.',
    150000.00,
    0,
    id,
    'Hyperion Marine',
    'HYP-005',
    NULL
FROM categories
WHERE name = 'Équipements maritimes'
ON CONFLICT DO NOTHING;