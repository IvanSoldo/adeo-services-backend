package com.adeo.services.service.qrcode;

import com.adeo.services.entity.Room;
import com.adeo.services.service.room.RoomService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HotelRoomQrCodeServiceImpl implements HotelRoomQrCodeService {

  private final RoomService roomService;
  private final QrCodeService qrCodeService;
  private final ZipService zipService;
  private final String frontendUrl;

  public HotelRoomQrCodeServiceImpl(
      RoomService roomService,
      QrCodeService qrCodeService,
      ZipService zipService,
      @Value("${application.frontend-url}") String frontendUrl) {

    this.roomService = roomService;
    this.qrCodeService = qrCodeService;
    this.zipService = zipService;
    this.frontendUrl = frontendUrl;
  }

  @Override
  public ZipFile getQrCodesForAllHotelRooms() {

    List<Room> rooms = roomService.getAll();

    List<QrCode> qrCodes = rooms.stream()
        .map(room -> qrCodeService.create(URI.create(frontendUrl + room.getUuid()), room))
        .collect(Collectors.toList());

    return zipService.zip(qrCodes);
  }
}
