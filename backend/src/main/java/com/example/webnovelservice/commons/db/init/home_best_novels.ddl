SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE novel AUTO_INCREMENT=1;
TRUNCATE TABLE showcase_type;
TRUNCATE TABLE novel_showcase_type;
TRUNCATE TABLE novel;

INSERT INTO novel (title, last_chapter, description, genre, author_id, created_at, updated_at)
VALUES
    ('The Haunting of Hill House', 1, 'A chilling tale of terror and horror', 'horror', 1, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Pride and Prejudice', 1, 'A delightful romantic comedy of manners', 'romance comedy', 1, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('The Girl on the Train', 1, 'A gripping psychological thriller', 'thriller', 1, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Dune', 1, 'A classic of science fiction literature', 'science fiction', 1, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Frankenstein', 1, 'A Gothic masterpiece of horror', 'horror', 1, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Sense and Sensibility', 1, 'A charming tale of love and sisterhood', 'romance comedy', 1, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('The Silence of the Lambs', 1, 'A thrilling novel of suspense and horror', 'thriller', 1, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Brave New World', 1, 'A dystopian vision of the future', 'science fiction', 1, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Dracula', 1, 'The quintessential vampire novel', 'horror', 1, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW())),
    ('Emma', 1, 'A charming tale of matchmaking and love', 'romance comedy', 1, TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()), TIMESTAMPADD(SECOND, RAND() * 1000000, NOW()));

INSERT INTO showcase_type (list_name, list_description) VALUES
                                                            ('new_release_top', 'New releases from top authors'),
                                                            ('best_seller', 'Bestselling novels of all time');

INSERT INTO novel_showcase_type (novel_id, showcase_type_id)
SELECT id, 1 FROM novel WHERE id = 10;

INSERT INTO novel_showcase_type (novel_id, showcase_type_id)
SELECT id, 2 FROM novel WHERE id != 10;


select * from novel;
select * from showcase_type;
select * from novel_showcase_type;

