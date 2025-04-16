package com.hyperskill.qrcodeapi.util;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.http.MediaType;

import java.util.Map;

public class Constants {

    public static final int MIN_SIZE = 150;
    public static final int MAX_SIZE = 350;

    public static final Map<String, ErrorCorrectionLevel> CORRECTION_LEVELS = Map.of(
            "L", ErrorCorrectionLevel.L,
            "M", ErrorCorrectionLevel.M,
            "Q", ErrorCorrectionLevel.Q,
            "H", ErrorCorrectionLevel.H
    );

    public static final Map<String, MediaType> MEDIA_TYPES = Map.of(
            "png", MediaType.IMAGE_PNG,
            "jpeg", MediaType.IMAGE_JPEG,
            "gif", MediaType.IMAGE_GIF
    );

}
