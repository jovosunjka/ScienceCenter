-- password==username

INSERT INTO `permission` (`id`, `name`) VALUES (1, 'SUBMIT_DOCUMENT');
INSERT INTO `permission` (`id`, `name`) VALUES (2, 'READ_DOCUMENT');


INSERT INTO `role` (`id`,`name`) VALUES (1, 'AUTHOR');
INSERT INTO `role` (`id`,`name`) VALUES (2, 'READER');

INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES (1, 1);
INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES (2, 2);

INSERT INTO `user` (`id`,`username`,`password`,`user_status`,`first_name`,`last_name`,`email`,`city`,`country`) VALUES (1,'pera','$2a$10$Q5DbwCixOYIG1Z5C8b/AKOWHlx2.rJZItr9uLs/wQxviobV7RSA/.',0,'Pera','Peric','pera@gmail.com','Novi Sad','Srbija');
INSERT INTO `user` (`id`,`username`,`password`,`user_status`,`first_name`,`last_name`,`email`,`city`,`country`) VALUES (2,'zika', '$2a$10$ZKGhgi8JQOK9dyuFHoboSOsnzW3RsJTA6ewKJZcIercjTIlo9XIPW',0,'Zika','Zikic','zika@gmail.com','Beograd','Srbija');
INSERT INTO `user` (`id`,`username`,`password`,`user_status`,`first_name`,`last_name`,`email`,`city`,`country`) VALUES (3,'mika', '$2a$10$L6Wyo3ym1aJd38CASFGHEe0vTiq6DN.5uTensquVobUmVkt0plZz2',0,'Mika','Mikic','mika@gmail.com','Banja Luka','Republika Srpska');

INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (1,1);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (2,1);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (3,2);

INSERT INTO `magazine` (`id`,`name`,`username`,`password`,`merchant_id`,`membership_fee`,`currency`) VALUES (1, 'Magazine1','magazine1', 'magazine1', NULL, 2500, 0);
INSERT INTO `magazine` (`id`,`name`,`username`,`password`,`merchant_id`,`membership_fee`,`currency`) VALUES (2, 'Magazine2','magazine2', 'magazine2', NULL, 2800, 0);
INSERT INTO `magazine` (`id`,`name`,`username`,`password`,`merchant_id`,`membership_fee`,`currency`) VALUES (3, 'Magazine3','magazine3', 'magazine3', NULL, 2700, 0);