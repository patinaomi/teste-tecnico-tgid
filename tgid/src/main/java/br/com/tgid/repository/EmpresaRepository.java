package br.com.tgid.repository;

import br.com.tgid.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Empresa findByCnpj(String cnpj);
}
