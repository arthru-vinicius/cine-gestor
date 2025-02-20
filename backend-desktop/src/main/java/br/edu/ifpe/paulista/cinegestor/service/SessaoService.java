package br.edu.ifpe.paulista.cinegestor.service;

import br.edu.ifpe.paulista.cinegestor.model.Sessao;
import br.edu.ifpe.paulista.cinegestor.model.Sessao.StatusSessao;
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

    // Lista todas as sessões (tanto agendadas quanto encerradas)
    public List<Sessao> listarSessoes() {
        return sessaoRepository.findAll();
    }

    // Mét0do para listar apenas as sessões agendadas (se necessário)
    public List<Sessao> listarSessoesAgendadas() {
        return sessaoRepository.findByStatus(StatusSessao.AGENDADA);
    }

    // Cadastrar nova sessão: se o status não for informado, seta como AGENDADA
    public Sessao cadastrarSessao(Sessao sessao) {
        if (sessao.getStatus() == null) {
            sessao.setStatus(StatusSessao.AGENDADA);
        }
        return sessaoRepository.save(sessao);
    }

    public Optional<Sessao> buscarPorId(Integer id) {
        return sessaoRepository.findById(id);
    }

    // Atualizar campos básicos; opcionalmente pode permitir atualizar o status, mas normalmente não na operação de edição
    public Sessao atualizarSessao(Integer id, Sessao novaSessao) {
        return sessaoRepository.findById(id).map(sessao -> {
            sessao.setFilme(novaSessao.getFilme());
            sessao.setSala(novaSessao.getSala());
            sessao.setHorario(novaSessao.getHorario());
            // Se desejar permitir atualização do status, pode incluir:
            // sessao.setStatus(novaSessao.getStatus());
            return sessaoRepository.save(sessao);
        }).orElse(null);
    }

    // Em vez de excluir, marca a sessão como ENCERRADA
    public void deletarSessao(Integer id) {
        sessaoRepository.findById(id).ifPresent(sessao -> {
            sessao.setStatus(StatusSessao.ENCERRADA);
            sessaoRepository.save(sessao);
        });
    }

    // Ocupar um assento: valida se a sessão está agendada e, se sim, ocupa o assento
    public void ocuparAssento(Integer sessaoId, String assento) {
        sessaoRepository.findById(sessaoId).ifPresent(sessao -> {
            if (sessao.getStatus() != StatusSessao.AGENDADA) {
                throw new RuntimeException("Não é possível ocupar assento em uma sessão encerrada.");
            }
            sessao.ocuparAssento(assento);
            sessaoRepository.save(sessao);
        });
    }
}
