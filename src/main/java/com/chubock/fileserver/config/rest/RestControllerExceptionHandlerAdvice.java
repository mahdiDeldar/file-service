package com.chubock.fileserver.config.rest;

import com.chubock.fileserver.component.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestControllerExceptionHandlerAdvice {

    private static final String VALIDATION_ERROR_TITLE = "Validation Error";

    private final Messages messages;

    public RestControllerExceptionHandlerAdvice(Messages messages) {
        this.messages = messages;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {

        MessagesResponse response = new MessagesResponse();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            response.addMessage(new Message(VALIDATION_ERROR_TITLE, fieldName + ": " + errorMessage));
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<MessagesResponse> handleRuntimeExceptions(RuntimeException ex) {
        MessagesResponse response = new MessagesResponse();
        response.addMessage(new Message(messages.getMessage(ex.getMessage())));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public static class Message {

        private String text;
        private String title = "";

        public Message() {
        }

        public Message(String text) {
            this.text = text;
        }

        public Message(String title, String text) {
            this.title = title;
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }

    public static class MessagesResponse {

        private List<Message> messages = new ArrayList<>();

        public List<Message> getMessages() {
            return messages;
        }

        public void setMessages(List<Message> messages) {
            this.messages = messages;
        }

        public void addMessage(Message message) {
            getMessages().add(message);
        }

    }

}
