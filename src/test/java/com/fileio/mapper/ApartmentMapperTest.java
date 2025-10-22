package com.fileio.mapper;

import com.fileio.dto.ApartmentDto;
import com.fileio.model.ApartmentDao;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;

@DisplayName("Тестирование методов маппера ApartmentMapper")
@ExtendWith(SoftAssertionsExtension.class)
class ApartmentMapperTest {

    ApartmentMapper apartmentMapper = Mappers.getMapper(ApartmentMapper.class);

    @Test
    @DisplayName("Создания объекта ApartmentDao на основе полученных данных.")
    void toApartmentDao(SoftAssertions sa) {
        String city = "Москва";
        String street = "ул. Ленина";
        Short houseNumber = 3;
        StringBuilder address = new StringBuilder();
        address.append(city).append(" , ").append(street).append(" , д. ").append(houseNumber);
        ApartmentDto apartmentDto = new ApartmentDto();
        apartmentDto.setAddress(address.toString());
        apartmentDto.setApartment((short) 3);
        apartmentDto.setPrice("5 000 000 руб.");
        apartmentDto.setLiving_area("45 м²");

        ApartmentDao result = apartmentMapper.toApartmentDao(apartmentDto, city, street, houseNumber);
        sa.assertThat(result).isNotNull();
        sa.assertThat(result.getApartmentNumber()).isEqualTo(apartmentDto.getApartment());
        sa.assertThat(result.getFloor()).isEqualTo(apartmentDto.getFloor());
    }
}