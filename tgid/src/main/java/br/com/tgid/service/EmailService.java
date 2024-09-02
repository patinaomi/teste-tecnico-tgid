package br.com.tgid.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void enviarEmail(String destinatario, String assunto, String msg);
    void enviarEmailErroTransacao(Long clienteId, String mensagemErro);
}
