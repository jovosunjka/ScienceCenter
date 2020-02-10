package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.model.ScientificArea;

import java.util.List;

public interface ScientificAreaService {

    List<ScientificArea> getAllScientificAreas();

    List<ScientificArea> getScientificAreasByIds(List<Long> ids);

    ScientificArea getScientificArea(Long id);
}
