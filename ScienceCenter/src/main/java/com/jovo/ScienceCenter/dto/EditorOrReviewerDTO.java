package com.jovo.ScienceCenter.dto;

public class EditorOrReviewerDTO {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String scientificAreas;

    public EditorOrReviewerDTO() {

    }

    public EditorOrReviewerDTO(long id, String username, String firstName, String lastName,
                               String scientificAreas) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.scientificAreas = scientificAreas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getScientificAreas() {
        return scientificAreas;
    }

    public void setScientificAreas(String scientificAreas) {
        this.scientificAreas = scientificAreas;
    }
}
