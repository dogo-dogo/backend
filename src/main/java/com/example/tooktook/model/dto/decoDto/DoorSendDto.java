package com.example.tooktook.model.dto.decoDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class DoorSendDto {
    private ArrayList<Color> color;
    private ArrayList<Deco> deco;


}
