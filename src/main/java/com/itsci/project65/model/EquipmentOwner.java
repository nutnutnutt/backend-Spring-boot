package com.itsci.project65.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="equipment_owner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class EquipmentOwner implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ownerId", length = 20)
    private int ownerId;

    @Column(name = "ownerUserName", length = 20, nullable = false)
    private String ownerUserName;

    @Column(name = "ownerPassword", length = 16, nullable = false)
    private String ownerPassword;

    @Column(name = "ownerCFPassword", length = 100, nullable = false)
    private String ownerCFPassword;

    @Column(name = "ownerEmail", length = 60, nullable = false)
    private String ownerEmail;

    @Column(name = "ownerFName", length = 50, nullable = false)
    private String ownerFName;

    @Column(name = "ownerLName", length = 50, nullable = false)
    private String ownerLName;

    @Column(name = "ownerGender", nullable = false)
    private String ownerGender;

    private String ownerDOB;

    @Column(name = "ownerTel", length = 10, nullable = false)
    private String ownerTel;

    @Column(name = "ownerHouseNumber", length = 30, nullable = false)
    private String ownerHouseNumber;

    @Column(name = "ownerAlley", length = 20, nullable = false)
    private String ownerAlley;

    @Column(name = "ownerMoo", length = 20, nullable = false)
    private String ownerMoo;

    @Column(name = "ownerSubDistrict", length = 30, nullable = false)
    private String ownerSubDistrict;

    @Column(name = "ownerDistrict", length = 30, nullable = false)
    private String ownerDistrict;

    @Column(name = "ownerProvince", length = 30, nullable = false)
    private String ownerProvince;

    @Column(name = "ownerPostalCode", length = 5, nullable = false)
    private String ownerPostalCode;

    @Column(name = "ownerImg", length = 255, nullable = true)
    private String ownerImg;

    @OneToMany(mappedBy = "equipmentOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Equipment> equipments;

    // UserDetails methods
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_OWNER"));
    }

    @Override
    public String getPassword() {
        return ownerPassword;
    }

    @Override
    public String getUsername() {
        return ownerUserName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
