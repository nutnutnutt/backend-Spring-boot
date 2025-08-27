package com.itsci.project65.repository;

import com.itsci.project65.model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmerRepository extends JpaRepository<Farmer,Integer> {
    Farmer findByFarmerUserName(String farmerUserName);
}