package de.ait.pool.controller;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest // означает, что SpringBoot запустит полностью приложение ( с базой )
@AutoConfigureMockMvc // конфигурация MockMVC
@ActiveProfiles("test")
@DisplayName("Endpoint /products is working:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class ProductIntegrationTest {

}