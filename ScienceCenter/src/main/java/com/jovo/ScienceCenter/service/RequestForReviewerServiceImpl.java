package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.RequestForReviewerDTO;
import com.jovo.ScienceCenter.exception.NotFoundException;
import com.jovo.ScienceCenter.model.RequestForReviewer;
import com.jovo.ScienceCenter.model.ScientificArea;
import com.jovo.ScienceCenter.model.UserData;
import com.jovo.ScienceCenter.repository.RequestForReviewerRepository;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestForReviewerServiceImpl implements RequestForReviewerService {

    @Autowired
    private RequestForReviewerRepository requestForReviewerRepository;

    @Autowired
    private IdentityService identityService;


    @Override
    public RequestForReviewer getRequestForReviewer(Long id) {
        return requestForReviewerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("RequestForReviewer (id=".concat(""+id).concat(") not found!")));
    }

    @Override
    public void save(RequestForReviewer requestForReviewer) {
        requestForReviewerRepository.save(requestForReviewer);
    }

    @Override
    public void delete(RequestForReviewer requestForReviewer) {
        requestForReviewerRepository.delete(requestForReviewer);
    }

    @Override
    public List<RequestForReviewerDTO> getAllRequestsForReviewer() {
        return requestForReviewerRepository.findAll().stream()
                .map(r -> {
                    UserData userData = r.getUserData();
                    org.camunda.bpm.engine.identity.User user = identityService.createUserQuery()
                                                                    .userId(userData.getCamundaUserId()).singleResult();
                    if (user == null) {
                        return null;
                    }

                    List<String> scientificAreaNames = userData.getScientificAreas().stream()
                            .map(ScientificArea::getName).collect(Collectors.toList());
                    String scientificAreaNamesStr = String.join(", ", scientificAreaNames);

                    return new RequestForReviewerDTO(r.getId(), user.getId(), user.getFirstName(), user.getLastName(),
                                        scientificAreaNamesStr);
                })
                .filter(r -> r != null)
                .collect(Collectors.toList());
    }
}
