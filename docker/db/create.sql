CREATE TABLE IF NOT EXISTS cache (
    query TEXT PRIMARY KEY,
    result TEXT
);

CREATE TABLE IF NOT EXISTS user_report (
    id serial PRIMARY KEY,
    username TEXT NOT NULL,
    report TEXT NOT NULL
);