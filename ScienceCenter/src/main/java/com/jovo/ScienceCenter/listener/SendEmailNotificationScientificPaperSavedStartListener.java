package com.jovo.ScienceCenter.listener;

import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.MainEditorAndScientificPaper;
import com.jovo.ScienceCenter.model.ScientificPaper;
import com.jovo.ScienceCenter.service.UserService;
import com.jovo.ScienceCenter.util.Email;
import com.jovo.ScienceCenter.util.FileUtil;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.SendTask;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SendEmailNotificationScientificPaperSavedStartListener implements ExecutionListener {

	@Autowired
	private RuntimeService runtimeService;

	private ClassPathResource scientificPaperSaved = new ClassPathResource("email_templates/scientific_paper_saved.html");

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("SendEmailNotificationScientificPaperSavedStartListener_START");

		String processInstanceId = execution.getProcessInstanceId();
		String processInitiator = (String) runtimeService.getVariable(processInstanceId, "processInitiator");

		MainEditorAndScientificPaper mainEditorAndScientificPaper =
			(MainEditorAndScientificPaper) runtimeService.getVariable(processInstanceId, "mainEditorAndScientificPaper");
		ScientificPaper scientificPaper = mainEditorAndScientificPaper.getScientificPaper();

		String contentStr = FileUtil.loadFileContent(scientificPaperSaved.getFile().getPath());
		contentStr = String.format(contentStr, processInitiator, scientificPaper.getTitle(),
									scientificPaper.getScientificArea().getName());
		String[] contentTokens = contentStr.split("=====");
		String subject = contentTokens[0].trim();
		String message = contentTokens[1].trim();
		Email currentEmail = new Email(null, subject, message);
		runtimeService.setVariable(processInstanceId, "currentEmail", currentEmail);

		System.out.println("SendEmailNotificationScientificPaperSavedStartListener_END");
	}
}
