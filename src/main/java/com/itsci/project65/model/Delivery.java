package com.itsci.project65.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="delivery")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})

public class Delivery {
    @Id
    private int deliveryId;

    @Column(name = "deliveryDetail", length = 255, nullable = false)
    private String deliveryDetail;

    @Column(name = "updateStatus", length = 50)
    private String updateStatus;

    private String deliveryPickUpDate;

    @Column(name = "returnStatus", length = 50)
    private String returnStatus;

    @OneToOne
    @JoinColumn(name = "booking_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
}
