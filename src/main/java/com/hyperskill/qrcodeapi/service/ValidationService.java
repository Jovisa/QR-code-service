package com.hyperskill.qrcodeapi.service;

import com.hyperskill.qrcodeapi.exception.InvalidParamException;
import com.hyperskill.qrcodeapi.model.QRServiceRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;


import static com.hyperskill.qrcodeapi.util.Constants.*;

@Service
public class ValidationService {

    public void validate(QRServiceRequest request) {
        validateContents(request.contents());
        validateSize(request.size());
        validateCorrection(request.correction());
        validateType(request.type());
    }

    private void validateContents(String contents) {
        if (Strings.isBlank(contents)) {
            throw new InvalidParamException("Contents cannot be null or blank");
        }
    }

    private void validateSize(int size) {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new InvalidParamException("Image size must be between 150 and 350 pixels");
        }
    }

    private void validateCorrection(String correction) {
        if (!CORRECTION_LEVELS.containsKey(correction)) {
            throw new InvalidParamException("Permitted error correction levels are L, M, Q, H");
        }
    }

    private void validateType(String type) {
        if (!MEDIA_TYPES.containsKey(type)) {
            throw new InvalidParamException("Only png, jpeg and gif image types are supported");

        }
    }

}
