package com.itsci.project65.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="review")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})

public class Review {
    @Id
    private int reviewId;
    private int reviewScore;
    private int rating;

    @Column(name = "comment", length = 255)
    private String comment;

    @OneToOne
    @JoinColumn(name = "booking_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Booking booking;
}
