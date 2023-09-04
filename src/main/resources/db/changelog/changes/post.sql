CREATE TABLE posts (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    twitter_post_content VARCHAR(1000) NOT NULL,
    twitter_user_id BIGINT,
    FOREIGN KEY (twitter_user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);
