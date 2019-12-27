package com.jovo.ScienceCenter.controller;

import com.jovo.ScienceCenter.dto.DTO;
import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.service.MagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/magazines")
public class MagazineController {

    @Autowired
    private MagazineService magazineService;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DTO>> getMagazines() {
        List<Magazine> magazines = magazineService.getAllMagazines();
        List<DTO> magazineDTOs = magazines.stream()
                                        .map(m -> new DTO(m.getId(), m.getName()))
                                        .collect(Collectors.toList());
        return new ResponseEntity<List<DTO>>(magazineDTOs, HttpStatus.OK);
    }
}
