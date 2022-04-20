package com.adeo.services.service.qrcode;

import static org.assertj.core.api.Assertions.assertThat;

import com.adeo.services.entity.Room;
import com.adeo.services.service.qrcode.QrCode;
import com.adeo.services.service.qrcode.QrCodeService;
import com.adeo.services.service.qrcode.QrCodeServiceImpl;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QrCodeServiceImplTest {

  private QrCodeService qrCodeService;

  @BeforeEach
  void setUp() {
    qrCodeService = new QrCodeServiceImpl();
  }

  @Test
  void given_Uri_Then_Crate_QrCode() {
    // prepare
    String url = "https://www.youtube.com/watch?v=0Z1fdCFF8VY";
    Room room = new Room();
    room.setName("Room");
    room.setNumber("56");

    // execute
    QrCode qrCode = qrCodeService.create(URI.create(url), room);

    // verify
    assertThat(qrCode.asRaw()).isNotNull();
    assertThat(qrCode.getFullFileName()).isEqualTo("Room #56.jpeg");

    //UtilTest.saveQrCodeToRootFolder(qrCode);
  }


}