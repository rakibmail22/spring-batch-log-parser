CREATE TABLE log_entry (
  id         INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  entry_date TIMESTAMP(6),
  ip         VARCHAR(256)     NOT NULL,
  request    VARCHAR(256),
  status     VARCHAR(64),
  user_agent VARCHAR(256),
  PRIMARY KEY (id)
);

CREATE TABLE black_list (
  id            INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  ip            VARCHAR(256)     NOT NULL,
  start_date    TIMESTAMP(6),
  frequency     VARCHAR(32),
  threshold     INTEGER,
  request_count INTEGER,
  comments      VARCHAR(1024),
  PRIMARY KEY (id)
);