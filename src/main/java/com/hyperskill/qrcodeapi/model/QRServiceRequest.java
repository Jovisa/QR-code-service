package com.hyperskill.qrcodeapi.model;




//todo validation not used for now
public record QRServiceRequest(

//        @NotBlank(message = "Contents cannot be null or blank")
        String contents,

//        @Min(value = 150, message = "Image size must be between 150 and 350 pixels")
//        @Max(value = 350, message = "Image size must be between 150 and 350 pixels")
        int size,

        String correction,

//        @Pattern(
//                regexp = "png|jpeg|gif",
//                flags = Pattern.Flag.CASE_INSENSITIVE,
//                message = "Only png, jpeg and gif image types are supported"
//        )
        String type
) {
}


