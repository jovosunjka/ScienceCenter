package com.jovo.ScienceCenter.task;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.jovo.ScienceCenter.dto.CoauthorDTO;
import com.jovo.ScienceCenter.exception.SaveNewMagazineFailedException;
import com.jovo.ScienceCenter.exception.SaveNewScientificPaperFailedException;
import com.jovo.ScienceCenter.model.Currency;
import com.jovo.ScienceCenter.model.PayerType;
import com.jovo.ScienceCenter.model.StatusOfScientificPaper;
import com.jovo.ScienceCenter.model.UserData;
import com.jovo.ScienceCenter.service.MagazineService;
import com.jovo.ScienceCenter.service.ScientificPaperService;
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
public class SaveNewScientificPaperTask implements JavaDelegate {

    @Autowired
    private ScientificPaperService scientificPaperService;

    @Autowired
    private UserService userService;

    @Autowired
    private Producer producer;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object message;

        try {
            System.out.println("SaveNewScientificPaperTask_START");

            String scientificPaperStatusStr = (String) delegateExecution.getVariable("scientificPaperStatus");

            if (scientificPaperStatusStr == null) {
                UserData author = userService.getLoggedUser();

                String title = (String) delegateExecution.getVariable("title");
                String coauthors = (String) delegateExecution.getVariable("coauthors");

                List<CoauthorDTO> coauthorDTOs = convertStringToListCoauthorDTOs(coauthors);

                String keywords = (String) delegateExecution.getVariable("keywords");
                String scientificPaperAbstract = (String) delegateExecution.getVariable("abstract");

                Long selectedScientificAreaId = (Long) delegateExecution.getVariable("selectedScientificAreaId");
                String fileName = (String) delegateExecution.getVariable("fileName");

                scientificPaperService.saveNewScientificPaper(delegateExecution.getProcessInstanceId(), author, title,
                        coauthorDTOs, keywords, scientificPaperAbstract, selectedScientificAreaId, fileName);
            }
            else {
                String repairedFileName = (String) delegateExecution.getVariable("repairedFileName");
                scientificPaperService.repairScientificPaper(delegateExecution.getProcessInstanceId(), repairedFileName);
            }

            System.out.println("SaveNewScientificPaperTask_END");
        } catch (Exception e) {
            System.out.println("SaveNewScientificPaperTask Failed");
            //e.printStackTrace();
            message = new WebSocketMessageDTO(true, "save-new-scientific-paper", e.getMessage());
            producer.sendMessage(message);
            throw new SaveNewScientificPaperFailedException("Save new scientific paper failed");
        }

        message = new WebSocketMessageDTO(false, "save-new-scientific-paper",
                "The new scientific paper is saved!");
        producer.sendMessage(message);
    }

    private List<CoauthorDTO> convertStringToListCoauthorDTOs(String coauthors) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, CoauthorDTO.class);
        List<CoauthorDTO> coauthorDTOs = mapper.readValue(coauthors, listType);
        return coauthorDTOs;

        // https://stackoverflow.com/questions/28821715/java-lang-classcastexception-java-util-linkedhashmap-cannot-be-cast-to-com-test
        /*
        public <T> List<T> jsonArrayToObjectList(String json, Class<T> tClass) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass);
            List<T> ts = mapper.readValue(json, listType);
            LOGGER.debug("class name: {}", ts.get(0).getClass().getName());
            return ts;
        }
        */
    }

}