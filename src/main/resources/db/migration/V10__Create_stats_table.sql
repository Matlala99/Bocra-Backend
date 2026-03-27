CREATE TABLE IF NOT EXISTS `stats` (
  `id` VARCHAR(255) NOT NULL PRIMARY KEY,
  `value` DOUBLE,
  `suffix` VARCHAR(50),
  `label` VARCHAR(255),
  `description` TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
