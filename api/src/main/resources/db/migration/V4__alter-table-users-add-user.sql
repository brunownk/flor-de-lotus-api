DROP TABLE IF EXISTS users;

-- Cria a nova tabela users com a estrutura desejada
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Adiciona um novo usuário à tabela
INSERT INTO users (login, password) VALUES ('evelincordeiro2012@vet.flordelotus', '$2a$12$U.ZjeMO8MfDyzu1/9JRuCuU1gkdTVK1l2KAAsCpzMsEwaqZcIiNcO');
