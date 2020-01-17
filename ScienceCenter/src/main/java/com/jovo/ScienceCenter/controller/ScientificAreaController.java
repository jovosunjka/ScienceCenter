package com.jovo.ScienceCenter.controller;


import com.jovo.ScienceCenter.model.ScientificArea;
import com.jovo.ScienceCenter.service.ScientificAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/scientific-areas")
public class ScientificAreaController {

    @Autowired
    private ScientificAreaService scientificAreaService;

    @RequestMapping(value ="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ScientificArea>> getAllScientificAreas() {
        List<ScientificArea> scientificAreas = scientificAreaService.getAllScientificAreas();
        return new ResponseEntity<List<ScientificArea>>(scientificAreas, HttpStatus.OK);
    }
}
