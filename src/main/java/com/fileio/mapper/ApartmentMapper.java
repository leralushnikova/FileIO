package com.fileio.mapper;

import com.fileio.dto.ApartmentDto;
import com.fileio.model.ApartmentDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс маппер для преобразования объектов.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ApartmentMapper {

    /**
     * Метод преобразует получившиеся данные в объект {@link ApartmentDao}
     *
     * @param apartmentDto - объект ApartmentDto.
     * @param city - строка города
     * @param street - строка улицы
     * @param houseNumber - номер дома
     * @return объект {@link ApartmentDao}
     */
    @Mapping(target = "price", source = "apartmentDto.price", qualifiedByName = "parseDouble")
    @Mapping(target = "livingArea", source = "apartmentDto.living_area", qualifiedByName = "parseDouble")
    @Mapping(target = "apartmentNumber", source = "apartmentDto.apartment")
    ApartmentDao toApartmentDao(ApartmentDto apartmentDto, String city, String street, Short houseNumber);

    /**
     * Разбивает строку на массив, где в [0] элементе будет адрес дома, а в [1] - номер дома
     *
     * @param address - адрес квартиры
     * @return возвращает строчный массив
     */
    default String[] splitAddress(String address) {
        String regex = "^\\s*([^,]+),\\s*((?:ул\\.|пр-т\\.|проспект|шоссе|бульвар|пер\\.|пл\\.)\\s*[^,]+),\\s*д\\.\\s*(\\d+)";
        Pattern pattern = java.util.regex.Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);

        if (matcher.find()) {
            return new String[]{matcher.group(1).trim(), matcher.group(2), matcher.group(3)};
        }
        throw new IllegalArgumentException("Адрес не распознан");
    }

    /**
     * Преобразует строку в десятичное число
     *
     * @param priceStr - строка
     * @return вобзращает десятичное число
     */
    @Named("parseDouble")
    default double parseDouble(String priceStr) {
        String clean = priceStr.replaceAll("[^0-9,.]", "");
        clean = clean.replace(',', '.');
        return Double.parseDouble(clean);
    }

    /**
     * Преобразует список объектов {@link ApartmentDto} в список {@link ApartmentDao}
     *
     * @param apartmentDtos - список объектов {@link ApartmentDto}
     * @return возвращает список {@link ApartmentDao}
     */
    default List<ApartmentDao> toApartmentDaos(List<ApartmentDto> apartmentDtos) {
        List<ApartmentDao> apartmentDaos = new ArrayList<>();
        for (ApartmentDto apartmentDto : apartmentDtos) {
            String[] strings = splitAddress(apartmentDto.getAddress());
            String city = strings[0];
            String street = strings[1];
            Short houseNumber = Short.valueOf(strings[2]);
            ApartmentDao apartmentDao = toApartmentDao(apartmentDto, city, street, houseNumber);
            apartmentDaos.add(apartmentDao);
        }
        return apartmentDaos;
    }

}
