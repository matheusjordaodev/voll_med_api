-- V7__fix-create-table-usuarios.sql
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
                          id    BIGINT NOT NULL AUTO_INCREMENT,
                          login VARCHAR(100) NOT NULL,
                          senha VARCHAR(255) NOT NULL,
                          PRIMARY KEY (id),
                          UNIQUE KEY uk_usuarios_login (login)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
