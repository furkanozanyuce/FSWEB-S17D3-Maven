package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> getAllKangaroos() {
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Kangaroo findById(@PathVariable Integer id) {
        if(id <= 0) {
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if(!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given Id does not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping
    public Kangaroo createKangaroo(@RequestBody Kangaroo newKangaroo) {
        if (newKangaroo.getId() == null || newKangaroo.getId() <= 0) {
            throw new ZooException("Id must be greater than 0 and must be provided", HttpStatus.BAD_REQUEST);
        }
        if (kangaroos.containsKey(newKangaroo.getId())) {
            throw new ZooException("Kangaroo with given Id already exists: " + newKangaroo.getId(), HttpStatus.CONFLICT);
        }
        kangaroos.put(newKangaroo.getId(), newKangaroo);
        return newKangaroo;
    }

    @PutMapping("/{id}")
    public Kangaroo updateKangaroo(@PathVariable Integer id, @RequestBody Kangaroo updatedKangaroo) {
        if (id <= 0) {
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given Id does not exist: " + id, HttpStatus.NOT_FOUND);
        }
        updatedKangaroo.setId(id);
        kangaroos.put(id, updatedKangaroo);
        return updatedKangaroo;
    }

    @DeleteMapping("/{id}")
    public Kangaroo deleteKangaroo(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with given Id does not exist: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.remove(id);
    }

}
