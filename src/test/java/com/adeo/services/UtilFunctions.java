package com.adeo.services;

import com.adeo.services.service.qrcode.QrCode;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class UtilFunctions {

  public static void saveQrCodeToRootFolder(QrCode qrCode) {
    File file = new File(qrCode.getFullFileName());
    try {
      Files.copy(qrCode.asRaw(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
