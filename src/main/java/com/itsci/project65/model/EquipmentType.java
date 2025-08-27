package com.itsci.project65.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="equipment_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})

public class EquipmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int equipmentTypeId;

    @Column(name = "equipmentTypeName", length = 100, nullable = false)
    private String equipmentTypeName;


    @JsonIgnore
    @OneToMany(mappedBy = "equipmentType", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Equipment> equipment;
}
