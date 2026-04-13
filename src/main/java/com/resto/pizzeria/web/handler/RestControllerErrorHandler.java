package com.resto.pizzeria.web.handler;

import com.resto.pizzeria.web.exception.ApiResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

@Slf4j
public class RestControllerErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(
            final ClientHttpResponse response
    ) throws IOException {
        return response.getStatusCode().is4xxClientError()
                || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(
            final URI url,
            final HttpMethod method,
            final ClientHttpResponse response
    ) throws IOException {
        final HttpStatusCode status = response.getStatusCode();

        if (status.value() == 404) {
            throw new ApiResponseException("Ressource non trouvée", status.value());
        }

        if (status.value() == 400) {
            throw new ApiResponseException("Requête invalide", status.value());
        }

        // todo : gérer les codes erreurs (json body)
        if (status.is5xxServerError()) {
            log.error("Erreur 500 : {}", status.value());
        } else {
            log.error("Erreur n'est pas géré : {}", status.value());
        }

        throw new ApiResponseException("Erreur", 400);
    }
}
