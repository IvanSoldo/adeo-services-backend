package com.adeo.services.service.qrcode;

import com.adeo.services.entity.Room;
import java.net.URI;

public interface QrCodeService {

  QrCode create(URI url, Room room);

}
