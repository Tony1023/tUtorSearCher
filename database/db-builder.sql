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
  dep VARCHAR(8) NOT NULL,
  num SMALLINT NOT NULL,
  course_name VARCHAR(128) NULL,
  description VARCHAR(1024) NULL,
  course_year SMALLINT NULL,
  semester TINYINT NULL,
  date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_courses_dep_num (dep, num)
);

CREATE TABLE IF NOT EXISTS UserCourses (
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  course_id INT(11) NOT NULL,
  FOREIGN KEY fk_usercourses_userid (user_id) REFERENCES Users(id),
  FOREIGN KEY fk_usercourses_courseid (course_id) REFERENCES Courses(id)
);

CREATE TABLE IF NOT EXISTS CourseOffered (
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  course_id INT(11) NOT NULL,
  FOREIGN KEY fk_usercourses_userid (user_id) REFERENCES Users(id),
  FOREIGN KEY fk_usercourses_courseid (course_id) REFERENCES Courses(id)
);

CREATE TABLE IF NOT EXISTS Availability (
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  slot_num INT(11) NOT NULL,
  FOREIGN KEY fk_availability_userid (user_id) REFERENCES Users(id),
  INDEX idx_availability_userid_slotnum (user_id, slot_num)
);

CREATE TABLE IF NOT EXISTS Requests (
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  tutor_id INT(11) NOT NULL,
  tutee_id INT(11) NOT NULL,
  course_id INT(11) NOT NULL,
  req_status TINYINT NULL,
  date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY fk_requests_tutorid (tutor_id) REFERENCES Users(id),
  FOREIGN KEY fk_requests_tuteeid (tutee_id) REFERENCES Users(id),
  FOREIGN KEY fk_requests_courseid (course_id) REFERENCES Courses(id)
);

CREATE TABLE IF NOT EXISTS Notifications (
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  req_id INT(11) NOT NULL,
  receiver_id INT(11) NOT NULL,
  receiver_is_tutor BIT NOT NULL,
  pushed BIT  DEFAULT 0,
  date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY fk_notifications_reqid (req_id) REFERENCES Requests(id),
  FOREIGN KEY fk_notifications_receiverid (receiver_id) REFERENCES Users(id)
);

CREATE TABLE IF NOT EXISTS Ratings (
  id INT(11) PRIMARY KEY AUTO_INCREMENT,
  tutor_id INT(11) NOT NULL,
  tutee_id INT(11) NOT NULL,
  rating TINYINT NOT NULL,
  review VARCHAR(1024) NULL,
  FOREIGN KEY fk_requests_tutorid (tutor_id) REFERENCES Users(id),
  FOREIGN KEY fk_requests_tuteeid (tutee_id) REFERENCES Users(id),
  INDEX idx_ratings_tutorid_rating (tutor_id, rating)
);
