package net.thumtack.onlineshop.dto.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseSettingsDto {

    private Integer maxNameLength;
    private Integer minPasswordLength;

    public ResponseSettingsDto(Integer maxNameLength, Integer minPasswordLength) {
        this.maxNameLength = maxNameLength;
        this.minPasswordLength = minPasswordLength;
    }
}
