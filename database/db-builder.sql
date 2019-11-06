DROP DATABASE IF EXISTS tUtorSearCher;
CREATE DATABASE tUtorSearCher;
USE tUtorSearCher;
CREATE TABLE IF NOT EXISTS Users (
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(256) UNIQUE NOT NULL,
  pass_hash VARCHAR(256) NOT NULL,
  picture_url VARCHAR(4096) NULL,
  name VARCHAR(128) NULL,
  grade VARCHAR(100) NULL,
  bio VARCHAR(1024) NULL,
  date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  date_active TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_users_email_passhash (email, pass_hash)
);

INSERT INTO Users(email, pass_hash, name) VALUES
('tony@usc.edu', 'password', 'Tony'),
('eye@usc.edu', 'password', 'Eric'),
('teagan@usc.edu', 'password', 'Teagan');


CREATE TABLE IF NOT EXISTS AuthTokens (
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  auth_token VARCHAR(256) NOT NULL,
  date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  date_active TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY fk_authtokens_userid (user_id) REFERENCES Users(id),
  INDEX idx_authtokens_userid_authtoken (user_id, auth_token)
);

CREATE TABLE IF NOT EXISTS Courses (
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  course_number VARCHAR(10) UNIQUE NOT NULL,
  course_name VARCHAR(128) NULL,
  description VARCHAR(1024) NULL,
  course_year SMALLINT NULL,
  semester TINYINT NULL,
  date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO Courses(course_number) VALUES
('CSCI103'), ('CSCI104'), ('CSCI201');

CREATE TABLE IF NOT EXISTS UserCourses (
  user_id INT(11) NOT NULL,
  course_id INT(11) NOT NULL,
  FOREIGN KEY fk_usercourses_userid (user_id) REFERENCES Users(id),
  FOREIGN KEY fk_usercourses_courseid (course_id) REFERENCES Courses(id),
  CONSTRAINT pk_uc PRIMARY KEY(user_id, course_id)
);

CREATE TABLE IF NOT EXISTS CourseOffered (
  user_id INT(11) NOT NULL,
  course_id INT(11) NOT NULL,
  FOREIGN KEY fk_usercourses_userid (user_id) REFERENCES Users(id),
  FOREIGN KEY fk_usercourses_courseid (course_id) REFERENCES Courses(id),
  CONSTRAINT pk_co PRIMARY KEY(user_id, course_id)
);
INSERT INTO CourseOffered(user_id, course_id) VALUES
(1,1), (2,1), (2,2);

CREATE TABLE IF NOT EXISTS CourseOffered (
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  course_id INT(11) NOT NULL,
  FOREIGN KEY fk_usercourses_userid (user_id) REFERENCES Users(id),
  FOREIGN KEY fk_usercourses_courseid (course_id) REFERENCES Courses(id)
);

CREATE TABLE IF NOT EXISTS Availability (
  user_id INT(11) NOT NULL,
  slot_num INT(11) NOT NULL,
  FOREIGN KEY fk_availability_userid (user_id) REFERENCES Users(id),
  INDEX idx_availability_userid_slotnum (user_id, slot_num),
  CONSTRAINT pk_a PRIMARY KEY(user_id, slot_num)
);

INSERT INTO Availability(user_id, slot_num) VALUES
(1,0),(1,1),(1,2),
(2,0),(2,1),(2,2),(2,3);

CREATE TABLE IF NOT EXISTS Requests (
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  tutor_id INT(11) NOT NULL,
  tutee_id INT(11) NOT NULL,
  course_id INT(11) NOT NULL,
  min_hours INT NOT NULL DEFAULT 0,
  req_status TINYINT NOT NULL DEFAULT 0, -- 0 pending 1 accepted 2 rejected 3 invalid
  date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY fk_requests_tutorid (tutor_id) REFERENCES Users(id),
  FOREIGN KEY fk_requests_tuteeid (tutee_id) REFERENCES Users(id),
  FOREIGN KEY fk_requests_courseid (course_id) REFERENCES Courses(id)
);

INSERT INTO Requests(tutee_id, tutor_id, course_id, req_status) VALUES
(1, 2, 1, 1);

CREATE TABLE IF NOT EXISTS RequestOverlap(
  req_id INT(11) NOT NULL,
  slot_num INT(11) NOT NULL,
  FOREIGN KEY fk_ro_req_id (req_id) REFERENCES Requests(id),
  INDEX idx_ro_req_id_slot_num (req_id, slot_num),
  CONSTRAINT pk_ro PRIMARY KEY(req_id, slot_num)
);

CREATE TABLE IF NOT EXISTS Notifications (
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  req_id INT(11) NOT NULL,
  sender_id INT(11) NOT NULL,
  receiver_id INT(11) NOT NULL,
  receiver_type INT(11) NOT NULL, -- 0 for tutor 1 for tutee
  pushed BIT  DEFAULT 0,
  date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY fk_notifications_reqid (req_id) REFERENCES Requests(id),
  FOREIGN KEY fk_notifications_receiverid (receiver_id) REFERENCES Users(id),
  FOREIGN KEY fk_notifications_senderid (sender_id) REFERENCES Users(id)
);

INSERT INTO Notifications(req_id, sender_id, receiver_id, receiver_type, pushed) VALUES
(1, 1, 2, 0, 1);

CREATE TABLE IF NOT EXISTS Ratings (
  tutor_id INT(11) NOT NULL,
  tutee_id INT(11) NOT NULL,
  rating TINYINT NOT NULL,
  review VARCHAR(1024) NULL,
  FOREIGN KEY fk_requests_tutorid (tutor_id) REFERENCES Users(id),
  FOREIGN KEY fk_requests_tuteeid (tutee_id) REFERENCES Users(id),
  INDEX idx_ratings_tutor_tutee (tutor_id, tutee_id),
  CONSTRAINT pk_r PRIMARY KEY(tutor_id, tutee_id)
);
