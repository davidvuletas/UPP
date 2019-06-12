#CODE BOOK
insert into scientific_area_code_book(acronym, area, sub_area)
values ('EPH-HIS', 'The Study of the Human Past', 'History');
insert into scientific_area_code_book(acronym, area, sub_area)
values ('CPC-ELT', 'Cultures and Cultural Production', 'Literary Studies');
insert into scientific_area_code_book(acronym, area, sub_area)
values ('CPC-MMU', 'Cultures and Cultural Production', 'Music and Musicology');
insert into scientific_area_code_book(acronym, area, sub_area)
values ('MHC-FIL', 'The Human Mind and its Complexity', 'Philosophy');
insert into scientific_area_code_book(acronym, area, sub_area)
values ('ATP-GEO', 'Environment, Space and Population', 'Geography');
insert into scientific_area_code_book(acronym, area, sub_area)
values ('IIM-GES', 'Individuals, Institutions and Markets', 'Management');
insert into scientific_area_code_book(acronym, area, sub_area)
values ('ATP-AQI', 'Environment, Space and Population', 'Architecture');
insert into scientific_area_code_book(acronym, area, sub_area)
values ('IVC-JUR', 'Institutions, Values, Beliefs and Behaviour', 'Law');


#USERS
insert into user(city, country, email, lastname, name, password)
values ('Novi Sad', 'Serbia', 'davidvuletas@mailinator.com', 'Vuletas', 'David', 'Abcd1234');
insert into user(city, country, email, lastname, name, password)
values ('Novi Sad', 'Serbia', 'jelenailic@mailinator.com', 'Ilic', 'Jelena', 'Abcd1234');
insert into user(city, country, email, lastname, name, password)
values ('Novi Sad', 'Serbia', 'marijakovacevic@mailinator.com', 'Kovacevic', 'Marija', 'Abcd1234');
insert into user(city, country, email, lastname, name, password)
values ('Novi Sad', 'Serbia', 'nikolinastamenic@mailinator.com', 'Stamenic', 'Nikolina', 'Abcd1234');
insert into user(city, country, email, lastname, name, password)
values ('Novi Sad', 'Serbia', 'darkodarkovic@mailinator.com', 'Darkovic', 'Darko', 'Abcd1234');
insert into user(city, country, email, lastname, name, password)
values ('Novi Sad', 'Serbia', 'markojokic@mailinator.com', 'Jokic', 'Marko', 'Abcd1234');
insert into user(city, country, email, lastname, name, password)
values ('Novi Sad', 'Serbia', 'peraperic@mailinator.com', 'Peric', 'Pera', 'Abcd1234');
insert into user(city, country, email, lastname, name, password)
values ('Novi Sad', 'Serbia', 'stankostanic@mailinator.com', 'Stanic', 'Stanko', 'Abcd1234');
insert into user(city, country, email, lastname, name, password)
values ('Novi Sad', 'Serbia', 'jovajovic@mailinator.com', 'Jovic', 'Jova', 'Abcd1234');


#USER -> ROLES
insert into user_roles(user_id, roles)
values (1, 'ADMIN');
insert into user_roles(user_id, roles)
values (1, 'AUTHOR');
insert into user_roles(user_id, roles)
values (2, 'AUTHOR');
insert into user_roles(user_id, roles)
values (3, 'EDITOR');
insert into user_roles(user_id, roles)
values (3, 'REVIEWER');
insert into user_roles(user_id, roles)
values (4, 'EDITOR');
insert into user_roles(user_id, roles)
values (4, 'REVIEWER');
insert into user_roles(user_id, roles)
values (5, 'EDITOR');
insert into user_roles(user_id, roles)
values (6, 'AUTHOR');
insert into user_roles(user_id, roles)
values (7, 'REVIEWER');
insert into user_roles(user_id, roles)
values (8, 'REVIEWER');
insert into user_roles(user_id, roles)
values (9, 'REVIEWER');

#EDITOR
insert into editor(title, scientific_journal_id, user_id)
values ('Assistant Professor', null, 3);
insert into editor(title, scientific_journal_id, user_id)
values ('Engineer', null, 4);
insert into editor(title, scientific_journal_id, user_id)
values ('Lawyer', null, 5);

#EDITOR -> AREA
insert into editor_scientific_areas(editor_id, scientific_areas_id)
values (1, 2);
insert into editor_scientific_areas(editor_id, scientific_areas_id)
values (1, 3);
insert into editor_scientific_areas(editor_id, scientific_areas_id)
values (2, 4);
insert into editor_scientific_areas(editor_id, scientific_areas_id)
values (2, 5);
insert into editor_scientific_areas(editor_id, scientific_areas_id)
values (3, 8);

#BOARD
insert into editorial_board(chief_editor_id)
values (1);
insert into editorial_board(chief_editor_id)
values (2);

#BOARD -> EDITORS
insert into editorial_board_editors(editorial_board_id, editors_id)
values (1, 2);
insert into editorial_board_editors(editorial_board_id, editors_id)
values (1, 3);
insert into editorial_board_editors(editorial_board_id, editors_id)
values (2, 2);
insert into editorial_board_editors(editorial_board_id, editors_id)
values (2, 3);


#AUTHORS
insert into author(user_id)
values (1);
insert into author(user_id)
values (2);
insert into author(user_id)
values (6);

#REVIEWERS
insert into reviewer(title, user_id)
values ('Assistant Professor', 3);
insert into reviewer(title, user_id)
values ('Engineer', 4);
insert into reviewer(title, user_id)
values ('Doctor', 7);
insert into reviewer(title, user_id)
values ('Lawyer', 8);
insert into reviewer(title, user_id)
values ('Electrician', 9);


insert into reviewer_scientific_areas(reviewer_id, scientific_areas_id)
values (1, 1);
insert into reviewer_scientific_areas(reviewer_id, scientific_areas_id)
values (1, 2);
insert into reviewer_scientific_areas(reviewer_id, scientific_areas_id)
values (1, 3);
insert into reviewer_scientific_areas(reviewer_id, scientific_areas_id)
values (1, 4);
insert into reviewer_scientific_areas(reviewer_id, scientific_areas_id)
values (5, 3);
insert into reviewer_scientific_areas(reviewer_id, scientific_areas_id)
values (4, 3);
insert into reviewer_scientific_areas(reviewer_id, scientific_areas_id)
values (2, 3);
insert into reviewer_scientific_areas(reviewer_id, scientific_areas_id)
values (3, 4);
insert into reviewer_scientific_areas(reviewer_id, scientific_areas_id)
values (4, 5);
insert into reviewer_scientific_areas(reviewer_id, scientific_areas_id)
values (4, 6);


#JOURNALS
insert into scientific_journal(issn_number, name, payment_method, board_id)
values ('1234-432X', 'The Russian Review', 'OPEN_ACCESS', 1);
insert into scientific_journal(issn_number, name, payment_method, board_id)
values ('5678-6578', 'Progress in Human Geography', 'READER', null);

insert into scientific_journal_reviewers(scientific_journal_id, reviewers_id)
VALUES (1, 1);
insert into scientific_journal_reviewers(scientific_journal_id, reviewers_id)
VALUES (1, 2);
insert into scientific_journal_reviewers(scientific_journal_id, reviewers_id)
VALUES (1, 3);
insert into scientific_journal_reviewers(scientific_journal_id, reviewers_id)
VALUES (2, 4);


#PAPERS
insert into scientific_paper(abstract_of_paper, keywords, path_topdf, main_author_id,
                             scientific_area_id, status)
values ('Progress in Human Geography is the journal of choice for
  those wanting to know about the state of the art in all areas of human geography
   research and scholarship. It is published six times per year in paper format
   and - in Online First - continuously in electronic format.'
            ' The six editors of PiHG are supported by an international', 'Human, Geography, Issue', '', 1, 5,
        'ACCEPTED');

insert into scientific_paper(abstract_of_paper, keywords, path_topdf, main_author_id,
                             scientific_area_id, status)
values ('The Russian Review is a major independent peer-reviewed multi-disciplinary academic '
            'journal devoted to the history,'
            ' literature, culture, fine arts, cinema,'
            ' society, and politics of the Russian Federation,'
            ' former Soviet Union and former Russian Empire.', 'Russian, Review, Soviet, Union', '', 2, 1,
        'ACCEPTED');

#JOURNAL -> PAPER
insert into scientific_journal_papers(scientific_journal_id, papers_id)
values (2, 1);
insert into scientific_journal_papers(scientific_journal_id, papers_id)
values (1, 2);

#AREA -> JOURNAL
insert into scientific_journal_area(scientific_journal_id, area_id)
values (1, 1);
insert into scientific_journal_area(scientific_journal_id, area_id)
values (1, 2);
insert into scientific_journal_area(scientific_journal_id, area_id)
values (1, 3);
insert into scientific_journal_area(scientific_journal_id, area_id)
values (2, 4);
insert into scientific_journal_area(scientific_journal_id, area_id)
values (2, 5);


#CO-AUTHORS
insert into co_author(author_id)
values (3);
insert into co_author(city, country, email, lastname, name)
values ('Zrenjanin', 'Serbia', 'mirkom@gmail.com', 'Mirkovic', 'Mirko');
insert into co_author(city, country, email, lastname, name)
values ('Beograd', 'Serbia', 'milkra@gmail.com', 'Kraljevic', 'Milana');

#CO-AUTHORS -> PAPER
insert into scientific_paper_co_authors(scientific_paper_id, co_authors_id)
VALUES (1, 1);
insert into scientific_paper_co_authors(scientific_paper_id, co_authors_id)
VALUES (1, 2);
insert into scientific_paper_co_authors(scientific_paper_id, co_authors_id)
VALUES (2, 3);

#REVIEW
insert into review (comment_for_author, comment_for_editor, suggestion_for_accept, reviewer_id)
values ('Maybe to change abstract', 'I think that this is ok for publish, with minor changes', 2, 1);
insert into review (comment_for_author, comment_for_editor, suggestion_for_accept, reviewer_id)
values ('This paper content is not relevant to title, this is not ok', 'I will not accept this paper due to bad format',
        4, 2);
insert into review (comment_for_author, comment_for_editor, suggestion_for_accept, reviewer_id)
values ('Wooow nice paper, no comment', 'I will accept this paper immediately',
        1, 3);
insert into review (comment_for_author, comment_for_editor, suggestion_for_accept, reviewer_id)
values ('Hmm check one more time title of paper, maybe to change it',
        'I will suggest that author need to make changes in title and in content',
        3, 4);
insert into review (comment_for_author, comment_for_editor, suggestion_for_accept, reviewer_id)
values ('Seems good for me, good concept',
        'Seems good for me, good concept, good title, and shortly described',
        1, 4);

# REVIEW -> PAPER

insert into scientific_paper_reviews(scientific_paper_id, reviews_id)
VALUES (1, 1);
insert into scientific_paper_reviews(scientific_paper_id, reviews_id)
VALUES (1, 2);
insert into scientific_paper_reviews(scientific_paper_id, reviews_id)
VALUES (1, 3);
insert into scientific_paper_reviews(scientific_paper_id, reviews_id)
VALUES (2, 4);
insert into scientific_paper_reviews(scientific_paper_id, reviews_id)
VALUES (2, 5);



