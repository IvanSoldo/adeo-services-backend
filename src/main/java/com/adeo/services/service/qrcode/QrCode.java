package com.adeo.services.service.qrcode;

import com.adeo.services.entity.Room;
import java.io.InputStream;

public class QrCode {

  private final InputStream qrCode;
  private final String fileName;
  private final String fileFormat;

  public QrCode(InputStream qrCode, Room room, String fileFormat) {
    this.qrCode = qrCode;
    this.fileName = room.getName() + " #" + room.getNumber();
    this.fileFormat = fileFormat;
  }

  public InputStream asRaw() {
    return qrCode;
  }

  public String getFullFileName() {
    return fileName + "." + fileFormat;
  }
}
