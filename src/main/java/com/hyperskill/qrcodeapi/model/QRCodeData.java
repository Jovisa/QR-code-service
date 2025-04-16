package com.hyperskill.qrcodeapi.model;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.http.MediaType;

public record QRCodeData(
        String contents,
        int size,
        ErrorCorrectionLevel correctionLevel,
        MediaType mediaType
) {}
