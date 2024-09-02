package br.com.tgid.service;

import br.com.tgid.entity.Empresa;
import br.com.tgid.repository.EmpresaRepository;
import br.com.tgid.service.impl.EmpresaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EmpresaServiceImpl empresaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarEmpresaComSucesso() {
        // Arrange: Criando diretamente uma instância da entidade Empresa
        Empresa empresa = new Empresa();
        empresa.setNome("Empresa Teste");
        empresa.setCnpj("31.396.023/0001-90");
        empresa.setEmail("empresa@teste.com");
        empresa.setSaldo(1000.0);

        // Configuração do Mock
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa);

        // Act: Chamando o método de criação do serviço
        Empresa empresaSalva = empresaService.criar(empresa);

        // Assert: Verificando os resultados
        assertEquals("Empresa Teste", empresaSalva.getNome());
        assertEquals("31.396.023/0001-90", empresaSalva.getCnpj());
        assertEquals("empresa@teste.com", empresaSalva.getEmail());
        assertEquals(1000.0, empresaSalva.getSaldo());
    }
}
