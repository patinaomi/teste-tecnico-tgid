package br.com.tgid.service.impl;

import br.com.tgid.repository.EmpresaRepository;
import br.com.tgid.service.EmpresaService;
import br.com.tgid.entity.Empresa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Override
    public Empresa criar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }
}
