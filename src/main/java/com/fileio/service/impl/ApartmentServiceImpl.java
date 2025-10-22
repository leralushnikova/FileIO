package com.fileio.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileio.dto.ApartmentDto;
import com.fileio.mapper.ApartmentMapper;
import com.fileio.model.ApartmentDao;
import com.fileio.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;

/**
 * Сервис работы обработки файлов.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {

    @Value("${file.path}")
    private String filePath;

    private final ObjectMapper mapper;
    private final ApartmentMapper apartmentMapper;

    @Override
    public void upload(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        List<ApartmentDto> apartmentDtos = mapper.readValue(inputStream, new TypeReference<>() {
        });

        List<ApartmentDao> apartmentDaos = apartmentMapper.toApartmentDaos(apartmentDtos);

        apartmentDaos.sort(Comparator.comparing(ApartmentDao::getHouseNumber)
                .thenComparing(ApartmentDao::getApartmentNumber));

        String fileName = multipartFile.getOriginalFilename();

        writeFile(apartmentDaos, fileName);

        log.debug("Данный записаны в файл:{} ", apartmentDaos);
    }


    /**
     * Метод записывает список объектов {@link ApartmentDao} в файл
     *
     * @param apartmentDaos - список объектов {@link ApartmentDao} в файл
     * @param fileName - имя файла
     * @throws IOException ошибка при записи в файл
     */
    private void writeFile(List<ApartmentDao> apartmentDaos, String fileName) throws IOException {
        File outFile = new File(filePath + fileName);

        mapper.writerWithDefaultPrettyPrinter().writeValue(outFile, apartmentDaos);
    }
}
