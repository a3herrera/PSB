UPDATE `sanatorio`.`seq_profile_id` SET `PROFILE_VALUE`='1' WHERE `PROFILE_KEY`='PROFILE_ID';
INSERT INTO `sanatorio`.`profiles` (`PROFILE_ID`, `NAME`, `STATE`, `VERSION`,`CREATION_DATE`) VALUES ('1', 'Administrator', '1', '1', sysdate());
 
 
UPDATE `sanatorio`.`seq_user_id` SET `USER_VALUE`='1' WHERE `USER_KEY`='USER_ID';
INSERT INTO `sanatorio`.`users` (`USER_ID`, `PASSWORD`, `STATE`, `USER_NAME`, `VERSION`,`CREATION_DATE`, `PROFILE_ID`) VALUES ('1', '73acd9a5972130b75066c82595a1fae3', 'Active', 'ADMIN', '1', sysdate(),'1');


UPDATE `sanatorio`.`seq_menu_option_id` SET `MENU_VALUE`='1' WHERE `MENU_KEY`='MENU_ID';
INSERT INTO `sanatorio`.`menu_options` (`MENU_ID`, `URL`, `ACTIVE`, `DEFAULT_KEY`, `IS_PARENT`, `KEY_`, `REDIRECT`, `VERSION`,`CREATION_DATE`) VALUES ('1', '/administration/administration.xhtml', '1', 'Admin', '1', 'label.menu.admin', '1', '1',sysdate());

UPDATE `sanatorio`.`seq_menu_option_id` SET `MENU_VALUE`='2' WHERE `MENU_KEY`='MENU_ID';
INSERT INTO `sanatorio`.`menu_options` (`MENU_ID`, `URL`, `ACTIVE`, `DEFAULT_KEY`, `IS_PARENT`, `KEY_`, `REDIRECT`, `VERSION`, `PARENT_ID`,`CREATION_DATE`) VALUES ('2', '/administration/profiles.xhtml', '1', 'Profiles', '1', 'label.menu.profiles', '0', '1', '1',sysdate());

UPDATE `sanatorio`.`seq_menu_option_id` SET `MENU_VALUE`='3' WHERE `MENU_KEY`='MENU_ID';
INSERT INTO `sanatorio`.`menu_options` (`MENU_ID`, `URL`, `ACTIVE`, `DEFAULT_KEY`, `IS_PARENT`, `KEY_`, `REDIRECT`, `VERSION`, `PARENT_ID`,`CREATION_DATE`) VALUES ('3', '/administration/users.xhtml', '1', 'Usuarios', '1', 'label.menu.users', '0', '1', '1',sysdate());

UPDATE `sanatorio`.`seq_menu_option_id` SET `MENU_VALUE`='4' WHERE `MENU_KEY`='MENU_ID';
INSERT INTO `sanatorio`.`menu_options` (`MENU_ID`, `URL`, `ACTIVE`, `DEFAULT_KEY`, `IS_PARENT`, `KEY_`, `REDIRECT`, `VERSION`,`CREATION_DATE`) VALUES ('4', '/internals/internals.xhtml', '1', 'Internals', '1', 'label.menu.internal', '1', '1',sysdate());


INSERT INTO `sanatorio`.`profile_options` (`PROFILE_ID`, `MENU_ID`) VALUES ('1', '1');


COMMIT;