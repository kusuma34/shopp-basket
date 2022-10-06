package com.example.shoppingcart.exception;

import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CustomHttpErrorResponse {
	private Integer status;
    private String path;
    private String errorMessage;
    private String timeStamp;
    private String trace;

    public CustomHttpErrorResponse(int status, Map<String, Object> errorAttributes) {
        this.setStatus(status);
        this.setPath((String) errorAttributes.get("path"));
        this.setErrorMessage((String) errorAttributes.get("message"));
        this.setTimeStamp(errorAttributes.get("timestamp").toString());
        this.setTrace((String) errorAttributes.get("trace"));
    }

	private void setTrace(String string) {
		this.trace=string;
	}

	private void setTimeStamp(String string) {
		this.timeStamp=string;		
	}

	private void setErrorMessage(String string) {
		this.errorMessage=string;
	}

	private void setPath(String string) {
		this.path=string;
	}

	private void setStatus(int status2) {
		this.status=status2;
	}
}
