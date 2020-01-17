package com.jovo.ScienceCenter.dto;

public class ConfirmReviewerStatusDTO {
    private Long confirmId;
    private boolean confirmed;

    public ConfirmReviewerStatusDTO() {

    }

    public ConfirmReviewerStatusDTO(Long confirmId, boolean confirmed) {
        this.confirmId = confirmId;
        this.confirmed = confirmed;
    }

    public Long getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(Long confirmId) {
        this.confirmId = confirmId;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
