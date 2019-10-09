package net.thumtack.onlineshop.exceptions;

import lombok.Data;

@Data
public class RespError {
    private String errorCode;
    private String field;
    private String message;

    public RespError(ServerExceptions serverExceptions) {
        this.errorCode = serverExceptions.getErrorCode();
        this.field = serverExceptions.getField();
        this.message = serverExceptions.getMessage();
    }

    public RespError(ServerErrors errorCode, String field, String message) {
        this.errorCode = String.valueOf(errorCode);
        this.field = field;
        this.message = message;
    }

}
