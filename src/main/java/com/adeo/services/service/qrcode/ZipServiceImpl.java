package com.adeo.services.service.qrcode;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.stereotype.Service;

@Service
public class ZipServiceImpl implements ZipService {

  @Override
  public ZipFile zip(List<QrCode> qrCodes) {
    return getCompressed(qrCodes);
  }


  private ZipFile getCompressed(List<QrCode> inputContents) {
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      ZipOutputStream zos = new ZipOutputStream(bos);
      for (QrCode zipInputContent : inputContents) {

        zos.putNextEntry(new ZipEntry(zipInputContent.getFullFileName()));

        int count;
        byte[] data = new byte[2048];

        try (BufferedInputStream entryStream = new BufferedInputStream(zipInputContent.asRaw(), 2048)) {
          while ((count = entryStream.read(data, 0, 2048)) != -1) {
            zos.write(data, 0, count);
          }
        }

        zos.closeEntry();

      }
      zos.close();
      return new ZipFile(new ByteArrayInputStream(bos.toByteArray()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}