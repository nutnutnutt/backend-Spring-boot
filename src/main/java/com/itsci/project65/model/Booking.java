package com.itsci.project65.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    private String bookingstartDate;

    private String bookingendDate;

    @Column(name = "bookingchangeAddress", length = 255)
    private String bookingchangeAddress;

    @Column(name = "bookingstatus", length = 50)
    private String bookingstatus;

    @Column(name = "bookingList", length = 255)
    private String bookingList;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private Farmer farmer;

    @OneToOne(mappedBy = "booking")
    private Delivery delivery;

    @OneToOne(mappedBy = "booking")
    private Review review;

    @ManyToMany
    @JoinTable(
            name = "booking_equipment",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private List<Equipment> equipmentList;

}
