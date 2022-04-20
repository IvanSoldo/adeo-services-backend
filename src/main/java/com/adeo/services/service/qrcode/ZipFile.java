package com.adeo.services.service.qrcode;

import java.io.InputStream;

public class ZipFile {
  private final InputStream inputStream;

  public ZipFile(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public InputStream getInputStream() {
    return inputStream;
  }
}
