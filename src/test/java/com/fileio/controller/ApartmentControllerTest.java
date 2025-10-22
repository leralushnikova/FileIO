package com.fileio.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayName("Тестирование ApartmentController")
class ApartmentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @SneakyThrows
    @Test
    @DisplayName("чтение файла и запись")
    void readFile_Success() {

        InputStream inputStream = getJsonInputStream(jsonData());
        MockMultipartFile multipartFile = new MockMultipartFile("file", "apartment-test.json", "application/json", inputStream);

        mockMvc.perform(multipart("/apartment/upload")
                        .file(multipartFile))
                .andExpect(status().isOk());
    }

    public InputStream getJsonInputStream(String jsonString) {
        byte[] jsonBytes = jsonString.getBytes(StandardCharsets.UTF_8);

        return new ByteArrayInputStream(jsonBytes);
    }

    private String jsonData () {
        return
                """
                        [
                            {
                                "address": "Москва, ул. Ленина, д. 10",
                                "apartment": "123",
                                "price": "5 000 000 руб.",
                                "living_area": "45 м²",
                                "rooms": "2",
                                "floor": "5",
                                "entrance": "1"
                            },
                            {
                                "address": "Санкт-Петербург, ул. Невского, д. 25",
                                "apartment": "16",
                                "price": "7 500 000 руб.",
                                "living_area": "60 м²",
                                "rooms": "2",
                                "floor": "7",
                                "entrance": "2"
                            }
                        ]""";
    }
}