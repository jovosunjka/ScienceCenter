package com.jovo.ScienceCenter.task;


import com.jovo.ScienceCenter.exception.SaveEditorsAndReviewersInMagazineFailedException;
import com.jovo.ScienceCenter.exception.SaveReviewersForScientificPaperFailedException;
import com.jovo.ScienceCenter.service.MagazineService;
import com.jovo.ScienceCenter.service.ScientificPaperService;
import com.jovo.ScienceCenter.websockets.dto.WebSocketMessageDTO;
import com.jovo.ScienceCenter.websockets.messaging.Producer;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaveSelectedReviewersForScientificPaperTask implements JavaDelegate {

    @Autowired
    private ScientificPaperService scientificPaperService;

    @Autowired
    private Producer producer;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("SaveSelectedReviewersForScientificPaperTask_START");

            List<Long> reviewerIds = (List<Long>) delegateExecution.getVariable("reviewers");
            scientificPaperService.saveSelectedReviewersForScientificPaper(delegateExecution.getProcessInstanceId(), reviewerIds);

            System.out.println("SaveSelectedReviewersForScientificPaperTask_END");
        } catch (Exception e) {
            System.out.println("SaveSelectedReviewersForScientificPaperTask Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "save-reviewers-for-scientific-paper", e.getMessage());
            producer.sendMessage(message);
            throw new SaveReviewersForScientificPaperFailedException("Save reviewers for scientific paper");
        }

        message = new WebSocketMessageDTO(false, "save-editors-and-reviewers",
                "Reviewers for scientific paper are saved!");
        producer.sendMessage(message);
    }

}