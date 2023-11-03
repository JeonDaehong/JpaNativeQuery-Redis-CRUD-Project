# My CRUD Application with JPA Native Query

A simple CRUD (Create, Read, Update, Delete) application for user management and forum posts using JPA Native Query.

## Database Information

This project utilizes two main tables: `TB_USER` and `TB_BOARD`.

### TB_USER
- `USER_ID` (BIGINT, 20) - Auto-increment primary key.
- `LOGIN_ID` (VARCHAR, 40) - User's login ID.
- `USER_NM` (VARCHAR, 40) - User's name.
- `PWSD` (VARCHAR, 200) - Password stored securely using BCryptPasswordEncoder.
- `CRTE_DTTM` (DATETIME) - Creation timestamp.
- `UPDT_DTTM` (DATETIME) - Update timestamp.

### TB_BOARD
- `BOARD_ID` (BIGINT, 20) - Auto-increment primary key.
- `TITLE` (VARCHAR, 40) - Title of the board post.
- `CONTENT` (TEXT) - Content of the board post.
- `BOARD_VIEW` (INT) - Number of views for the post.
- `USER_ID` (BIGINT, 20) - Foreign key linking to the `USER_ID` in `TB_USER`.
- `CRTE_DTTM` (DATETIME) - Creation timestamp.
- `UPDT_DTTM` (DATETIME) - Update timestamp.

The `USER_ID` in `TB_USER` serves as a reference key to establish a relationship with the `TB_BOARD` table. Additionally, the `PWSD` field is used for securely storing passwords, and its VARCHAR length is set to accommodate the encrypted values.
