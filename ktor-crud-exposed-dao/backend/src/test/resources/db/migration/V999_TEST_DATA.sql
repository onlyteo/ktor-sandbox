INSERT INTO PERSONS (NAME) VALUES ('Jack');

INSERT INTO GREETINGS (MESSAGE, PERSON_ID) VALUES ('Hello Jack!', (SELECT ID FROM PERSONS WHERE NAME = 'Jack'));