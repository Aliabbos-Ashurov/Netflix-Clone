use netflix;


CREATE VIEW MovieDetails AS
SELECT m.id                                                                             AS movie_id,
       m.title                                                                          AS movie_title,
       m.director                                                                       AS movie_director,
       m.duration                                                                       AS movie_duration,
       m.rating                                                                         AS movie_rating,
       m.language                                                                       AS movie_language,
       m.description                                                                    AS movie_description,
       m.thriller_link                                                                  AS movie_thriller_link,
       m.release_date                                                                   AS movie_release_date,
       mi.generated_name                                                                AS movie_image_generated_name,
       mi.extension                                                                     AS movie_image_extension,
       GROUP_CONCAT(DISTINCT c.name ORDER BY c.name SEPARATOR ', ')                     AS categories,
       GROUP_CONCAT(DISTINCT s.generated_name ORDER BY s.generated_name SEPARATOR ', ') AS scene_images
FROM Movie m
         JOIN Image mi ON m.image_id = mi.id
         LEFT JOIN MovieCategory mc ON m.id = mc.movie_id
         LEFT JOIN Category c ON mc.category_id = c.id
         LEFT JOIN MovieSceneImage msi ON m.id = msi.movie_id
         LEFT JOIN Image s ON msi.image_id = s.id
GROUP BY m.id,
         m.title,
         m.director,
         m.duration,
         m.rating,
         m.language,
         m.description,
         m.thriller_link,
         m.release_date,
         mi.generated_name,
         mi.extension
ORDER BY m.title;




CREATE VIEW CinemaDetails AS
SELECT
    ch.id AS cinema_hall_id,
    ch.name AS cinema_hall_name,
    ch.capacity,
    ch.status AS cinema_hall_status,
    ch.opening_hours,
    ch.closing_hours,
    ch.facilities,
    ch.created_at AS cinema_created_at,
    i.id AS image_id,
    i.generated_name AS image_generated_name,
    i.extension AS image_extension,
    i.created_at AS image_created_at
FROM
    CinemaHall ch
        LEFT JOIN
    Image i ON ch.image_id = i.id;