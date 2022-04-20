package com.adeo.services.controller.qrcode;

import com.adeo.services.service.qrcode.HotelRoomQrCodeService;
import com.adeo.services.service.qrcode.ZipFile;
import java.io.IOException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QrCodeController {

  private final HotelRoomQrCodeService hotelRoomQrCodeService;

  public QrCodeController(HotelRoomQrCodeService hotelRoomQrCodeService) {
    this.hotelRoomQrCodeService = hotelRoomQrCodeService;
  }

  @Secured("ROLE_ADMIN")
  @GetMapping("api/v1/qrcode")
  public ResponseEntity<Resource> getQrCodeForAllHotelRooms() throws IOException {
    ZipFile zipFile = hotelRoomQrCodeService.getQrCodesForAllHotelRooms();

    HttpHeaders header = new HttpHeaders();
    header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=qrcodes.zip");

    ByteArrayResource resource = new ByteArrayResource(zipFile.getInputStream().readAllBytes());

    return ResponseEntity.ok()
        .headers(header)
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(resource);
  }

}
