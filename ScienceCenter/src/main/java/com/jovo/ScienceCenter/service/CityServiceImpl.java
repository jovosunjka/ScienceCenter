package com.jovo.ScienceCenter.service;


import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.model.City;
import com.jovo.ScienceCenter.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;


    @Override
    public City getCity(String name) {
        return cityRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("City (name=".concat(name).concat(") not found!")));
    }

    @Override
    public List<String> getCities() {
        return cityRepository.findAll().stream()
                .map(c -> c.getName())
                .collect(Collectors.toList());
    }

}
