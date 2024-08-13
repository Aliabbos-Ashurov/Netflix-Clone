use netflix;

CREATE VIEW MovieView AS
SELECT m.id                                AS movie_id,
       m.title,
       m.director,
       m.duration,
       m.rating,
       m.language,
       m.description,
       m.image_id,
       img.file_name                       AS image_file_name,
       img.file_type                       AS image_file_type,
       img.size                            AS image_size,
       img.extension                       AS image_extension,
       m.release_date,
       m.created_at,
       GROUP_CONCAT(c.name SEPARATOR ', ') AS categories
FROM Movie m
         JOIN Image img ON m.image_id = img.id
         LEFT JOIN MovieCategory mvc ON m.id = mvc.movie_id
         LEFT JOIN Category c ON mvc.category_id = c.id
GROUP BY m.id, m.title, m.director, m.duration, m.rating, m.language, m.description, m.image_id, img.file_name,
         img.file_type, img.size, img.extension, m.release_date, m.created_at;


CREATE VIEW EventView AS
SELECT e.id    AS event_id,
       e.movie_id,
       m.title AS movie_title,
       e.cinema_hall_id,
       c.name  AS cinema_hall_name,
       e.show_time,
       e.price,
       e.available_seats,
       e.status,
       e.description,
       e.created_at
FROM Event e
         JOIN Movie m ON e.movie_id = m.id
         JOIN CinemaHall c ON e.cinema_hall_id = c.id;


CREATE VIEW EventTicketView AS
SELECT e.id     AS event_id,
       m.title  AS movie_title,
       c.name   AS cinema_hall_name,
       e.show_time,
       t.id     AS ticket_id,
       t.user_id,
       t.status AS ticket_status,
       t.seat_number,
       t.price  AS ticket_price,
       t.payment_method,
       t.purchase_time,
       t.created_at
FROM Event e
         JOIN Movie m ON e.movie_id = m.id
         JOIN CinemaHall c ON e.cinema_hall_id = c.id
         LEFT JOIN Ticket t ON t.movie_id = m.id AND t.cinema_hall_id = c.id AND t.show_time = e.show_time;


CREATE VIEW UserTicketView AS
SELECT u.id     AS user_id,
       u.fullname,
       u.username,
       u.email,
       u.phone_number,
       t.id     AS ticket_id,
       t.movie_id,
       m.title  AS movie_title,
       t.cinema_hall_id,
       c.name   AS cinema_hall_name,
       t.show_time,
       t.status AS ticket_status,
       t.seat_number,
       t.price  AS ticket_price,
       t.payment_method,
       t.purchase_time,
       t.created_at
FROM Users u
         LEFT JOIN Ticket t ON u.id = t.user_id
         LEFT JOIN Movie m ON t.movie_id = m.id
         LEFT JOIN CinemaHall c ON t.cinema_hall_id = c.id;