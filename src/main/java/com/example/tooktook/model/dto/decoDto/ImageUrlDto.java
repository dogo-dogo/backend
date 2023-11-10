package com.example.tooktook.model.dto.decoDto;


import lombok.*;

@Getter
@Setter
public class ImageUrlDto {
    private String doorUrl;
    private String giftUrl;

    @Builder
    public ImageUrlDto(String doorUrl, String giftUrl) {
        this.doorUrl = doorUrl;
        this.giftUrl = giftUrl;
    }
}
