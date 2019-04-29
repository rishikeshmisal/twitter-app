package com.twitter.twitterapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "followers")
public class Followers {

    public User getFollowTo() {
        return followTo;
    }

    public void setFollowTo(User followTo) {
        this.followTo = followTo;
    }

    public User getFollowBy() {
        return followBy;
    }

    public void setFollowBy(User followBy) {
        this.followBy = followBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "follow_to",referencedColumnName = "id")
    private User followTo;

    @OneToOne
    @JoinColumn(name = "follow_by",referencedColumnName = "id")
    private User followBy;
}
