package com.company.explorecalifornia.domain;

import jakarta.persistence.*;
import java.util.Objects;

/**
 *
 * The class {@code TourRating} represents the tour rating entity.
 *
 */
@Entity
@Table(name = "tour_rating")
public class TourRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_rating_id")
    private Long id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public TourRating() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourRating that = (TourRating) o;
        return Objects.equals(id, that.id) && Objects.equals(score, that.score)
                && Objects.equals(comment, that.comment) && Objects.equals(tour, that.tour)
                && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, comment, tour, user);
    }

    @Override
    public String toString() {
        return "TourRating{" +
                "id=" + id +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                ", tour=" + tour +
                ", user=" + user +
                '}';
    }
}