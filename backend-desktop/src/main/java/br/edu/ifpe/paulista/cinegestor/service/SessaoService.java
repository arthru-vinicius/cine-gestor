package br.edu.ifpe.paulista.cinegestor.service;

import br.edu.ifpe.paulista.cinegestor.model.Sessao;
import br.edu.ifpe.paulista.cinegestor.model.Sessao.StatusSessao;
import br.edu.ifpe.paulista.cinegestor.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;

    public List<Sessao> listarSessoes() {
        return sessaoRepository.findAll();
    }

    public List<Sessao> listarSessoesAgendadas() {
        return sessaoRepository.findByStatus(StatusSessao.agendada);
    }

    public Sessao cadastrarSessao(Sessao sessao) {
        if (sessao.getStatus() == null) {
            sessao.setStatus(StatusSessao.agendada);
        }
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
            sessao.setStatus(novaSessao.getStatus());
            return sessaoRepository.save(sessao);
        }).orElse(null);
    }

    public void deletarSessao(Integer id) {
        sessaoRepository.findById(id).ifPresent(sessao -> {
            sessao.setStatus(StatusSessao.encerrada);
            sessaoRepository.save(sessao);
        });
    }

    public void ocuparAssento(Integer sessaoId, String assento) {
        sessaoRepository.findById(sessaoId).ifPresent(sessao -> {
            if (sessao.getStatus() != StatusSessao.agendada) {
                throw new RuntimeException("Não é possível ocupar assento em uma sessão encerrada.");
            }
            sessao.ocuparAssento(assento);
            sessaoRepository.save(sessao);
        });
    }
}
