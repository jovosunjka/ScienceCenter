package com.jovo.ScienceCenter.task;


import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;


@Service
public class CheckPersonalInformationsTask implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("************ CheckPersonalInformationsTask_START ************");

        // Get a process variable
        String username = (String) delegateExecution.getVariable("username");
        String password = (String) delegateExecution.getVariable("password");
        String repeatPassword = (String) delegateExecution.getVariable("repeatPassword");
        String firstName = (String) delegateExecution.getVariable("firstName");
        String lastName = (String) delegateExecution.getVariable("lastName");
        String city = (String) delegateExecution.getVariable("city");
        String country = (String) delegateExecution.getVariable("country");
        String title = (String) delegateExecution.getVariable("title");
        String email = (String) delegateExecution.getVariable("email");
        String scientificAreas = (String) delegateExecution.getVariable("scientificAreas");
        // Boolean reviewer = (Boolean) delegateExecution.getVariable("reviewer");

        boolean validPersonalInformations = checkPersonalInformations(username, password, repeatPassword,
                firstName, lastName, city, country, title, email, scientificAreas);

        delegateExecution.setVariable("validPersonalInformations", validPersonalInformations);
        // TODO proveri da li dobro radi
        System.out.println("************ CheckPersonalInformationsTask_END ************");
    }

    private boolean checkPersonalInformations(String username, String password, String repeatPassword, String firstName,
                                                     String lastName, String city, String country, String title, String email,
                                                     String scientificAreas) {
        // System.out.println(StringUtils.isBlank(""));
        // System.out.println(StringUtils.isBlank("   "));
        // System.out.println(StringUtils.isBlank(null));

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(repeatPassword) || StringUtils.isBlank(firstName)
                || StringUtils.isBlank(lastName) || StringUtils.isBlank(city) || StringUtils.isBlank(country) || StringUtils.isBlank(title)
                || StringUtils.isBlank(email) || StringUtils.isBlank(scientificAreas)) {
            return false;
        }

        // TODO username exists?

        if (!password.equals(repeatPassword)) {
            return false;
        }

        // TODO mozda proveriti title (da li je pripada nekoj definisanoj listi titula)

        // TODO osnovna provera email adrese

        // TODO scientificAreas length >= 1

        return true;
    }
}