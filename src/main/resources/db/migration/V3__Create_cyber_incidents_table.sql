
CREATE TABLE IF NOT EXISTS `cyber_incidents` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `incident_id` VARCHAR(255) NOT NULL UNIQUE,
  `reporter_type` VARCHAR(255) NOT NULL,
  `organization_name` VARCHAR(255),
  `incident_type` VARCHAR(255) NOT NULL,
  `date_of_incident` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `description` TEXT NOT NULL,
  `status` VARCHAR(50) DEFAULT 'INVESTIGATING',
  `reported_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
