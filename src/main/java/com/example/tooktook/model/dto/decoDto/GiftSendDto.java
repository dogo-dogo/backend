package com.example.tooktook.model.dto.decoDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GiftSendDto {
    private ArrayList<GiftColorDto> giftColorDto;
    private ArrayList<RibbonColorDto> ribbonColorDto;
}
