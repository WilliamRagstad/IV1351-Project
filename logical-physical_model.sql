CREATE TABLE contact_person (
 social_security_number VARCHAR(12) NOT NULL,
 firstname VARCHAR(40),
 lastname VARCHAR(40),
 email VARCHAR(50),
 phone VARCHAR(11) NOT NULL
);

ALTER TABLE contact_person ADD CONSTRAINT PK_contact_person PRIMARY KEY (social_security_number);


CREATE TABLE discount (
 discount_id INT NOT NULL,
 title VARCHAR(20) NOT NULL,
 description VARCHAR(50),
 amount FLOAT(20) NOT NULL
);

ALTER TABLE discount ADD CONSTRAINT PK_discount PRIMARY KEY (discount_id);


CREATE TABLE instrument (
 instrument_id INT NOT NULL,
 name VARCHAR(40) NOT NULL,
 brand VARCHAR(40)
);

ALTER TABLE instrument ADD CONSTRAINT PK_instrument PRIMARY KEY (instrument_id);


CREATE TABLE payment (
 payment_id INT NOT NULL,
 discount_id INT,
 total FLOAT(20)
);

ALTER TABLE payment ADD CONSTRAINT PK_payment PRIMARY KEY (payment_id);


CREATE TABLE person (
 social_security_number VARCHAR(12) NOT NULL,
 firstname VARCHAR(40) NOT NULL,
 lastname VARCHAR(40) NOT NULL,
 age SMALLINT NOT NULL,
 email VARCHAR(50),
 phone VARCHAR(11) NOT NULL,
 street VARCHAR(40) NOT NULL,
 zip SMALLINT NOT NULL,
 city VARCHAR(40)
);

ALTER TABLE person ADD CONSTRAINT PK_person PRIMARY KEY (social_security_number);


CREATE TABLE student (
 student_id INT NOT NULL,
 social_security_number VARCHAR(12) NOT NULL,
 contact_person VARCHAR(12)
);

ALTER TABLE student ADD CONSTRAINT PK_student PRIMARY KEY (student_id);


CREATE TABLE application (
 application_id INT NOT NULL,
 student_id INT NOT NULL,
 instrument VARCHAR(40),
 knowledge_level INT,
 lesson_type VARCHAR(40)
);

ALTER TABLE application ADD CONSTRAINT PK_application PRIMARY KEY (application_id);


CREATE TABLE audition (
 audition_id INT NOT NULL,
 application_id INT NOT NULL,
 student_id INT NOT NULL,
 passed BIT(1) NOT NULL,
 date DATE NOT NULL,
 time TIMESTAMP(10) NOT NULL
);

ALTER TABLE audition ADD CONSTRAINT PK_audition PRIMARY KEY (audition_id);


CREATE TABLE instructor (
 instructor_id INT NOT NULL,
 social_security_number VARCHAR(12) NOT NULL,
 teaches_ensembles BIT(1) NOT NULL
);

ALTER TABLE instructor ADD CONSTRAINT PK_instructor PRIMARY KEY (instructor_id);


CREATE TABLE rental (
 rental_id INT NOT NULL,
 date DATE NOT NULL,
 duration SMALLINT,
 instrument_id INT NOT NULL,
 student_id INT NOT NULL
);

ALTER TABLE rental ADD CONSTRAINT PK_rental PRIMARY KEY (rental_id);


CREATE TABLE rental_payment (
 rental_payment_id INT NOT NULL,
 rental_id INT NOT NULL,
 payment_id INT NOT NULL
);

ALTER TABLE rental_payment ADD CONSTRAINT PK_rental_payment PRIMARY KEY (rental_payment_id);


CREATE TABLE sibling_discount (
 sibling_discount_id INT NOT NULL,
 discount_id INT NOT NULL,
 student_id INT
);

ALTER TABLE sibling_discount ADD CONSTRAINT PK_sibling_discount PRIMARY KEY (sibling_discount_id);


CREATE TABLE booking (
 booking_id INT NOT NULL,
 instructor_id INT,
 student_id INT,
 date DATE,
 time TIME(10)
);

ALTER TABLE booking ADD CONSTRAINT PK_booking PRIMARY KEY (booking_id);


CREATE TABLE ensemble (
 ensemble_id INT NOT NULL,
 instructor_id INT NOT NULL,
 genre VARCHAR(30),
 max_places SMALLINT,
 min_places SMALLINT,
 enrolled CHAR(10),
 date DATE,
 time TIMESTAMP(10)
);

ALTER TABLE ensemble ADD CONSTRAINT PK_ensemble PRIMARY KEY (ensemble_id);


CREATE TABLE ensemble_student (
 ensemble_id INT NOT NULL,
 student_id INT NOT NULL
);

ALTER TABLE ensemble_student ADD CONSTRAINT PK_ensemble_student PRIMARY KEY (ensemble_id,student_id);


CREATE TABLE group_lesson (
 group_lesson_id INT NOT NULL,
 instructor_id INT NOT NULL,
 instrument_id INT NOT NULL,
 knowledge_level SMALLINT,
 max_places SMALLINT,
 min_places SMALLINT,
 enrolled SMALLINT,
 date DATE,
 time TIMESTAMP(10)
);

ALTER TABLE group_lesson ADD CONSTRAINT PK_group_lesson PRIMARY KEY (group_lesson_id);


CREATE TABLE group_lesson_student (
 group_lesson_id INT NOT NULL,
 student_id INT NOT NULL
);

ALTER TABLE group_lesson_student ADD CONSTRAINT PK_group_lesson_student PRIMARY KEY (group_lesson_id,student_id);


CREATE TABLE instructed_instrument (
 instructed_instrument_id INT NOT NULL,
 instrument_id INT NOT NULL,
 instructor_id INT NOT NULL
);

ALTER TABLE instructed_instrument ADD CONSTRAINT PK_instructed_instrument PRIMARY KEY (instructed_instrument_id);


CREATE TABLE lesson (
 lesson_id INT NOT NULL,
 instructor_id INT NOT NULL,
 instrument_id INT NOT NULL,
 student_id INT NOT NULL,
 booking_id INT,
 knowledge_level SMALLINT
);

ALTER TABLE lesson ADD CONSTRAINT PK_lesson PRIMARY KEY (lesson_id);


CREATE TABLE lesson_payment (
 lesson_payment_id INT NOT NULL,
 knowledge_level SMALLINT,
 weekend BIT(1),
 payment_id INT NOT NULL,
 sibling_discount_id INT,
 student_id INT NOT NULL
);

ALTER TABLE lesson_payment ADD CONSTRAINT PK_lesson_payment PRIMARY KEY (lesson_payment_id);


ALTER TABLE payment ADD CONSTRAINT FK_payment_0 FOREIGN KEY (discount_id) REFERENCES discount (discount_id);


ALTER TABLE student ADD CONSTRAINT FK_student_0 FOREIGN KEY (social_security_number) REFERENCES person (social_security_number);
ALTER TABLE student ADD CONSTRAINT FK_student_1 FOREIGN KEY (contact_person) REFERENCES contact_person (social_security_number);


ALTER TABLE application ADD CONSTRAINT FK_application_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE audition ADD CONSTRAINT FK_audition_0 FOREIGN KEY (application_id) REFERENCES application (application_id);
ALTER TABLE audition ADD CONSTRAINT FK_audition_1 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE instructor ADD CONSTRAINT FK_instructor_0 FOREIGN KEY (social_security_number) REFERENCES person (social_security_number);


ALTER TABLE rental ADD CONSTRAINT FK_rental_0 FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id);
ALTER TABLE rental ADD CONSTRAINT FK_rental_1 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE rental_payment ADD CONSTRAINT FK_rental_payment_0 FOREIGN KEY (rental_id) REFERENCES rental (rental_id);
ALTER TABLE rental_payment ADD CONSTRAINT FK_rental_payment_1 FOREIGN KEY (payment_id) REFERENCES payment (payment_id);


ALTER TABLE sibling_discount ADD CONSTRAINT FK_sibling_discount_0 FOREIGN KEY (discount_id) REFERENCES discount (discount_id);
ALTER TABLE sibling_discount ADD CONSTRAINT FK_sibling_discount_1 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE booking ADD CONSTRAINT FK_booking_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);
ALTER TABLE booking ADD CONSTRAINT FK_booking_1 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE ensemble ADD CONSTRAINT FK_ensemble_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE ensemble_student ADD CONSTRAINT FK_ensemble_student_0 FOREIGN KEY (ensemble_id) REFERENCES ensemble (ensemble_id);
ALTER TABLE ensemble_student ADD CONSTRAINT FK_ensemble_student_1 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE group_lesson ADD CONSTRAINT FK_group_lesson_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);
ALTER TABLE group_lesson ADD CONSTRAINT FK_group_lesson_1 FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id);


ALTER TABLE group_lesson_student ADD CONSTRAINT FK_group_lesson_student_0 FOREIGN KEY (group_lesson_id) REFERENCES group_lesson (group_lesson_id);
ALTER TABLE group_lesson_student ADD CONSTRAINT FK_group_lesson_student_1 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE instructed_instrument ADD CONSTRAINT FK_instructed_instrument_0 FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id);
ALTER TABLE instructed_instrument ADD CONSTRAINT FK_instructed_instrument_1 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE lesson ADD CONSTRAINT FK_lesson_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);
ALTER TABLE lesson ADD CONSTRAINT FK_lesson_1 FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id);
ALTER TABLE lesson ADD CONSTRAINT FK_lesson_2 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE lesson ADD CONSTRAINT FK_lesson_3 FOREIGN KEY (booking_id) REFERENCES booking (booking_id);


ALTER TABLE lesson_payment ADD CONSTRAINT FK_lesson_payment_0 FOREIGN KEY (payment_id) REFERENCES payment (payment_id);
ALTER TABLE lesson_payment ADD CONSTRAINT FK_lesson_payment_1 FOREIGN KEY (sibling_discount_id) REFERENCES sibling_discount (sibling_discount_id);
ALTER TABLE lesson_payment ADD CONSTRAINT FK_lesson_payment_2 FOREIGN KEY (student_id) REFERENCES student (student_id);


