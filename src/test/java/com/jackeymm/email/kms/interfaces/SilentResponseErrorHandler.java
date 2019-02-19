package com.jackeymm.email.kms.interfaces;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

class SilentResponseErrorHandler extends DefaultResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
    return super.hasError(clientHttpResponse);
  }

  @Override
  public void handleError(ClientHttpResponse clientHttpResponse) {

  }
}
