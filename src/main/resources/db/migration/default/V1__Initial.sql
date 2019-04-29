create table "users"(
 id serial,
 username text unique,
 password text,
 primary key(id)
 );

create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token bytea,
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token bytea,
  authentication_id VARCHAR(256),
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication bytea,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token bytea,
  authentication bytea
);

create table oauth_code (
  code VARCHAR(256), authentication bytea
);

create table oauth_approvals (
  userId VARCHAR(256),
  clientId VARCHAR(256),
  scope VARCHAR(256),
  status VARCHAR(10),
  expiresAt TIMESTAMP,
  lastModifiedAt TIMESTAMP
);

INSERT INTO public.oauth_client_details
(client_id, client_secret, web_server_redirect_uri, "scope", access_token_validity, refresh_token_validity, resource_ids, authorized_grant_types, authorities, additional_information, autoapprove)
VALUES('uq4su8o61nbws5co', '$2a$10$H/3mR5/L2bKcvSbLG0Njhe2RdTUu52fYMs7maR1MhE7RzcTYRlpCC', '', 'read,write,trust', 7200, NULL, 'oauth2-resource', 'client_credentials,password,refresh_token', 'ROLE_CLIENT,ROLE_TRUSTED_CLIENT', '{}', '');
