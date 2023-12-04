package org.polytech.covidapi.Web;

import java.util.Base64;
import java.util.List;

import java.util.Map;

import org.polytech.covidapi.Domain.Doctor;
import org.polytech.covidapi.Domain.Patient;
import org.polytech.covidapi.Domain.RendezVous;
import org.polytech.covidapi.Services.DoctorServices;
import org.polytech.covidapi.Services.PatientServices;
import org.polytech.covidapi.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorServices doctorService;
    private final PatientServices patientService;
    
    @Autowired
    public DoctorController(DoctorServices doctorService, PatientServices patientService){
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @PostMapping(path = "private/create")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor){
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
    }

    @PutMapping(path = "private/create")
    public ResponseEntity<Doctor> modifyDoctor(@RequestBody Doctor doctor){
        Doctor modifiedDoctor = doctorService.updateDoctor(doctor);
        if (modifiedDoctor != null) {
            return ResponseEntity.status(HttpStatus.OK).body(modifiedDoctor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(path = "private/{doctorId}/patients")
    public ResponseEntity<List<Patient>> getPatientsForDoctorId(@PathVariable Integer doctorId){
        List<Patient> patients = patientService.getPatientsByDoctorId(doctorId);
        return ResponseEntity.ok(patients);
    }

    @GetMapping(path = "private/{doctorId}/patients/search")
    public List<Patient> getAllPatientByName(
        @RequestParam(name = "name", required = true) String name){
            return patientService.getAllPatientByName(name);
    }

    @PostMapping(path = "public/logging")
    public ResponseEntity<Integer> logAs(@RequestHeader HttpHeaders headers) {
        // Extract the Authorization header
        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Digest base64(")) {
            // Extract the base64 credentials from the Authorization header
            String base64Credentials = authHeader.substring("Digest base64(".length(), authHeader.length() - 1);
            String decodedCredentials = new String(Base64.getDecoder().decode(base64Credentials));
            
            String[] credentials = decodedCredentials.split(":", 2);
            String login = credentials[0];
            String password = credentials[1];

            Integer userId = doctorService.authenticate(login, password);

            if (userId != null) {
                return ResponseEntity.status(HttpStatus.OK).body(userId);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping(path = "private/logging")
    public ResponseEntity<Boolean> loggout(@RequestBody Integer userId) {
        boolean logResult = doctorService.loggout(userId);
        
        if (logResult) {
            // Successfully logged out
            return ResponseEntity.ok(true);
        } else {
            // Doctor not found
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "private/superadmins")
    public ResponseEntity<List<Doctor>> getSuperAdmins(){
        return ResponseEntity.ok(doctorService.getSuperAdminList());
    }

    @GetMapping(path = "private")
    public ResponseEntity<Doctor> getUser(@RequestParam(name = "id", required = true) Integer id) {
        Doctor doctor = doctorService.getDoctor(id);
        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "private/delete")
    public ResponseEntity<Void> deleteDoctor(@RequestBody Doctor doctor) {
        boolean deleted = doctorService.deleteDoctor(doctor);

        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            // Handle the case where the doctor wasn't found or couldn't be deleted
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(path = "private/addRDV")
    public ResponseEntity<Doctor> addRDVToRDVList(@RequestBody Map<String, Object> requestBody) {
        Integer userId = (Integer) requestBody.get("doctorId");
        Integer rdvId = (Integer) requestBody.get("rdvId");

        Doctor updatedDoctor = doctorService.addRDV(userId, rdvId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDoctor);
    }
}
