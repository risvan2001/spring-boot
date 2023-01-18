package com.rs.jadwal;

import com.rs.product.Product;
import com.rs.product.ProductNotFoundException;
import com.rs.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jadwal")
public class JadwalController
{
    @Autowired
    private JadwalRepository jadwalRepository;

    @GetMapping("/list")
    public List<Jadwal> listAll() {
        return jadwalRepository.findAll();
    }

    @PostMapping("/create")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Jadwal> createNewJadwal(@RequestBody @Valid Jadwal newJadwalData)
    {
        Jadwal savedJadwal = jadwalRepository.save(newJadwalData);
        URI newJadwalURI = URI.create("/jadwal/"+savedJadwal.getId());
        return ResponseEntity.created(newJadwalURI).body(savedJadwal);
    }

    @GetMapping("/name/{name}")
    @RolesAllowed({"ROLE_ADMIN", "ROLE_CUSTOMER"})
    public ResponseEntity<Optional<Jadwal>> Name(@PathVariable("name") String name)
    {
        if (jadwalRepository.findByName(name).isPresent())
        {
            return ResponseEntity.ok(jadwalRepository.findByName(name));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Jadwal> updateJadwal(@PathVariable Integer id, @RequestBody Jadwal updatedJadwalData)
    {
        Jadwal jadwal = jadwalRepository.findById(id).orElseThrow(() -> new JadwalNotFoundException("Jadwal not exist with id: " +id));
        jadwal.setName(updatedJadwalData.getName());
        jadwal.setHari_praktik(updatedJadwalData.getHari_praktik());
        jadwal.setLokasi_praktik(updatedJadwalData.getLokasi_praktik());
        Jadwal savedJadwal = jadwalRepository.save(jadwal);
        URI newJadwalURI = URI.create("/jadwal/"+savedJadwal.getId());
        return ResponseEntity.created(newJadwalURI).body(savedJadwal);
    }
}
