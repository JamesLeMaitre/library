package dev.jtm.library.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Formatter<variant> {
    private boolean status;
    private variant data;
    private String message;
}
