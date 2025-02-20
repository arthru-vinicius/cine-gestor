package br.edu.ifpe.paulista.cinegestor.service;

import br.edu.ifpe.paulista.cinegestor.model.*;
import br.edu.ifpe.paulista.cinegestor.repository.IngressoRepository;
import br.edu.ifpe.paulista.cinegestor.repository.PrecoIngressoRepository;
import br.edu.ifpe.paulista.cinegestor.repository.SessaoRepository;
import br.edu.ifpe.paulista.cinegestor.util.GeradorPDFBox;
import br.edu.ifpe.paulista.cinegestor.util.GerenciadorArquivos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Service
public class IngressoService {

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private PrecoIngressoRepository precoIngressoRepository;
    
    @Autowired
    private MockPagamentoService mockPagamentoService;
    
    @Autowired
    private GeradorPDFBox geradorPDFBox;

    @Autowired
    private GerenciadorArquivos gerenciadorArquivos;
    
    public List<Ingresso> listarIngressos() {
        return ingressoRepository.findAll();
    }
    
    @Transactional
    public void cancelarIngresso(Integer id) {
        Optional<Ingresso> ingressoOpt = ingressoRepository.findById(id);

        if (ingressoOpt.isPresent()) {
            Ingresso ingresso = ingressoOpt.get();
            Sessao sessao = ingresso.getSessao();

            // 🔄 Liberar o assento ocupado
            sessao.liberarAssento(ingresso.getAssento());
            sessaoRepository.save(sessao);

            // ❌ Excluir o ingresso do banco de dados
            ingressoRepository.deleteById(id);

            // 🗑 Excluir o PDF do ingresso armazenado
            String nomeArquivo = "ingresso_" + id + ".pdf";
            gerenciadorArquivos.excluirArquivo(nomeArquivo);

            System.out.println("Ingresso #" + id + " cancelado e assento liberado.");
        } else {
            throw new RuntimeException("Ingresso não encontrado.");
        }
    }

    @Transactional
    public Ingresso processarVenda(Sessao sessao, String assento, Ingresso.TipoIngresso tipo, Ingresso.MetodoPagamento metodo) throws IOException, WriterException {
        // Verifica se o assento já está ocupado
        if (sessao.getAssentosOcupados().contains(assento)) {
            throw new RuntimeException("O assento " + assento + " já está ocupado.");
        }

        // Busca o preço do ingresso baseado no dia da semana
        PrecoIngresso preco = buscarPreco(sessao.getHorario());
        BigDecimal precoPago = (tipo == Ingresso.TipoIngresso.MEIA)
                ? preco.getPreco().divide(BigDecimal.valueOf(2))
                : preco.getPreco();

        // Simular pagamento
        boolean pagamentoAprovado = mockPagamentoService.processarPagamento(precoPago.doubleValue(), metodo.name());

        if (!pagamentoAprovado) {
            throw new RuntimeException("Pagamento não aprovado. Tente novamente.");
        }

        // Atualiza a sessão ocupando o assento
        sessao.ocuparAssento(assento);
        sessaoRepository.save(sessao); 

        // Criar o ingresso
        Ingresso ingresso = new Ingresso();
        ingresso.setSessao(sessao);
        ingresso.setAssento(assento);
        ingresso.setTipo(tipo);
        ingresso.setPrecoIngresso(preco);
        ingresso.setPrecoPago(precoPago); // Agora registramos o valor pago
        ingresso.setDataVenda(LocalDateTime.now());
        ingresso.setMetodoPagamento(metodo);

        // Salvar ingresso no banco de dados
        ingresso = ingressoRepository.save(ingresso);

        // Gerar o PDF do ingresso
        String conteudo = """
            # Ingresso de Cinema
            **Filme:** %s
            **Sessão:** %s
            **Sala:** %s
            **Assento:** %s
            **Tipo:** %s
            **Valor Pago:** R$ %.2f
            **Pagamento:** %s
            """.formatted(
            sessao.getFilme().getTitulo(),
            sessao.getHorario(),
            sessao.getSala().getNumeroSala(),
            assento,
            tipo.name(),
            precoPago.doubleValue(),
            metodo.name()
        );

        byte[] pdf = geradorPDFBox.gerarIngressoPDF("Ingresso de Cinema", conteudo, String.valueOf(ingresso.getId()));
        String caminhoArquivo = gerenciadorArquivos.salvarIngresso("ingresso_" + ingresso.getId() + ".pdf", pdf);

        System.out.println("Ingresso salvo em: " + caminhoArquivo);
        return ingresso;
    }

    private PrecoIngresso buscarPreco(LocalDateTime horario) {
        DayOfWeek diaSemana = horario.getDayOfWeek();
        return precoIngressoRepository.findByDiaSemana(PrecoIngresso.DiaSemana.valueOf(diaSemana.name()))
                .orElseThrow(() -> new RuntimeException("Preço não encontrado para o dia da semana: " + diaSemana));
    }
}
