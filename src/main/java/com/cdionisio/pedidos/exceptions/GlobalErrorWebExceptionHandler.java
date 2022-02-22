package com.cdionisio.pedidos.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
@Order(-1)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorWebExceptionHandler.class);

    private static final String ERROR_KEY = "error";
    private static final String EXCEPTION_KEY = "exception";

    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resourceProperties,
                                          ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        LOGGER.info("Init renderErrorResponse");
        Map<String, Object> errorGeneral = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        Map<String, Object> mapException = new HashMap<>();
        LOGGER.error(String.valueOf(getError(request)));

        HttpStatus httpStatus;
        String statusCode = String.valueOf(errorGeneral.get("status"));

        switch (statusCode) {
            case "500":
                LOGGER.info("Getting error response 500");
                if(getError(request) instanceof ExpiredJwtException) {
                    mapException.put(ERROR_KEY, HttpStatus.UNAUTHORIZED.value());
                    mapException.put(EXCEPTION_KEY, "Token expirado");
                    httpStatus = HttpStatus.UNAUTHORIZED;
                    break;
                } else {
                    mapException.put(ERROR_KEY, statusCode);
                    mapException.put(EXCEPTION_KEY, "Error general del backend");
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    break;
                }

            case "400":
                LOGGER.info("Getting error response 400");
                mapException.put(ERROR_KEY, statusCode);
                mapException.put(EXCEPTION_KEY, "Petición incorrecta");
                mapException.put("mensaje", this.getMapValidations(request));
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            default:
                LOGGER.info("Getting error response {}", statusCode);
                mapException.put(ERROR_KEY, statusCode);
                mapException.put(EXCEPTION_KEY, errorGeneral.get(ERROR_KEY));
                httpStatus = HttpStatus.CONFLICT;
                break;
        }

        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(mapException));
    }

    private Map<String, Object> getMapValidations(ServerRequest request) {
        LOGGER.info("Getting Parameters from Validation");

        Map<String, Object> mapAtributos = request.exchange().getAttributes();
        String classErrorsValidation = "org.springframework.boot.web.reactive.error.DefaultErrorAttributes.ERROR";

        WebExchangeBindException valorMapError = (WebExchangeBindException) mapAtributos.get(classErrorsValidation);
        BindingResult result =  valorMapError.getBindingResult();
        List<FieldError> listaErrores = result.getFieldErrors();

        Map<String, Object> mapValidations = new HashMap<>();
        listaErrores.forEach(fieldError -> mapValidations.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return mapValidations;
    }
}

