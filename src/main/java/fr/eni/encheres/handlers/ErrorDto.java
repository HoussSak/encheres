package fr.eni.encheres.handlers;

import fr.eni.encheres.exception.ErrorCodes;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ErrorDto {

    private final String code;
    private final String message;
}
