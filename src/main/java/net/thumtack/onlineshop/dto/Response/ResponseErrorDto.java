package net.thumtack.onlineshop.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.thumtack.onlineshop.exceptions.RespError;
import net.thumtack.onlineshop.exceptions.ServerExceptions;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class ResponseErrorDto {
    private List<RespError> errors = new ArrayList<>();

    public ResponseErrorDto(ServerExceptions serverExceptions){
        RespError respError = new RespError(serverExceptions);
        errors.add(respError);
    }

    public ResponseErrorDto(RespError respError){
        this.errors.add(respError);
    }

    public void addErrors(RespError respError) {
        this.errors.add(respError);
    }
}
