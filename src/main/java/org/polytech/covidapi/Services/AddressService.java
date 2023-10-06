package org.polytech.covidapi.Services;

import org.polytech.covidapi.Domain.Address;
import org.polytech.covidapi.Repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    public Address createAddress(Address address){
        return addressRepository.save(address);
    }
}
