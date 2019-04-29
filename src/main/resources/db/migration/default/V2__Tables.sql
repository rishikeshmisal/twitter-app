create table tweets(
id serial,
tweet text,
created_on timestamptz default current_timestamp,
user_id int,
foreign key(user_id) references users(id),
primary key(id));

create table followers (
id serial,
follow_to int,
follow_by int,
primary key(id),
foreign key(follow_to) references users(id),
foreign key(follow_by) references users(id)
);

create table tweet_likes (
id serial,
tweet_id bigint,
user_id bigint,
foreign key(tweet_id) references tweets(id),
foreign key(user_id) references users(id),
primary key(id)
)