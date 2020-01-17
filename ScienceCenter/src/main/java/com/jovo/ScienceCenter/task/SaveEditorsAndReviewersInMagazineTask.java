package com.jovo.ScienceCenter.task;


import com.jovo.ScienceCenter.exception.SaveEditorsAndReviewersInMagazineFailedException;
import com.jovo.ScienceCenter.service.MagazineService;
import com.jovo.ScienceCenter.service.UserService;
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
public class SaveEditorsAndReviewersInMagazineTask implements JavaDelegate {

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private Producer producer;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("SaveEditorsAndReviewersInMagazineTask_START");

            String editors = (String) delegateExecution.getVariable("editors");
            List<Long> editorIds;
            if (editors.equals("")) {
                editorIds = new ArrayList<Long>();
            }
            else {
                editorIds = Arrays.stream(editors.split(","))
                        .map(idStr -> Long.parseLong(idStr))
                        .collect(Collectors.toList());
            }

            String reviewers = (String) delegateExecution.getVariable("reviewers");
            List<Long> reviewerIds;
            if (reviewers.equals("")) {
                reviewerIds = new ArrayList<Long>();
            }
            else {
                reviewerIds = Arrays.stream(reviewers.split(","))
                        .map(idStr -> Long.parseLong(idStr))
                        .collect(Collectors.toList());
            }

            String name = (String) delegateExecution.getVariable("name");

            magazineService.saveEditorsAndReviewersInMagazine(name, editorIds, reviewerIds);

            System.out.println("SaveEditorsAndReviewersInMagazineTask_END");
        } catch (Exception e) {
            System.out.println("SaveEditorsAndReviewersInMagazineTask Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "save-editors-and-reviewers", e.getMessage());
            producer.sendMessage(message);
            throw new SaveEditorsAndReviewersInMagazineFailedException("Save editors and reviewers in magazine failed");
        }

        message = new WebSocketMessageDTO(false, "save-editors-and-reviewers",
                "Editors and reviewers are saved in magazine!");
        producer.sendMessage(message);
    }

}