package com.delivery.app.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.delivery.app.dto.ExceptionResponseDTO;
import com.delivery.app.exception.NotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(NotFoundException.class)
	protected ResponseEntity<Object> tratarNotFoundException(NotFoundException ex, WebRequest request) {
		
		ExceptionResponseDTO dto = new ExceptionResponseDTO();
		
		dto.setStatus(HttpStatus.NOT_FOUND.value());
		dto.setMensagem(ex.getLocalizedMessage());
		
		return handleExceptionInternal(ex, dto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> tratarMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
																				WebRequest request) {
		
		ExceptionResponseDTO dto = new ExceptionResponseDTO();
		
		dto.setStatus(HttpStatus.BAD_REQUEST.value());
		dto.setMensagem("Verifique os parâmetros da url");
		
		return handleExceptionInternal(ex, dto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		
	}
	
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<Object> tratarRuntimeException(RuntimeException ex, WebRequest request) {
		
		ExceptionResponseDTO dto = new ExceptionResponseDTO();
		
		dto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		dto.setMensagem("Servidor temporariamente indisponível, tente novamente mais tarde!");
		
		return handleExceptionInternal(ex, dto, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		return handleValidationInternal(ex, request, ex.getBindingResult());
		
	}
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, WebRequest request, BindingResult bindingResult) {
		
		List<ExceptionResponseDTO.Erro> erros = bindingResult.getFieldErrors().stream()
			.map(campo -> {
				
				String mensagem = messageSource.getMessage(campo, LocaleContextHolder.getLocale());
				
				ExceptionResponseDTO.Erro erro = new ExceptionResponseDTO.Erro();
				erro.setCampo(campo.getField());
				erro.setErro(mensagem);
				
				return erro;
				
			}).toList();
		
		ExceptionResponseDTO response = new ExceptionResponseDTO();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setMensagem("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente!");
		response.setValidacoes(erros);
		
		return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		
	}
	
}















