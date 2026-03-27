CREATE TABLE IF NOT EXISTS `license_applications` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `applicant_name` VARCHAR(255) NOT NULL,
  `registration_number` VARCHAR(255) NOT NULL,
  `contact_email` VARCHAR(255),
  `contact_phone` VARCHAR(20),
  `license_type` VARCHAR(255) NOT NULL,
  `technical_proposal` LONGTEXT,
  `financial_provision` LONGTEXT,
  `status` VARCHAR(50) DEFAULT 'PENDING',
  `admin_review_notes` LONGTEXT,
  `submitted_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

