package com.hyperskill.qrcodeapi.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hyperskill.qrcodeapi.exception.QRCodeGenerationException;
import com.hyperskill.qrcodeapi.model.QRCodeData;
import com.hyperskill.qrcodeapi.model.QRServiceRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


import java.awt.image.BufferedImage;
import java.util.Map;

import static com.hyperskill.qrcodeapi.util.Constants.CORRECTION_LEVELS;
import static com.hyperskill.qrcodeapi.util.Constants.MEDIA_TYPES;

@Service
public class QrCodeService {

    private final ValidationService validationService;

    public QrCodeService(ValidationService validationService) {
        this.validationService = validationService;
    }


    public QRCodeData getQRCodeData(QRServiceRequest request) {
        validationService.validate(request);
        ErrorCorrectionLevel correctionLevel = CORRECTION_LEVELS.get(request.correction());
        MediaType mediaType = MEDIA_TYPES.get(request.type());
        return new QRCodeData(
                request.contents(),
                request.size(),
                correctionLevel,
                mediaType);
    }

    public BufferedImage generateQRCodeImage(QRCodeData qrCodeData) {
        QRCodeWriter writer = new QRCodeWriter();
        Map<EncodeHintType, ?> hints = Map.of(EncodeHintType.ERROR_CORRECTION, qrCodeData.correctionLevel());
        try {
            BitMatrix bitMatrix = writer.encode(
                    qrCodeData.contents(),
                    BarcodeFormat.QR_CODE,
                    qrCodeData.size(),
                    qrCodeData.size(),
                    hints);

            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            throw new QRCodeGenerationException("Failed to generate QR code", e);
        }
    }

}
