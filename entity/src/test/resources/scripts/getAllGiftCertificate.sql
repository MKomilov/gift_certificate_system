CREATE TABLE IF NOT EXISTS gift_certificate
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255)   NOT NULL,
    description      TEXT,
    price            DECIMAL(10, 2) NOT NULL,
    duration         INTEGER,
    create_date      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tag(
                           id   BIGSERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS gift_certificate_tag
(
    gift_certificate_id BIGINT REFERENCES gift_certificate (id) ON DELETE CASCADE,
    tag_id              BIGINT REFERENCES tag (id) ON DELETE CASCADE
);

INSERT INTO tag(name) VALUES ('test1'), ('test2'), ('test3');

INSERT INTO gift_certificate(name, description, price, duration)
VALUES ('gift1', 'Description1', 10, 1),
       ('gift2', 'Description2', 11, 2),
       ('gift3', 'Description3', 12, 3);

INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (CAST(3 AS BIGINT), CAST(1 AS BIGINT)),
       (CAST(3 AS BIGINT), CAST(3 AS BIGINT)),
       (CAST(1 AS BIGINT), CAST(1 AS BIGINT)),
       (CAST(1 AS BIGINT), CAST(2 AS BIGINT)),
       (CAST(2 AS BIGINT), CAST(2 AS BIGINT)),
       (CAST(2 AS BIGINT), CAST(3 AS BIGINT));
