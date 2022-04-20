package com.adeo.services.service.qrcode;

import com.adeo.services.entity.Room;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;

@Service
public class QrCodeServiceImpl implements QrCodeService {

  private static final int QR_CODE_WIDTH = 200;
  private static final int QR_CODE_HEIGHT = 200;
  private static final String QR_CODE_FILE_FORMAT = "jpeg";

  @Override
  public QrCode create(URI url, Room room) {

    QRCodeWriter barcodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = null;
    try {
      bitMatrix = barcodeWriter.encode(url.toString(), BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT);
    } catch (WriterException e) {
      e.printStackTrace();
    }

    BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      ImageIO.write(bufferedImage, QR_CODE_FILE_FORMAT, os);
    } catch (IOException e) {
      e.printStackTrace();
    }

    InputStream inputStream = new ByteArrayInputStream(os.toByteArray());

    return new QrCode(inputStream, room,  QR_CODE_FILE_FORMAT);
  }
}
