package com.jovo.ScienceCenter.listener;

import com.jovo.ScienceCenter.model.Magazine;
import com.jovo.ScienceCenter.model.MainEditorAndScientificPaper;
import com.jovo.ScienceCenter.model.UserData;
import com.jovo.ScienceCenter.service.UserService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaveNewScientificPaperEndListener implements ExecutionListener {
	@Autowired
	private UserService userService;

	@Autowired
	private RuntimeService runtimeService;
	
	@Override
	public void notify(DelegateExecution execution) {
		System.out.println("SaveNewScientificPaperEndListener_START");

		String processInstnaceId = execution.getProcessInstanceId();

		List<String> emailsForSendingNotifications = new ArrayList<String>();

		String processInitiator = (String) runtimeService.getVariable(processInstnaceId, "processInitiator");
		String processInitiatorEmail = userService.getUser(processInitiator).getEmail();
		emailsForSendingNotifications.add(processInitiatorEmail);

		MainEditorAndScientificPaper mainEditorAndScientificPaper =
			(MainEditorAndScientificPaper) runtimeService.getVariable(processInstnaceId, "mainEditorAndScientificPaper");
		String mainEditorEmail =
				userService.getUser(mainEditorAndScientificPaper.getMainEditor().getCamundaUserId()).getEmail();
		emailsForSendingNotifications.add(mainEditorEmail);

		runtimeService.setVariable(processInstnaceId, "emailsForSendingNotifications", emailsForSendingNotifications);

		System.out.println("SaveNewScientificPaperEndListener_END");
	}
}
