package com.ddrd.goldeskapp.data.model.partido;

public class PartidoResponseDuplicate {

    private String status;  // "SUCCESS", "WARNING", "ERROR"
    private String message;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
