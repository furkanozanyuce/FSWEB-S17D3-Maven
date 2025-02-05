package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    Map<Integer, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> getAllKoalas() {
        return koalas.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Koala findById(@PathVariable Integer id) {
        if(id <= 0) {
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if(!koalas.containsKey(id)) {
            throw new ZooException("Koala with given Id does not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return koalas.get(id);
    }

    @PostMapping
    public Koala createKoala(@RequestBody Koala newKoala) {
        if (newKoala.getId() == null || newKoala.getId() <= 0) {
            throw new ZooException("Id must be greater than 0 and must be provided", HttpStatus.BAD_REQUEST);
        }
        if (koalas.containsKey(newKoala.getId())) {
            throw new ZooException("Koala with given Id already exists: " + newKoala.getId(), HttpStatus.CONFLICT);
        }
        koalas.put(newKoala.getId(), newKoala);
        return newKoala;
    }

    @PutMapping("/{id}")
    public Koala updateKoala(@PathVariable Integer id, @RequestBody Koala updatedKoala) {
        if (id <= 0) {
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with given Id does not exist: " + id, HttpStatus.NOT_FOUND);
        }
        updatedKoala.setId(id);
        koalas.put(id, updatedKoala);
        return updatedKoala;
    }

    @DeleteMapping("/{id}")
    public void deleteKoala(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with given Id does not exist: " + id, HttpStatus.NOT_FOUND);
        }
        koalas.remove(id);
    }

}
