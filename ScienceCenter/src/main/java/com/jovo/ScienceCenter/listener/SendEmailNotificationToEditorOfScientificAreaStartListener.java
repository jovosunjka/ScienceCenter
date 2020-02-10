package com.jovo.ScienceCenter.listener;

import com.jovo.ScienceCenter.model.MainEditorAndScientificPaper;
import com.jovo.ScienceCenter.model.ScientificPaper;
import com.jovo.ScienceCenter.model.UserData;
import com.jovo.ScienceCenter.service.UserService;
import com.jovo.ScienceCenter.util.Email;
import com.jovo.ScienceCenter.util.FileUtil;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class SendEmailNotificationToEditorOfScientificAreaStartListener implements ExecutionListener {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private UserService userService;

	private ClassPathResource editorOfScientificAreaForSomePaper = new ClassPathResource("email_templates/editor_of_scientific_area_for_some_paper.html");

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("SendEmailNotificationToEditorOfScientificAreaStartListener_START");

		String processInstanceId = execution.getProcessInstanceId();
		String processInitiator = (String) runtimeService.getVariable(processInstanceId, "processInitiator");

		UserData selectedEditorOfScientificArea =
				(UserData) runtimeService.getVariable(processInstanceId, "selectedEditorOfScientificArea");
		String emailAddress = userService.getUser(selectedEditorOfScientificArea.getCamundaUserId()).getEmail();

		MainEditorAndScientificPaper mainEditorAndScientificPaper =
				(MainEditorAndScientificPaper) runtimeService.getVariable(processInstanceId, "mainEditorAndScientificPaper");
		ScientificPaper scientificPaper = mainEditorAndScientificPaper.getScientificPaper();

		String commentForScientificPaper = (String) runtimeService.getVariable(processInstanceId, "commentForScientificPaper");

		String contentStr = FileUtil.loadFileContent(editorOfScientificAreaForSomePaper.getFile().getPath());
		contentStr = String.format(contentStr, selectedEditorOfScientificArea.getCamundaUserId(), scientificPaper.getTitle(),
				scientificPaper.getScientificArea().getName(), processInitiator);
		String[] contentTokens = contentStr.split("=====");
		String subject = contentTokens[0].trim();
		String message = contentTokens[1].trim();
		Email currentEmail = new Email(emailAddress, subject, message);
		runtimeService.setVariable(processInstanceId, "currentEmail", currentEmail);

		System.out.println("SendEmailNotificationToEditorOfScientificAreaStartListener_END");
	}
}
