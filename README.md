# My CRUD Application with JPA Native Query and Redis Integration

A simple CRUD (Create, Read, Update, Delete) application for user management and forum posts using JPA Native Query. This application also addresses performance issues related to exclusive locks on view counts using Redis.

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

### TB_BOARD_SCORE
- `BOARD_ID` (BIGINT, 20) - Foreign key linking to the `BOARD_ID` in `TB_BOARD`.
- `USER_ID` (BIGINT, 20) - Foreign key linking to the `USER_ID` in `TB_USER`.
- `SCORE` (INT) - Content Score.
- `CRTE_DTTM` (DATETIME) - Creation timestamp.
- `UPDT_DTTM` (DATETIME) - Update timestamp.

The `USER_ID` in `TB_USER` serves as a reference key to establish a relationship with the `TB_BOARD` table. Additionally, the `PWSD` field is used for securely storing passwords, and its VARCHAR length is set to accommodate the encrypted values.

## Features

### User Management
- **Sign-up**: Users can register for an account by providing their login ID, name, and password.

- **Profile Update**: Registered users can update their profile information, including their name.

- **Account Deactivation**: Users have the option to deactivate their accounts if they wish to leave the platform.

- **Login**: Users can securely log in to their accounts with their credentials.

- **Logout**: Users can log out of their accounts to secure their session.

### Forum
- **List Viewing**: Users can browse a list of forum posts, displayed with pagination (10 posts per page).

- **Post Viewing**: Users can view the full content of a forum post, including the title and post body.

- **Post Editing**: Users can edit their own forum posts, but only if they are the authors.

- **Post Deletion**: Users can delete their own forum posts, ensuring post ownership.

- **View Count Increment with Redis**: To address exclusive lock issues on view counts and improve performance, Redis is integrated to manage view counts atomically. This ensures that multiple requests to increment view counts do not conflict, improving the scalability of the application.

### Authorization Control
- **Post Editing and Deletion Authorization**: Users are granted permission to edit and delete only their own posts, ensuring data security.

These features collectively offer a comprehensive user experience for managing accounts and participating in forum discussions with enhanced performance through Redis integration.

<hr>

# JPA 네이티브 쿼리와 Redis 통합을 활용한 CRUD 어플리케이션

JPA 네이티브 쿼리를 사용한 사용자 관리 및 포럼 게시물에 대한 CRUD (생성, 읽기, 업데이트, 삭제) 어플리케이션입니다. 이 어플리케이션은 Redis를 사용하여 조회수에 대한 배타적 잠금으로 인한 성능 문제를 해결하고 있습니다.

## 데이터베이스 정보

이 프로젝트는 두 가지 주요 테이블을 사용합니다: `TB_USER`와 `TB_BOARD`.

### TB_USER
- `USER_ID` (BIGINT, 20) - 자동 증가하는 기본 키.
- `LOGIN_ID` (VARCHAR, 40) - 사용자의 로그인 ID.
- `USER_NM` (VARCHAR, 40) - 사용자의 이름.
- `PWSD` (VARCHAR, 200) - BCryptPasswordEncoder를 사용하여 안전하게 저장된 비밀번호.
- `CRTE_DTTM` (DATETIME) - 생성 타임스탬프.
- `UPDT_DTTM` (DATETIME) - 업데이트 타임스탬프.

### TB_BOARD
- `BOARD_ID` (BIGINT, 20) - 자동 증가하는 기본 키.
- `TITLE` (VARCHAR, 40) - 게시물 제목.
- `CONTENT` (TEXT) - 게시물 내용.
- `BOARD_VIEW` (INT) - 게시물 조회수.
- `USER_ID` (BIGINT, 20) - `TB_USER`의 `USER_ID`와 연결된 외래 키.
- `CRTE_DTTM` (DATETIME) - 생성 타임스탬프.
- `UPDT_DTTM` (DATETIME) - 업데이트 타임스탬프.

### TB_BOARD_SCORE
- `BOARD_ID` (BIGINT, 20) - `TB_BOARD`의 `BOARD_ID`와 연결된 외래 키.
- `USER_ID` (BIGINT, 20) - `TB_USER`의 `USER_ID`와 연결된 외래 키..
- `SCORE` (INT) - 게시글의 점수
- `CRTE_DTTM` (DATETIME) - 생성 타임스탬프.
- `UPDT_DTTM` (DATETIME) - 업데이트 타임스탬프.

`TB_USER`의 `USER_ID`는 `TB_BOARD` 테이블과의 관계를 설정하기 위한 참조 키로 사용됩니다. 또한 `PWSD` 필드는 비밀번호를 안전하게 저장하기 위해 사용되며, 암호화된 값에 맞게 VARCHAR 길이가 설정되어 있습니다.

## 기능

### 사용자 관리
- **가입**: 사용자는 로그인 ID, 이름, 비밀번호를 제공하여 계정을 등록할 수 있습니다.

- **프로필 업데이트**: 등록된 사용자는 이름을 포함한 프로필 정보를 업데이트할 수 있습니다.

- **계정 비활성화**: 사용자는 플랫폼을 떠나고자 할 때 계정을 비활성화할 수 있습니다.

- **로그인**: 사용자는 자신의 자격 증명으로 안전하게 로그인할 수 있습니다.

- **로그아웃**: 사용자는 계정을 안전하게 로그아웃할 수 있습니다.

### 포럼
- **목록 보기**: 사용자는 페이지당 10개의 게시물이 표시되는 포럼 게시물 목록을 탐색할 수 있습니다.

- **게시물 보기**: 사용자는 게시물 제목과 내용을 포함한 전체 포럼 게시물을 볼 수 있습니다.

- **게시물 편집**: 사용자는 자신의 포럼 게시물을 편집할 수 있습니다. 다만 게시물의 저자일 경우에만 가능합니다.

- **게시물 삭제**: 사용자는 자신의 포럼 게시물을 삭제할 수 있으며, 게시물 소유권을 보장합니다.

- **Redis를 통한 조회수 증가**: 조회수에 대한 배타적 잠금 문제를 해결하고 성능을 향상시키기 위해 Redis가 통합되어 있습니다. 이를 통해 여러 요청이 동시에 조회수를 증가시키는 충돌을 방지하고 어플리케이션의 확장성을 향상시킵니다.

### 권한 제어
- **게시물 편집 및 삭제 권한**: 사용자는 자신의 게시물만 편집 및 삭제할 수 있도록 권한이 부여되어 데이터 보안을 보장합니다.

이러한 기능들은 계정 관리 및 포럼 토론에 참여하기 위한 포괄적인 사용자 경험을 제공하며, Redis 통합을 통해 성능을 향상시켰습니다.
