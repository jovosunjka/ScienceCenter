package com.jovo.ScienceCenter.task.external;

//import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.client.ExternalTaskClient;

import java.util.HashMap;
import java.util.Map;

public class CheckPersonalInformationsExternalTask {

    private static String BASE_URL = "http://localhost:8080/engine-rest";

    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(BASE_URL)
                .asyncResponseTimeout(10000) // long polling timeout
                .build();

        // subscribe to an external task topic as specified in the process
        client.subscribe("check-personal-informations")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here

                    System.out.println("************ CheckPersonalInformationsExternalTask_START ************");

                    // Get a process variable
                    String username = (String) externalTask.getVariable("username");
                    String password = (String) externalTask.getVariable("password");
                    String repeatPassword = (String) externalTask.getVariable("repeatPassword");
                    String firstName = (String) externalTask.getVariable("firstName");
                    String lastName = (String) externalTask.getVariable("lastName");
                    String city = (String) externalTask.getVariable("city");
                    String country = (String) externalTask.getVariable("country");
                    String title = (String) externalTask.getVariable("title");
                    String email = (String) externalTask.getVariable("email");
                    String scientificAreas = (String) externalTask.getVariable("scientificAreas");
                    // Boolean reviewer = (Boolean) externalTask.getVariable("reviewer");

                    boolean validPersonalInformations = checkPersonalInformations(username, password, repeatPassword,
                                                            firstName, lastName, city, country, title, email, scientificAreas);

                    // Complete the task
                    Map<String, Object> mapProcessVariables = new HashMap<String, Object>();
                    mapProcessVariables.put("validPersonalInformations", validPersonalInformations);
                    // externalTaskService.complete(externalTask);
                    externalTaskService.complete(externalTask, mapProcessVariables);
                    // zavrsavamo ovaj task i updatujemo porcesnu varijablu validPersonalInformations

                    System.out.println("************ CheckPersonalInformationsExternalTask_END ************");
                })
                .open();
    }

    private static boolean checkPersonalInformations(String username, String password, String repeatPassword, String firstName,
                                                     String lastName, String city, String country, String title, String email,
                                                     String scientificAreas) {
        // System.out.println(StringUtils.isBlank(""));
        // System.out.println(StringUtils.isBlank("   "));
        // System.out.println(StringUtils.isBlank(null));

        /*if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(repeatPassword) || StringUtils.isBlank(firstName)
            || StringUtils.isBlank(lastName) || StringUtils.isBlank(city) || StringUtils.isBlank(country) || StringUtils.isBlank(title)
            || StringUtils.isBlank(email) || StringUtils.isBlank(scientificAreas)) {
            return false;
        }*/

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