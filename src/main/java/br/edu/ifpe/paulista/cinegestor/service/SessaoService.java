package br.edu.ifpe.paulista.cinegestor.service;

import br.edu.ifpe.paulista.cinegestor.model.*;
import br.edu.ifpe.paulista.cinegestor.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    public List<Sessao> listarSessoes() {
        return sessaoRepository.findAll();
    }

    public Sessao cadastrarSessao(Sessao sessao) {
        return sessaoRepository.save(sessao);
    }

    public Optional<Sessao> buscarPorId(Integer id) {
        return sessaoRepository.findById(id);
    }

    public Sessao atualizarSessao(Integer id, Sessao novaSessao) {
        return sessaoRepository.findById(id).map(sessao -> {
            sessao.setFilme(novaSessao.getFilme());
            sessao.setSala(novaSessao.getSala());
            sessao.setHorario(novaSessao.getHorario());
            return sessaoRepository.save(sessao);
        }).orElse(null);
    }

    public void deletarSessao(Integer id) {
        sessaoRepository.deleteById(id);
    }

    public void ocuparAssento(Integer sessaoId, String assento) {
        sessaoRepository.findById(sessaoId).ifPresent(sessao -> {
            sessao.ocuparAssento(assento);
            sessaoRepository.save(sessao);
        });
    }
}
