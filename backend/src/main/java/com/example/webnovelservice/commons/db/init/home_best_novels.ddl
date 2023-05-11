SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE novel AUTO_INCREMENT=1;
ALTER TABLE showcase_type AUTO_INCREMENT=1;

TRUNCATE TABLE showcase_type;
TRUNCATE TABLE novel_showcase_type;
TRUNCATE TABLE novel;

-- Insert into novel table
INSERT INTO novel (title, chapter_count, description, author_id, view_count, created_at, updated_at)
VALUES
    ('The Haunting of Hill House', 1, 'A chilling tale of terror and horror', 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Pride and Prejudice', 1, 'A delightful romantic comedy of manners', 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('The Girl on the Train', 1, 'A gripping psychological thriller', 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Dune', 1, 'A classic of science fiction literature', 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Frankenstein', 1, 'A Gothic masterpiece of horror', 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Sense and Sensibility', 1, 'A charming tale of love and sisterhood', 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('The Silence of the Lambs', 1, 'A thrilling novel of suspense and horror', 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Brave New World', 1, 'A dystopian vision of the future', 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Dracula', 1, 'The quintessential vampire novel', 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Emma', 1, 'A charming tale of matchmaking and love', 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Mystery of the Lost City', 1, 'An exciting adventure to find the ancient city', FLOOR(RAND() * 5) + 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Love in the Moonlight', 1, 'A romantic story set in a mystical land', FLOOR(RAND() * 5) + 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Escape from Mars', 1, 'A thrilling space adventure', FLOOR(RAND() * 5) + 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('The Secret Cove', 1, 'A story of friendship and adventure on a mysterious island', FLOOR(RAND() * 5) + 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('The Haunted Mansion', 1, 'A chilling ghost story', FLOOR(RAND() * 5) + 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Timeless Love', 1, 'A heartwarming romance that defies time', FLOOR(RAND() * 5) + 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('The Robot Uprising', 1, 'An action-packed story of a world dominated by machines', FLOOR(RAND() * 5) + 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('The Enchanted Forest', 1, 'A magical tale of discovery and friendship', FLOOR(RAND() * 5) + 1, FLOOR(RAND() * 1000), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()));

-- Insert into genre table
INSERT INTO genre (genre_name)
VALUES
    ('horror'),
    ('romance'),
    ('comedy'),
    ('thriller'),
    ('science fiction'),
    ('adventure'),
    ('fantasy');

-- insert relationship between novel and genre
INSERT INTO novel_genre (novel_id, genre_id)
VALUES
    ((SELECT id FROM novel WHERE title = 'The Haunting of Hill House'), (SELECT id FROM genre WHERE genre_name = 'horror')),
    ((SELECT id FROM novel WHERE title = 'Pride and Prejudice'), (SELECT id FROM genre WHERE genre_name = 'romance')),
    ((SELECT id FROM novel WHERE title = 'Pride and Prejudice'), (SELECT id FROM genre WHERE genre_name = 'comedy')),
    ((SELECT id FROM novel WHERE title = 'The Girl on the Train'), (SELECT id FROM genre WHERE genre_name = 'thriller')),
    ((SELECT id FROM novel WHERE title = 'Dune'), (SELECT id FROM genre WHERE genre_name = 'science fiction')),
    ((SELECT id FROM novel WHERE title = 'Frankenstein'), (SELECT id FROM genre WHERE genre_name = 'horror')),
    ((SELECT id FROM novel WHERE title = 'Sense and Sensibility'), (SELECT id FROM genre WHERE genre_name = 'romance')),
    ((SELECT id FROM novel WHERE title = 'Sense and Sensibility'), (SELECT id FROM genre WHERE genre_name = 'comedy')),
    ((SELECT id FROM novel WHERE title = 'The Silence of the Lambs'), (SELECT id FROM genre WHERE genre_name = 'thriller')),
    ((SELECT id FROM novel WHERE title = 'The Silence of the Lambs'), (SELECT id FROM genre WHERE genre_name = 'horror')),
    ((SELECT id FROM novel WHERE title = 'Brave New World'), (SELECT id FROM genre WHERE genre_name = 'science fiction')),
    ((SELECT id FROM novel WHERE title = 'Dracula'), (SELECT id FROM genre WHERE genre_name = 'horror')),
    ((SELECT id FROM novel WHERE title = 'Emma'), (SELECT id FROM genre WHERE genre_name = 'romance')),
    ((SELECT id FROM novel WHERE title = 'Emma'), (SELECT id FROM genre WHERE genre_name = 'comedy')),
    ((SELECT id FROM novel WHERE title = 'Mystery of the Lost City'), (SELECT id FROM genre WHERE genre_name = 'adventure')),
    ((SELECT id FROM novel WHERE title = 'Love in the Moonlight'), (SELECT id FROM genre WHERE genre_name = 'fantasy')),
    ((SELECT id FROM novel WHERE title = 'Escape from Mars'), (SELECT id FROM genre WHERE genre_name = 'science fiction')),
    ((SELECT id FROM novel WHERE title = 'Escape from Mars'), (SELECT id FROM genre WHERE genre_name = 'adventure')),
    ((SELECT id FROM novel WHERE title = 'The Secret Cove'), (SELECT id FROM genre WHERE genre_name = 'adventure')),
    ((SELECT id FROM novel WHERE title = 'The Haunted Mansion'), (SELECT id FROM genre WHERE genre_name = 'horror')),
    ((SELECT id FROM novel WHERE title = 'Timeless Love'), (SELECT id FROM genre WHERE genre_name = 'romance')),
    ((SELECT id FROM novel WHERE title = 'The Robot Uprising'), (SELECT id FROM genre WHERE genre_name = 'science fiction')),
    ((SELECT id FROM novel WHERE title = 'The Enchanted Forest'), (SELECT id FROM genre WHERE genre_name = 'fantasy'));


INSERT INTO showcase_type (name, description) VALUES
                                                            ('pd_picked_list', 'List of novels picked by PD'),
                                                            ('creators_picked_list', 'List of novels picked by creators');

INSERT INTO chapter (title, chapter_order, thumbnail_url, contents, tokens_required, novel_id, created_at, updated_at)
VALUES
    ('Chapter 1: The Arrival', 1, 'https://images.unsplash.com/photo-1502920970749-a8239374543e', 'The protagonist arrives at the haunted mansion...', 15, 1, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Chapter 2: The Ghostly Encounter', 2, 'https://images.unsplash.com/photo-1555685812-4b943f1cb0eb', 'A chilling encounter with a ghost in the night...', 20, 1, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Chapter 1: The Sisters Meet', 1, 'https://images.unsplash.com/photo-1499084732479-de2c02d45fcc', 'Elizabeth and Jane meet the charming Mr. Bingley...', 10, 2, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Chapter 1: The Train Ride', 1, 'https://images.unsplash.com/photo-1496449903678-68ddcb189a24', 'Rachel witnesses something strange from her train window...', 25, 3, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Chapter 1: The Desert Planet', 1, 'https://images.unsplash.com/photo-1574158622682-e40e69881006', 'Young Paul Atreides arrives on the desert planet Arrakis...', 30, 4, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Chapter 1: The Creation', 1, 'https://images.unsplash.com/photo-1541781774459-bb2af2f05b9d', 'Dr. Frankenstein brings his creation to life...', 20, 5, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Chapter 1: A New Home', 1, 'https://images.unsplash.com/photo-1503341504253-dff4815485f1', 'The Dashwood sisters move to a new home...', 10, 6, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Chapter 1: The Investigation', 1, 'https://images.unsplash.com/photo-1506973035872-a4ec16b8e8d9', 'FBI trainee Clarice Starling is tasked with a chilling investigation...', 25, 7, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Chapter 1: The World State', 1, 'https://images.unsplash.com/photo-1519160558534-5794a2d4af5d', 'A dystopian vision of the future...', 15, 8, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Chapter 1: The Count', 1, 'https://images.unsplash.com/photo-1548247416-ec66f4900b2e', 'Jonathan Harker meets the enigmatic Count Dracula...', 20, 9, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Chapter 1: A New Friendship', 1, 'https://images.unsplash.com/photo-1551803021-9f1ff9f8b0a6', 'Emma Woodhouse forms a new friendship...', 10, 10, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()));



INSERT INTO novel_showcase_type (novel_id, showcase_type_id)
SELECT id, 1 FROM novel WHERE id = 10;

INSERT INTO novel_showcase_type (novel_id, showcase_type_id)
SELECT id, 2 FROM novel WHERE id != 10;

select * from novel;
select * from showcase_type;
select * from novel_showcase_type;

