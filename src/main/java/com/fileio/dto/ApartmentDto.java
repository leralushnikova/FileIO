package com.fileio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApartmentDto {
    private String address;
    private Short apartment;
    private String price;
    private String living_area;
    private Byte rooms;
    private Byte floor;
    private Byte entrance;
}
