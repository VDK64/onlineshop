package net.thumtack.onlineshop.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class ServerExceptions extends Exception{
    private String errorCode;
    private String field;
    private String message;
    private List<RespError> errors;

    public ServerExceptions(ServerErrors serverErrors, String field) {
        super(serverErrors.getErrorMessage());
        this.errorCode = String.valueOf(serverErrors);
        this.field = field;
        this.message = serverErrors.getErrorMessage();
    }

    public ServerExceptions(List<RespError> errorList) {
        errors = new ArrayList<>();
        this.errors.addAll(errorList);
    }

    @Override
    public String toString() {
        return "{" +
                "errorCode='" + errorCode + '\'' +
                ", field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
