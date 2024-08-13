-- ---------------- --
-- ---------------- --
-- ---------------- --

SELECT * FROM moviedetails;
SELECT * FROM cinemadetails;


SELECT * FROM Users;

select * FROM  moviedetails;

SELECT * FROM Role;

SELECT * FROM Permission;

SELECT * FROM UserRoles;
SELECT * FROM Image;

SELECT * FROM Movie;

SELECT * FROM MovieCategory;

SELECT * FROM MovieSceneImage;

SELECT * FROM CinemaHall;

SELECT * FROM Ticket;

SELECT * FROM Event;

SELECT * FROM Category;



SELECT * FROM Movie m
    LEFT JOIN MovieCategory mc ON m.id = mc.movie_id
         LEFT JOIN Category c ON c.id = mc.category_id;



UPDATE CinemaHall
SET capacity = '{
  "seats": [
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
  ]
}'
WHERE id = 3;


INSERT INTO Permission(name) VALUES
('VIEW_MOVIES'),
('EDIT_PROFILE'),
('RATE_MOVIES'),
('VIEW_REVIEWS'),
('COMMENT_ON_REVIEWS'),
('WATCH_TRAILERS'),
('SHARE_CONTENT'),
('CRUD_MOVIES'),
('MANAGE_USERS'),
('VIEW_REPORTS'),
('MODIFY_SETTINGS'),
('APPROVE_REVIEWS'),
('REJECT_REVIEWS'),
('CREATE_PROMOTIONS'),
('MANAGE_CONTENT_CATEGORIES'),
('ACCESS_ANALYTICS'),
('MANAGE_SUBSCRIPTIONS'),
('ACCESS_LOGS'),
('DELETE_REVIEWS'),
('MONITOR_USER_ACTIVITY'),
('MANAGE_REPORTS'),
('REMOVE_INAPPROPRIATE_CONTENT'),
('VIEW_TICKETS'),
('RESPOND_TO_TICKETS'),
('VIEW_USER_INFO'),
('MODIFY_USER_INFO'),
('DEBUG_ISSUES'),
('CONFIGURE_SERVICES'),
('MANAGE_API_KEYS'),
('UPDATE_DOCUMENTATION'),
('MONITOR_APPLICATION_PERFORMANCE'),
('IMPLEMENT_FEATURES'),
('TEST_NEW_RELEASES'),
('REVIEW_CODE');

INSERT INTO Category (name, description)
VALUES ('Sports', 'Movies related to sports'),
       ('Kids', 'Movies suitable for children'),
       ('Family', 'Movies that are suitable for the whole family'),
       ('Thriller', 'Movies that are suspenseful and thrilling'),
       ('Drama', 'Movies that focus on emotional and character-driven stories'),
       ('Comedy', 'Movies designed to make people laugh'),
       ('Horror', 'Movies intended to scare and frighten viewers'),
       ('Action', 'Movies with a lot of action and excitement'),
       ('Western', 'Movies set in the American West, often featuring cowboys and outlaws');

INSERT INTO CinemaHall (name, capacity, status, opening_hours, closing_hours, facilities)
VALUES ('Grand Cinema', 250, 'VIP', '10:00:00', '23:00:00', 'Dolby Atmos, IMAX, Recliner Seats'),
       ('Movie Palace', 180, 'Standard', '09:00:00', '22:00:00', 'Standard Seating, 3D'),
       ('Family Theater', 150, 'Family', '11:00:00', '21:00:00', 'Play Area, Family Seats'),
       ('Action Cinemas', 200, 'VIP', '10:30:00', '23:30:00', 'Action Seating, Surround Sound'),
       ('Classic Hall', 120, 'Standard', '12:00:00', '20:00:00', 'Classic Seats, Digital Projection'),
       ('Urban Theater', 220, 'Family', '10:00:00', '22:00:00', 'Family-Friendly Seating, Luxury Amenities'),
       ('Cineplex Western', 170, 'Standard', '09:30:00', '23:00:00', 'Western Decor, Standard Seating');


