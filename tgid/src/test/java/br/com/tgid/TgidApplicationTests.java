package br.com.tgid;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "TGID API", version = "1.0", description = "API para teste de desenvolvimento"))
class TgidApplicationTests {

    @Test
    void contextLoads() {
    }

}
