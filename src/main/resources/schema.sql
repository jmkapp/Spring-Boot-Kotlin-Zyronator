DROP TABLE IF EXISTS listener_mix;
DROP TABLE IF EXISTS listener;
DROP TABLE IF EXISTS mix;

CREATE TABLE `listener` (
  `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `enabled` bool DEFAULT FALSE,
  UNIQUE KEY uk_listener_name (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `mix` (
  `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `discogs_api_url` varchar(255) DEFAULT NULL,
  `discogs_web_url` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `recorded` date DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `listener_mix` (
  `id` bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `last_listened` date NOT NULL,
  `listener_id` bigint(20) NOT NULL,
  `mix_id` bigint(20) NOT NULL,
  FOREIGN KEY fk_listener_mix_listener_listener_id (`listener_id`)
	REFERENCES listener(id),
  FOREIGN KEY fk_listener_mix_mix_id (`mix_id`)
	REFERENCES mix(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;