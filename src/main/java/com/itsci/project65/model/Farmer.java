package com.itsci.project65.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name="farmers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})

public class Farmer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farmerId", length = 20)
    private int farmerId;


    @Column(name = "farmerUserName", length = 20, nullable = false)
    private String farmerUserName;


    @Column(name = "farmerPassword", length = 16, nullable = false)
    private String farmerPassword;


    @Column(name = "farmerCFPassword", length = 100, nullable = false)
    private String farmerCFPassword;


    @Column(name = "farmerEmail", length = 60, nullable = false)
    private String farmerEmail;


    @Column(name = "farmerFName", length = 50, nullable = false)
    private String farmerFName;


    @Column(name = "farmerLName", length = 50, nullable = false)
    private String farmerLName;


    @Column(name = "farmerGender", length = 10, nullable = false)
    private String farmerGender;

    private String farmerDOB;


    @Column(name = "farmerTel", length = 10, nullable = false)
    private String farmerTel;


    @Column(name = "farmerHouseNumber", length = 30, nullable = false)
    private String farmerHouseNumber;


    @Column(name = "farmerAlley", length = 20, nullable = false)
    private String farmerAlley;


    @Column(name = "farmerMoo", length = 20, nullable = false)
    private String farmerMoo;

    @Column(name = "farmerSubDistrict", length = 30, nullable = false)
    private String farmerSubDistrict;


    @Column(name = "farmersDistrict", length = 30, nullable = false)
    private String farmerDistrict;

    @Column(name = "farmerProvince", length = 30, nullable = false)
    private String farmerProvince;

    @Column(name = "farmerPostalCode", length = 5, nullable = false)
    private String farmerPostalCode;

    @Column(name = "farmerImg", length = 255, nullable = false)
    private String farmerImg;

    @JsonIgnore
    @OneToMany(mappedBy = "farmer" ,cascade = CascadeType.ALL)
    private List<Address> address;

    // UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_FARMER"));
    }

    @Override
    public String getPassword() {
        return farmerPassword;
    }

    @Override
    public String getUsername() {
        return farmerUserName;
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
