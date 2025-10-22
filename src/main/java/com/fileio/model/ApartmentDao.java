package com.fileio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApartmentDao {
    private String city;
    private String street;
    private Short houseNumber;
    private Short apartmentNumber;
    private Double price;
    private Double livingArea;
    private Byte rooms;
    private Byte floor;
    private Byte entrance;
}
