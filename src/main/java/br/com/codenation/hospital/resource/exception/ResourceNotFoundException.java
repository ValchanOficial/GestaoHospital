package br.com.codenation.hospital.resource.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import br.com.codenation.hospital.services.exception.ObjectNotFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ControllerAdvice //tratar possiveis erros nas requisições
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String message) {
		super(message);
	}
	//	@ExceptionHandler(ObjectNotFoundException.class)
//	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
//
//		HttpStatus status = HttpStatus.NOT_FOUND;
//		StandardError error = new StandardError(System.currentTimeMillis(), status.value(), "Não encontrado.", e.getMessage(), request.getRequestURI());
//		return ResponseEntity.status(status).body(error);
//	}
}