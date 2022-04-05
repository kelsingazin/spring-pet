package com.appsdeveloperblog.app.ws.ui.model.response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private Date timestamp;
    private String message;
}
