use netflix;

CREATE TABLE IF NOT EXISTS Image
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    generated_name VARCHAR(100) NOT NULL,
    file_name      VARCHAR(100) NOT NULL,
    file_type      VARCHAR(10)  NOT NULL,
    size           BIGINT       NOT NULL,
    extension      VARCHAR(255) NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Users
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    fullname     VARCHAR(255)               NOT NULL,
    username     VARCHAR(50) UNIQUE         NOT NULL,
    password     VARCHAR(255)               NOT NULL,
    email        VARCHAR(255)               NOT NULL,
    role         varchar(50) DEFAULT 'USER' NOT NULL,
    phone_number VARCHAR(15) DEFAULT 'UNKNOWN PHONE NUMBER',
    image_id     INT,
    created_at   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FOREIGN KEY (image_id) REFERENCES Image (id) ON DELETE CASCADE
);

CREATE TABLE Role
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name varchar(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Permission
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       varchar(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE UserRoles
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    role_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT FOREIGN KEY (role_id) REFERENCES Role (id),
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES Users (id)
);

CREATE TABLE IF NOT EXISTS RolePermissions
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    role_id       INT NOT NULL,
    permission_id INT NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FOREIGN KEY (role_id) REFERENCES Role (id),
    CONSTRAINT FOREIGN KEY (permission_id) REFERENCES Permission (id)
);


CREATE TABLE IF NOT EXISTS Category
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS MovieCategory
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    movie_id    INT NOT NULL,
    category_id INT NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FOREIGN KEY (movie_id) REFERENCES Movie (id) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (category_id) REFERENCES Category (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Movie
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(100)  NOT NULL,
    director      VARCHAR(100)  NOT NULL,
    duration      INT           NOT NULL,
    rating        DECIMAL(3, 1) NOT NULL,
    language      VARCHAR(50)   NOT NULL,
    description   TEXT          NOT NULL,
    thriller_link varchar(200)  NOT NULL,
    image_id      INT           NOT NULL,
    release_date  DATE          NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FOREIGN KEY (image_id) REFERENCES Image (id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS MovieSceneImage
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    movie_id   INT NOT NULL,
    image_id   INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FOREIGN KEY (movie_id) REFERENCES Movie (id) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (image_id) REFERENCES Image (id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS CinemaHall
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) UNIQUE NOT NULL,
    capacity      JSON                NOT NULL,
    status        VARCHAR(100)        NOT NULL,
    opening_hours TIME                NOT NULL,
    closing_hours TIME                NOT NULL,
    facilities    TEXT                NOT NULL,
    image_id      INT,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FOREIGN KEY (image_id) REFERENCES Image (id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS Ticket
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    user_id        INT,
    movie_id       INT,
    cinema_hall_id INT,
    status         VARCHAR(20)    NOT NULL,
    show_time      TIMESTAMP      NOT NULL,
    row_seat       INT            NOT NULL,
    column_seat    INT            NOT NULL,
    price          DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50)    NOT NULL,
    purchase_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES Users (id),
    CONSTRAINT FOREIGN KEY (movie_id) REFERENCES Movie (id),
    CONSTRAINT FOREIGN KEY (cinema_hall_id) REFERENCES CinemaHall (id)
);
CREATE TABLE IF NOT EXISTS Event
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    movie_id        INT            NOT NULL,
    cinema_hall_id  INT            NOT NULL,
    show_time       TIMESTAMP      NOT NULL,
    price           DECIMAL(10, 2) NOT NULL,
    available_seats INT            NOT NULL,
    status          VARCHAR(20)    NOT NULL,
    description     TEXT,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FOREIGN KEY (movie_id) REFERENCES Movie (id),
    CONSTRAINT FOREIGN KEY (cinema_hall_id) REFERENCES CinemaHall (id)
);

CREATE UNIQUE INDEX idx_unique_user_role ON UserRoles (role_id, user_id);
CREATE UNIQUE INDEX idx_unique_event ON Event (movie_id, cinema_hall_id, show_time);
CREATE UNIQUE INDEX idx_unique_role_permission ON RolePermissions (role_id, permission_id);
