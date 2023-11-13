package org.polytech.covidapi.Services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.polytech.covidapi.Domain.Address;
import org.polytech.covidapi.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Address createAddress(Address address){
        return addressRepository.save(address);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Address updateAddress(Integer addressId, Address updatedAddress){
        Address existingAddress = addressRepository.findById(addressId)
            .orElseThrow(() -> new EntityNotFoundException("Address Not Found"));

        existingAddress.setCountry(updatedAddress.getCountry());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setStreet(updatedAddress.getStreet());
        existingAddress.setStreetNumber(updatedAddress.getStreetNumber());

        return addressRepository.save(existingAddress);
    }

    public Optional<Address> getById(Integer addressId){
        return addressRepository.findById(addressId);
    }
}
