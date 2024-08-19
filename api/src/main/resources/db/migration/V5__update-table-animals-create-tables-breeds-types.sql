-- Criação da tabela animal_types
CREATE TABLE animal_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    created_by_id BIGINT NOT NULL,
    deleted_by_id BIGINT
);

-- Criação da tabela animal_breeds
CREATE TABLE animal_breeds (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    animal_type_id BIGINT NOT NULL REFERENCES animal_types(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    created_by_id BIGINT NOT NULL,
    deleted_by_id BIGINT
);

-- Criação da tabela animals
CREATE TABLE animals (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id),
    breed_id BIGINT REFERENCES animal_breeds(id),
    type_id BIGINT REFERENCES animal_types(id),
    gender VARCHAR(10) NOT NULL,
    date_of_birth DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    created_by_id BIGINT NOT NULL,
    deleted_by_id BIGINT,
    active BOOLEAN DEFAULT TRUE
);

