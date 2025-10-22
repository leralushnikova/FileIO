package com.fileio.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Интерфейс работы с файлами.
 */
public interface ApartmentService {

    /**
     * Операция по загрузке и обработке файла
     *
     * @param multipartFile - файла
     * @throws IOException ошибка при чтении файла.
     */
    void upload(MultipartFile multipartFile) throws IOException;

}
