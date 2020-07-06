package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.model.City;

import java.util.List;

public interface CityService {

    City getCity(String name);

    List<String> getCities();
}
