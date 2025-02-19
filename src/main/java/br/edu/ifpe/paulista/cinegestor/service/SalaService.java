package br.edu.ifpe.paulista.cinegestor.service;

import br.edu.ifpe.paulista.cinegestor.model.Assento;
import br.edu.ifpe.paulista.cinegestor.model.Sala;
import br.edu.ifpe.paulista.cinegestor.repository.AssentoRepository;
import br.edu.ifpe.paulista.cinegestor.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private AssentoRepository assentoRepository;

    // Listar todas as salas
    public List<Sala> listarSalas() {
        return salaRepository.findAll();
    }

    // Buscar sala por ID
    public Optional<Sala> buscarPorId(Integer id) {
        return salaRepository.findById(id);
    }

    // Cadastrar nova sala e gerar assentos automaticamente
    public Sala cadastrarSala(Sala sala) {
        // Primeiro salva a sala
        Sala novaSala = salaRepository.save(sala);
        
        // Gerar os assentos
        gerarAssentos(novaSala);
        
        return novaSala;
    }

    // Atualizar sala e ajustar os assentos
    public Sala atualizarSala(Integer id, Sala salaAtualizada) {
        return salaRepository.findById(id).map(sala -> {
            sala.setNumeroSala(salaAtualizada.getNumeroSala());
            sala.setQtdCadeiras(salaAtualizada.getQtdCadeiras());
            sala.setLinhas(salaAtualizada.getLinhas());
            sala.setColunas(salaAtualizada.getColunas());
            sala.setLadoEntrada(salaAtualizada.getLadoEntrada());

            // Atualizar assentos conforme as novas configurações
            assentoRepository.deleteBySalaId(sala.getId());
            gerarAssentos(sala);

            return salaRepository.save(sala);
        }).orElseThrow(() -> new RuntimeException("Sala não encontrada!"));
    }

    // Excluir sala e automaticamente remover assentos
    public void deletarSala(Integer id) {
        if (!salaRepository.existsById(id)) {
            throw new RuntimeException("Sala não encontrada!");
        }
        
        assentoRepository.deleteBySalaId(id); // Remover os assentos antes de deletar a sala
        salaRepository.deleteById(id);
    }

    // Método para gerar assentos automaticamente ao criar ou atualizar uma sala
    private void gerarAssentos(Sala sala) {
        int qtdLinhas = sala.getLinhas();
        int qtdColunas = sala.getColunas();
        
        for (char linha = 'A'; linha < 'A' + qtdLinhas; linha++) {
            for (int coluna = 1; coluna <= qtdColunas; coluna++) {
                Assento assento = new Assento();
                assento.setSala(sala);
                assento.setLinha(linha);
                assento.setColuna(coluna);
                assento.setOcupado(false);
                assentoRepository.save(assento);
            }
        }
    }
}
