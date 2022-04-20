package com.adeo.services.service.qrcode;

import java.util.List;

public interface ZipService {

  ZipFile zip(List<QrCode> qrCodes);
}
