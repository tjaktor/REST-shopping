INSERT INTO USERS (ID, EMAIL, PASSWORD, USERNAME, LOCKED) VALUES (1, 'admin@admin.com', '$2a$10$Hf//VTCqfJ3i8FPPH9F/Lu6vao0IlmJmWMpOUlxNQm.RAoyxnLgui', 'admin', false), (2, 'user@user.com', '$2a$10$Hf//VTCqfJ3i8FPPH9F/Lu6vao0IlmJmWMpOUlxNQm.RAoyxnLgui', 'user', false), (3, 'collector@collector.com', '$2a$10$Hf//VTCqfJ3i8FPPH9F/Lu6vao0IlmJmWMpOUlxNQm.RAoyxnLgui', 'collector', false);

INSERT INTO ROLES (ID, ROLENAME) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER'), (3, 'ROLE_COLLECTOR');

INSERT INTO PRIVILEGES (ID, PRIVILEGE) VALUES (1, 'READ_PRIVILEGE'), (2, 'WRITE_PRIVILEGE'), (3, 'COLLECT_PRIVILEGE');

INSERT INTO USERS_ROLES (USER_ID, ROLE_ID) VALUES (1, 1), (2, 2), (3, 3);

INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (1, 1), (1, 2), (1, 3), (2, 1), (2, 3), (3, 3);