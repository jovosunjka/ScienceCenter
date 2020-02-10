package com.jovo.ScienceCenter.listener;

import com.jovo.ScienceCenter.model.MainEditorAndScientificPaper;
import com.jovo.ScienceCenter.model.ScientificPaper;
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
public class SendEmailNotificationToRepairScientificPaperStartListener implements ExecutionListener {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private UserService userService;

	private ClassPathResource repairScientificPaper = new ClassPathResource("email_templates/repair_scientific_paper.html");

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("SendEmailNotificationToRepairScientificPaperStartListener_START");

		String processInstanceId = execution.getProcessInstanceId();
		String processInitiator = (String) runtimeService.getVariable(processInstanceId, "processInitiator");
		String emailaddress = userService.getUser(processInitiator).getEmail();

		MainEditorAndScientificPaper mainEditorAndScientificPaper =
				(MainEditorAndScientificPaper) runtimeService.getVariable(processInstanceId, "mainEditorAndScientificPaper");
		ScientificPaper scientificPaper = mainEditorAndScientificPaper.getScientificPaper();

		String commentForScientificPaper = (String) runtimeService.getVariable(processInstanceId, "commentForScientificPaper");

		String contentStr = FileUtil.loadFileContent(repairScientificPaper.getFile().getPath());
		contentStr = String.format(contentStr, processInitiator, scientificPaper.getTitle(), commentForScientificPaper);
		String[] contentTokens = contentStr.split("=====");
		String subject = contentTokens[0].trim();
		String message = contentTokens[1].trim();
		Email currentEmail = new Email(emailaddress, subject, message);
		runtimeService.setVariable(processInstanceId, "currentEmail", currentEmail);

		System.out.println("SendEmailNotificationToRepairScientificPaperStartListener_END");
	}
}
