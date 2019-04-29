create index on tweets using btree(user_id);

create index on followers using btree(follow_by);

create index on followers using btree(follow_by, follow_to);

create index on tweet_likes using btree (tweet_id);

create index on tweet_likes using btree(user_id);

create index on tweet_likes using btree(tweet_id,user_id);