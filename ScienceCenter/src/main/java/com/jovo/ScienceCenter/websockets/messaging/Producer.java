package com.jovo.ScienceCenter.websockets.messaging;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/*
 * Izdvojena komponenta koja sadrzi atribut SimpMessagingTemplate
 * cije metode vrse slanje poruka sa servera na pretplacene klijente.
 */
@Component
public class Producer {


	/*
	 * Implementira SimpMessagesSendingOperations klasu koja sadrzi metode za slanje poruka korisnicima.
	 */
	@Autowired
	private SimpMessagingTemplate template;


	public void sendMessage(Object message) {
		this.template.convertAndSend("/topic", message);
	}

}
