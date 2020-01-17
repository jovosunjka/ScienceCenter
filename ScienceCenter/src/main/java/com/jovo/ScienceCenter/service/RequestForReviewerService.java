package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.RequestForReviewerDTO;
import com.jovo.ScienceCenter.model.RequestForReviewer;

import java.util.List;

public interface RequestForReviewerService {

    RequestForReviewer getRequestForReviewer(Long id);

    void save(RequestForReviewer requestForReviewer);

    void delete(RequestForReviewer requestForReviewer);

    List<RequestForReviewerDTO> getAllRequestsForReviewer();
}
