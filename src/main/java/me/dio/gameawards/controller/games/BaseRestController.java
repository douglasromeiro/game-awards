package me.dio.gameawards.controller.games;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import me.dio.gameawards.controller.ApiErrorDTO;
import me.dio.gameawards.service.exception.BusinessException;
import me.dio.gameawards.service.exception.NoContentException;

@RequestMapping("/api")
public abstract class BaseRestController {
	
	@ExceptionHandler(NoContentException.class)
	public ResponseEntity<Void> handlerNoContentException(NoContentException exception) {
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiErrorDTO> handlerBusinessExceptino(BusinessException exception) {
		ApiErrorDTO error = new ApiErrorDTO(exception.getMessage());
		return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ApiErrorDTO> handlerBusinessExceptino(Throwable exception) {
		exception.printStackTrace();
		ApiErrorDTO error = new ApiErrorDTO("Ops, ocorreu um erro inesperado...");
		return ResponseEntity.internalServerError().body(error);
	}
	
}
