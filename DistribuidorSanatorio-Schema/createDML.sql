UPDATE `sanatorio`.`seq_profile_id` SET `PROFILE_VALUE`='1' WHERE `PROFILE_KEY`='PROFILE_ID';
INSERT INTO `sanatorio`.`profiles` (`PROFILE_ID`, `NAME`, `STATE`, `VERSION`,`CREATION_DATE`) VALUES ('1', 'Administrator', '1', '1', sysdate());
 
 
UPDATE `sanatorio`.`seq_user_id` SET `USER_VALUE`='1' WHERE `USER_KEY`='USER_ID';
INSERT INTO `sanatorio`.`users` (`USER_ID`, `PASSWORD`, `STATE`, `USER_NAME`, `VERSION`,`CREATION_DATE`, `PROFILE_ID`,`EMAIL`) VALUES ('1', '73acd9a5972130b75066c82595a1fae3', 'ACTIVE', 'ADMIN', '1', sysdate(),'1','passrecover2017@gmail.com');
INSERT INTO `sanatorio`.`users` (`USER_ID`, `PASSWORD`, `STATE`, `USER_NAME`, `VERSION`,`CREATION_DATE`, `PROFILE_ID`,`EMAIL`) VALUES ('3', '10c4981bb793e1698a83aea43030a388', 'ACTIVE', 'admin.admin', '1', sysdate(),'1','admin');
INSERT INTO `sanatorio`.`users` (`USER_ID`, `PASSWORD`, `STATE`, `USER_NAME`, `VERSION`,`CREATION_DATE`, `PROFILE_ID`,`EMAIL`) VALUES ('2', '32129ccd0b3861befbc83383cda8ee87', 'ACTIVE', 'santos.santiago', '1', sysdate(),'1','lecx0110@gmail.com');


UPDATE `sanatorio`.`seq_menu_option_id` SET `MENU_VALUE`='1' WHERE `MENU_KEY`='MENU_ID';
INSERT INTO `sanatorio`.`menu_options` (`MENU_ID`, `URL`, `ACTIVE`, `DEFAULT_KEY`, `IS_PARENT`, `KEY_`, `REDIRECT`, `VERSION`,`CREATION_DATE`) VALUES ('1', '/administration/administration.xhtml', '1', 'Admin', '1', 'label.menu.admin', '1', '1',sysdate());

UPDATE `sanatorio`.`seq_menu_option_id` SET `MENU_VALUE`='2' WHERE `MENU_KEY`='MENU_ID';
INSERT INTO `sanatorio`.`menu_options` (`MENU_ID`, `URL`, `ACTIVE`, `DEFAULT_KEY`, `IS_PARENT`, `KEY_`, `REDIRECT`, `VERSION`, `PARENT_ID`,`CREATION_DATE`) VALUES ('2', '/administration/profiles.xhtml', '1', 'Profiles', '1', 'label.menu.profiles', '1', '1', '1',sysdate());

UPDATE `sanatorio`.`seq_menu_option_id` SET `MENU_VALUE`='3' WHERE `MENU_KEY`='MENU_ID';
INSERT INTO `sanatorio`.`menu_options` (`MENU_ID`, `URL`, `ACTIVE`, `DEFAULT_KEY`, `IS_PARENT`, `KEY_`, `REDIRECT`, `VERSION`, `PARENT_ID`,`CREATION_DATE`) VALUES ('3', '/administration/users.xhtml', '1', 'Users', '1', 'label.menu.users', '1', '1', '1',sysdate());


UPDATE `sanatorio`.`seq_menu_option_id` SET `MENU_VALUE`='4' WHERE `MENU_KEY`='MENU_ID';
INSERT INTO `sanatorio`.`menu_options` (`MENU_ID`, `URL`, `ACTIVE`, `DEFAULT_KEY`, `IS_PARENT`, `KEY_`, `REDIRECT`, `VERSION`,`CREATION_DATE`) VALUES ('4', '/sanatorium/sanatoriums.xhtml', '1', 'sanatorium', '1', 'label.menu.sanatoriums', '1', '1',sysdate());

UPDATE `sanatorio`.`seq_menu_option_id` SET `MENU_VALUE`='5' WHERE `MENU_KEY`='MENU_ID';
INSERT INTO `sanatorio`.`menu_options` (`MENU_ID`, `URL`, `ACTIVE`, `DEFAULT_KEY`, `IS_PARENT`, `KEY_`, `REDIRECT`, `VERSION`, `PARENT_ID`,`CREATION_DATE`) VALUES ('5', '/sanatorium/centers.xhtml', '1', 'Centers', '1', 'label.menu.centers', '1', '1', '4',sysdate());

UPDATE `sanatorio`.`seq_menu_option_id` SET `MENU_VALUE`='6' WHERE `MENU_KEY`='MENU_ID';
INSERT INTO `sanatorio`.`menu_options` (`MENU_ID`, `URL`, `ACTIVE`, `DEFAULT_KEY`, `IS_PARENT`, `KEY_`, `REDIRECT`, `VERSION`, `PARENT_ID`,`CREATION_DATE`) VALUES ('6', '/sanatorium/areas.xhtml', '1', 'Areas', '1', 'label.menu.areas', '1', '1', '4',sysdate());



UPDATE `sanatorio`.`seq_menu_option_id` SET `MENU_VALUE`='7' WHERE `MENU_KEY`='MENU_ID';
INSERT INTO `sanatorio`.`menu_options` (`MENU_ID`, `URL`, `ACTIVE`, `DEFAULT_KEY`, `IS_PARENT`, `KEY_`, `REDIRECT`, `VERSION`,`CREATION_DATE`) VALUES ('7', '/internals/internals.xhtml', '1', 'Internals', '1', 'label.menu.internal', '1', '1',sysdate());


INSERT INTO `sanatorio`.`profile_options` (`PROFILE_ID`, `MENU_ID`) VALUES ('1', '1');
INSERT INTO `sanatorio`.`profile_options` (`PROFILE_ID`, `MENU_ID`) VALUES ('1', '4');
INSERT INTO `sanatorio`.`profile_options` (`PROFILE_ID`, `MENU_ID`) VALUES ('1', '7');

COMMIT;