ALTER TABLE PERSON_INFORMATION DROP FOREIGN KEY FK_PERSON_INFORMATION_PERSON_ID
ALTER TABLE PROFILES DROP FOREIGN KEY CNN_UN_PROFILE_NAME
ALTER TABLE USERS DROP FOREIGN KEY FK_USERS_PROFILE_ID
ALTER TABLE USERS DROP FOREIGN KEY FK_USERS_PERSON_ID
ALTER TABLE USERS DROP FOREIGN KEY CNN_UN_USER_NAME
ALTER TABLE MENU_OPTIONS DROP FOREIGN KEY FK_MENU_OPTIONS_PARENT_ID
ALTER TABLE PERSON_ADD_INFO DROP FOREIGN KEY FK_PERSON_ADD_INFO_INFORMATION_ID
ALTER TABLE PROFILE_OPTIONS DROP FOREIGN KEY FK_PROFILE_OPTIONS_PROFILE_ID
ALTER TABLE PROFILE_OPTIONS DROP FOREIGN KEY FK_PROFILE_OPTIONS_MENU_ID
DROP TABLE PERSONS
DROP TABLE PERSON_INFORMATION
DROP TABLE PROFILES
DROP TABLE USERS
DROP TABLE MENU_OPTIONS
DROP TABLE PERSON_ADD_INFO
DROP TABLE PROFILE_OPTIONS
DELETE FROM SEQ_PERSON_INFORMATION_ID WHERE PERSON_INF_KEY = 'PERSON_INF_ID'
DELETE FROM SEQ_PERSON_ID WHERE PERSON_KEY = 'PERSON_ID'
DELETE FROM SEQ_USER_ID WHERE USER_KEY = 'USER_ID'
DELETE FROM SEQ_MENU_OPTION_ID WHERE MENU_KEY = 'MENU_ID'
DELETE FROM SEQ_PROFILE_ID WHERE PROFILE_KEY = 'PROFILE_ID'
