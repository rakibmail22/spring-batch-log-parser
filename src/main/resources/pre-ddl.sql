CREATE TABLE log_entry (
  id         INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  entry_date TIMESTAMP,
  ip         VARCHAR(256),
  request    VARCHAR(256),
  status     VARCHAR(64),
  user_agent VARCHAR(256),
  PRIMARY KEY (id)
);