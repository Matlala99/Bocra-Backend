
CREATE TABLE IF NOT EXISTS `audit_logs` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `action` VARCHAR(255) NOT NULL,
  `username` VARCHAR(255),
  `details` TEXT,
  `timestamp` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `ip_address` VARCHAR(50),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
