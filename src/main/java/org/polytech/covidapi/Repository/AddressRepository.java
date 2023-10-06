package org.polytech.covidapi.Repository;

import java.util.Optional;

import org.polytech.covidapi.Domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    
    Optional<Address> findById(Integer addressId);
}
