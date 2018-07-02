CREATE TABLE IF NOT EXISTS recipes (
  id SERIAL PRIMARY KEY,
  title varchar(100) NOT NULL,
  making_time varchar(100) NOT NULL,
  serves varchar(100)NOT NULL,
  ingredients varchar(300) NOT NULL,
  cost integer NOT NULL,
  created_at timestamp default CURRENT_TIMESTAMP,
  updated_at timestamp default CURRENT_TIMESTAMP
);
