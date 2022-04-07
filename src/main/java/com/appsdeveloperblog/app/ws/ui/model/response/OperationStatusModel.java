package com.appsdeveloperblog.app.ws.ui.model.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class OperationStatusModel {
    private String operationName;
    private String operationResult;
}
