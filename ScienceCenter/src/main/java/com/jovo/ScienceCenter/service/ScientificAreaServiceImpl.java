package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.model.ScientificArea;
import com.jovo.ScienceCenter.repository.ScientificAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScientificAreaServiceImpl implements ScientificAreaService {

    @Autowired
    private ScientificAreaRepository scientificAreaRepository;

    @Override
    public List<ScientificArea> getAllScientificAreas() {
        return scientificAreaRepository.findAll();
    }

    @Override
    public List<ScientificArea> getScientificAreasByIds(List<Long> ids) {
        return scientificAreaRepository.findAllById(ids);
    }
}
