
INSERT INTO `permission` (`id`, `name`) VALUES (1, 'SUBMIT_DOCUMENT');
INSERT INTO `permission` (`id`, `name`) VALUES (2, 'READ_DOCUMENT');

INSERT INTO `role` (`id`,`name`) VALUES (1, 'ADMINISTRATOR');
INSERT INTO `role` (`id`,`name`) VALUES (2, 'USER');
INSERT INTO `role` (`id`,`name`) VALUES (3, 'AUTHOR');
INSERT INTO `role` (`id`,`name`) VALUES (4, 'READER');
INSERT INTO `role` (`id`,`name`) VALUES (5, 'EDITOR');

INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES (1, 1);
INSERT INTO `role_permissions` (`role_id`, `permission_id`) VALUES (2, 2);

INSERT INTO `scientific_area` (`id`,`name`) VALUES (1, 'aerodynamics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (2, 'aeronautics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (3, 'anatomy');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (4, 'astronomy');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (5, 'astrophysics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (6, 'bacteriology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (7, 'ballistics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (8, 'big science');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (9, 'biochemistry');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (10, 'biology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (11, 'biophysics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (12, 'bioscience');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (13, 'botany');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (14, 'chaos theory');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (15, 'chemistry');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (16, 'chronology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (17, 'climatology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (18, 'computational linguistics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (19, 'computer science');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (20, 'criminology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (21, 'cryogenics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (22, 'dermatology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (23, 'dynamics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (24, 'ecology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (25, 'electronics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (26, 'entomology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (27, 'geology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (28, 'geophysics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (29, 'graphology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (30, 'Informatics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (31, 'information technology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (32, 'information theory');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (33, 'inorganic chemistry');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (34, 'kinetics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (35, 'mechanical engineering');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (36, 'metallurgy');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (37, 'meteorology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (38, 'microbiology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (39, 'microelectronics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (40, 'mycology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (41, 'nuclear physics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (42, 'oceanography');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (43, 'optics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (44, 'organic chemistry');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (45, 'ornithology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (46, 'petrology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (47, 'philology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (48, 'physical geography');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (49, 'physiology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (50, 'plate tectonics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (51, 'psycholinguistics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (52, 'quantum mechanics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (53, 'robotics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (54, 'seismology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (55, 'sexology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (56, 'statistics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (57, 'STEM');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (58, 'telecommunications');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (59, 'thermodynamics');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (60, 'toxicology');
INSERT INTO `scientific_area` (`id`,`name`) VALUES (61, 'zoology');

INSERT INTO `user_data` (`id`,`camunda_user_id`,`city`,`country`,`reviewer`,`user_status`) VALUES (1,'admin','Madrid','Spanija',1,0);
INSERT INTO `user_data` (`id`,`camunda_user_id`,`city`,`country`,`reviewer`,`user_status`) VALUES (2,'pera','Novi Sad','Srbija',1,0);
INSERT INTO `user_data` (`id`,`camunda_user_id`,`city`,`country`,`reviewer`,`user_status`) VALUES (3,'zika','Beograd','Srbija',1,0);
INSERT INTO `user_data` (`id`,`camunda_user_id`,`city`,`country`,`reviewer`,`user_status`) VALUES (4,'mika','Banja Luka','Republika Srpska',1,0);
INSERT INTO `user_data` (`id`,`camunda_user_id`,`city`,`country`,`reviewer`,`user_status`) VALUES (5,'editor','Nis','Srbija',0,0);
INSERT INTO `user_data` (`id`,`camunda_user_id`,`city`,`country`,`reviewer`,`user_status`) VALUES (6,'editor1','Atina','Grcka',0,0);
INSERT INTO `user_data` (`id`,`camunda_user_id`,`city`,`country`,`reviewer`,`user_status`) VALUES (7,'editor2','Pariz','Francuska',0,0);
INSERT INTO `user_data` (`id`,`camunda_user_id`,`city`,`country`,`reviewer`,`user_status`) VALUES (8,'editor3','Rim','Italija',0,0);
INSERT INTO `user_data` (`id`,`camunda_user_id`,`city`,`country`,`reviewer`,`user_status`) VALUES (9,'editor4','Bec','Austrija',0,0);
INSERT INTO `user_data` (`id`,`camunda_user_id`,`city`,`country`,`reviewer`,`user_status`) VALUES (10,'editor5','Berlin','Nemacka',0,0);
INSERT INTO `user_data` (`id`,`camunda_user_id`,`city`,`country`,`reviewer`,`user_status`) VALUES (11,'editor6','Varsava','Poljska',0,0);

INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (1,1);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (2,2);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (3,2);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (4,3);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (5,5);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (6,5);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (7,5);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (8,5);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (9,5);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (10,5);
INSERT INTO `user_roles` (`user_id`,`role_id`) VALUES (11,5);

INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (1,1);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (1,2);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (2,3);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (2,4);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (3,5);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (3,6);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (3,7);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (4,8);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (4,9);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (4,10);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (5,10);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (5,2);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (5,3);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (6,1);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (6,3);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (6,4);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (7,5);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (7,6);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (7,7);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (8,1);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (8,2);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (8,3);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (8,4);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (9,7);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (9,8);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (9,9);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (10,5);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (10,6);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (10,7);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (11,11);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (11,12);
INSERT INTO `user_scientific_areas` (`user_id`,`scientific_area_id`) VALUES (11,13);


INSERT INTO `magazine` (`id`,`name`,`issn`,`username`,`password`,`merchant_id`,`membership_fee`,`currency`,`main_editor_id`,`magazine_status`) VALUES (1, 'Magazine1','issn1','magazine1', 'magazine1', NULL, 2500, 0, 5, 0);
INSERT INTO `magazine` (`id`,`name`,`issn`,`username`,`password`,`merchant_id`,`membership_fee`,`currency`,`main_editor_id`,`magazine_status`) VALUES (2, 'Magazine2','issn2','magazine2', 'magazine2', NULL, 2800, 0, 6, 0);
INSERT INTO `magazine` (`id`,`name`,`issn`,`username`,`password`,`merchant_id`,`membership_fee`,`currency`,`main_editor_id`,`magazine_status`) VALUES (3, 'Magazine3','issn3','magazine3', 'magazine3', NULL, 2700, 0, 7, 0);

INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (1,1);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (1,2);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (1,3);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (1,4);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (1,5);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (2,6);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (2,7);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (2,8);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (2,9);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (2,10);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (3,11);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (3,12);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (3,13);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (3,14);
INSERT INTO `magazine_scientific_areas` (`magazine_id`,`scientific_area_id`) VALUES (3,15);

INSERT INTO `magazine_editors` (`magazine_id`,`editor_id`) VALUES (1,6);
INSERT INTO `magazine_editors` (`magazine_id`,`editor_id`) VALUES (1,7);
INSERT INTO `magazine_editors` (`magazine_id`,`editor_id`) VALUES (1,11);
INSERT INTO `magazine_editors` (`magazine_id`,`editor_id`) VALUES (2,8);
INSERT INTO `magazine_editors` (`magazine_id`,`editor_id`) VALUES (2,9);
INSERT INTO `magazine_editors` (`magazine_id`,`editor_id`) VALUES (2,10);
INSERT INTO `magazine_editors` (`magazine_id`,`editor_id`) VALUES (3,10);
INSERT INTO `magazine_editors` (`magazine_id`,`editor_id`) VALUES (3,6);
INSERT INTO `magazine_editors` (`magazine_id`,`editor_id`) VALUES (3,7);

INSERT INTO `magazine_reviewers` (`magazine_id`,`reviewer_id`) VALUES (1,2);
INSERT INTO `magazine_reviewers` (`magazine_id`,`reviewer_id`) VALUES (1,3);
INSERT INTO `magazine_reviewers` (`magazine_id`,`reviewer_id`) VALUES (1,4);
INSERT INTO `magazine_reviewers` (`magazine_id`,`reviewer_id`) VALUES (2,2);
INSERT INTO `magazine_reviewers` (`magazine_id`,`reviewer_id`) VALUES (2,3);
INSERT INTO `magazine_reviewers` (`magazine_id`,`reviewer_id`) VALUES (2,4);
INSERT INTO `magazine_reviewers` (`magazine_id`,`reviewer_id`) VALUES (3,2);
INSERT INTO `magazine_reviewers` (`magazine_id`,`reviewer_id`) VALUES (3,3);
INSERT INTO `magazine_reviewers` (`magazine_id`,`reviewer_id`) VALUES (3,4);