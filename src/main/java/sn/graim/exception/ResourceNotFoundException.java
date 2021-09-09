package sn.graim.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String resourceName;
	private String fieldName;
	private Object fieldValue;
		
	public ResourceNotFoundException(String resourceName, Object fieldValue, String fieldName) {
        super(String.format("%s Inexistante avec %s : '%s'", resourceName, fieldValue, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}
}
