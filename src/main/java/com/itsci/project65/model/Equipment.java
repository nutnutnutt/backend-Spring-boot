package com.itsci.project65.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="equipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})

public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int equipmentId;

    @Column(name = "equipmentName", length = 100, nullable = false)
    private String equipmentName;

    @Column(name = "price", length = 10)
    private int price;

    @Column(name = "equipmentDetails", length = 1000)
    private String equipmentDetails;

    @Column(name = "equipmentStatus", length = 50)
    private String equipmentStatus;

    @Column(name = "equipmentFeature", length = 500)
    private String equipmentFeature;

    @Column(name = "equipmentAddress", length = 75)
    private String equipmentAddress;

    @Column(name = "equipmentImg", length = 255)
    private String equipmentImg;

    @Column(name = "viewsReviews", length = 255)
    private String viewsReviews;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private EquipmentOwner equipmentOwner;

    @ManyToMany(mappedBy = "equipmentList")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Booking> booking;

    @ManyToOne
    @JoinColumn(name = "equipment_type")
    @JsonBackReference
    private EquipmentType equipmentType;

}
