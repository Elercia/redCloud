delete
from app_user;

truncate dynamic_config;
INSERT INTO dynamic_config
VALUES ('ENVIRONMENT', 'DEV'),
       ('OAUTH_ISSUER', 'DEV'),
       ('OAUTH_SECRET', 'DEV'),
       ('STORAGE_PATH', 'D:/tmp');