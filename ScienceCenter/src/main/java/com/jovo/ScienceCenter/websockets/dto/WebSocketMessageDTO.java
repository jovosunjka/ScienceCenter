package com.jovo.ScienceCenter.websockets.dto;

public class WebSocketMessageDTO {
    private boolean hasError;
    private String type;
    private String text;

    public WebSocketMessageDTO() {

    }

    public WebSocketMessageDTO(boolean hasError, String type, String text) {
        this.hasError = hasError;
        this.type = type;
        this.text = text;
    }

    public boolean getHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
