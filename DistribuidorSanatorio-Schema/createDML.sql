UPDATE `sanatorio`.`seq_profile_id` SET `PROFILE_VALUE`='1' WHERE `PROFILE_KEY`='PROFILE_ID';
INSERT INTO `sanatorio`.`profiles` (`PROFILE_ID`, `NAME`, `STATE`, `VERSION`,`CREATION_DATE`) VALUES ('1', 'Administrator', '1', '1', sysdate());
 
 
UPDATE `sanatorio`.`seq_user_id` SET `USER_VALUE`='1' WHERE `USER_KEY`='USER_ID';
INSERT INTO `sanatorio`.`users` (`USER_ID`, `PASSWORD`, `STATE`, `USER_NAME`, `VERSION`,`CREATION_DATE`, `PROFILE_ID`) VALUES ('1', '73acd9a5972130b75066c82595a1fae3', 'Active', 'ADMIN', '1', sysdate(),'1');





COMMIT;