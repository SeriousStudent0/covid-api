package org.polytech.covidapi.Web;

import javax.persistence.EntityNotFoundException;

import org.polytech.covidapi.Domain.Address;
import org.polytech.covidapi.Services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;
    
    @Autowired
    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Address> createAddress(@RequestBody Address address){
        Address createdAddress = addressService.createAddress(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(
            @PathVariable Integer id,
            @RequestBody Address updatedAddress) {
        try {
            Address updated = addressService.updateAddress(id, updatedAddress);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
}
