package com.hyperskill.qrcodeapi.controller;

import com.hyperskill.qrcodeapi.model.QRCodeData;
import com.hyperskill.qrcodeapi.model.QRServiceRequest;
import com.hyperskill.qrcodeapi.service.QrCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.awt.image.BufferedImage;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class QrCodeController {

    private final QrCodeService qrCodeService;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }


    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("GRRRRRRRRRRRR!");
    }

    @GetMapping("/health")
    public ResponseEntity<Void> getHealth() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/qrcode")
    public ResponseEntity<?> getQrCode(@RequestParam String contents,
                                       @RequestParam(defaultValue = "250") int size,
                                       @RequestParam(defaultValue = "L") String correction,
                                       @RequestParam(defaultValue = "png") String type) {

        QRCodeData qrCodeData = qrCodeService.getQRCodeData(
                new QRServiceRequest(contents, size, correction, type));
        BufferedImage qrCodeImage = qrCodeService.generateQRCodeImage(qrCodeData);

        return ResponseEntity.ok()
                .contentType(qrCodeData.mediaType())
                .body(qrCodeImage);
    }

}
