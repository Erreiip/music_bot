Drop table IF EXISTS User_report    Cascade;
Drop table IF EXISTS Playlists      Cascade;
Drop table IF EXISTS Playlist_songs Cascade;


CREATE TABLE IF NOT EXISTS User_report (

    id serial,
    username TEXT NOT NULL,
    report TEXT NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Playlists (

    guild_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    rights TEXT NOT NULL,

    PRIMARY KEY (guild_id, name)
);

CREATE TABLE IF NOT EXISTS Tracks (

    id serial,
    playlist_guild_id BIGINT NOT NULL,
    playlist_name VARCHAR(255) NOT NULL,
    title TEXT NOT NULL,
    uri TEXT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (playlist_guild_id, playlist_name) REFERENCES Playlists(guild_id, name) ON DELETE CASCADE
);