package br.edu.ifpe.paulista.cinegestor.controller;

import br.edu.ifpe.paulista.cinegestor.model.Ingresso;
import br.edu.ifpe.paulista.cinegestor.model.PrecoIngresso;
import br.edu.ifpe.paulista.cinegestor.model.Sessao;
import br.edu.ifpe.paulista.cinegestor.repository.PrecoIngressoRepository;
import br.edu.ifpe.paulista.cinegestor.service.IngressoService;
import br.edu.ifpe.paulista.cinegestor.service.PagamentoService;
import br.edu.ifpe.paulista.cinegestor.service.ControleCaixaService;
import br.edu.ifpe.paulista.cinegestor.repository.SessaoRepository;
import br.edu.ifpe.paulista.cinegestor.util.GerenciadorArquivos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/ingressos")
public class IngressoController {

    @Autowired
    private IngressoService ingressoService;

    @Autowired
    private ControleCaixaService controleCaixaService;
    
    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private SessaoRepository sessaoRepository;
    
    @Autowired
    private GerenciadorArquivos gerenciadorArquivos;

    @Autowired
    private PrecoIngressoRepository precoIngressoRepository;

    /**
     * Lista todos os ingressos vendidos.
     */
    @GetMapping
    public List<Ingresso> listarIngressos() {
        return ingressoService.listarIngressos();
    }

    /**
     * Compra um ingresso, verifica o pagamento e gera o PDF.
     * @throws WriterException 
     */
    @PostMapping("/comprar")
    public ResponseEntity<?> comprarIngresso(@RequestParam Integer sessaoId,
                                             @RequestParam String assento,
                                             @RequestParam Ingresso.TipoIngresso tipo,
                                             @RequestParam Ingresso.MetodoPagamento metodo) throws WriterException {
        try {
            // Buscar sessão associada
            Sessao sessao = sessaoRepository.findById(sessaoId)
                    .orElseThrow(() -> new RuntimeException("Sessão não encontrada!"));

            // Processar a venda e salvar ingresso no banco
            Ingresso ingresso = ingressoService.processarVenda(sessao, assento, tipo, metodo);
            return ResponseEntity.ok(ingresso);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro na compra do ingresso: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erro ao gerar o PDF do ingresso.");
        }
    }

    /**
     *  Cancela um ingresso e libera o assento da sessão.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarIngresso(@PathVariable Integer id) {
        ingressoService.cancelarIngresso(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     *  Permite baixar o ingresso em PDF.
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> baixarIngresso(@PathVariable Integer id) throws IOException {
        byte[] pdf = gerenciadorArquivos.carregarIngresso("ingresso_" + id + ".pdf");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    
    /**
     *  Retorna o total de dinheiro arrecadado no dia.
     */
    @GetMapping("/caixa")
    public ResponseEntity<BigDecimal> obterTotalDiario() {
        return ResponseEntity.ok(controleCaixaService.calcularTotalDiario());
    }
    
    /**
     *  Calcula o troco quando o pagamento é em dinheiro.
     */
    @PostMapping("/calcular-troco")
    public ResponseEntity<Double> calcularTroco(@RequestParam double valorPago, @RequestParam double valorIngresso) {
        double troco = pagamentoService.calcularTroco(valorPago, valorIngresso);
        return ResponseEntity.ok(troco);
    }

    // GET: Lista todos os preços dos ingressos
    @GetMapping("/precos")
    public List<PrecoIngresso> listarPrecos() {
        return precoIngressoRepository.findAll();
    }

    // PUT: Atualiza o preço de ingresso para um dia específico
    @PutMapping("/precos")
    public ResponseEntity<PrecoIngresso> atualizarPrecoIngresso(
            @RequestParam PrecoIngresso.DiaSemana dia,
            @RequestParam BigDecimal novoPreco) {
        try {
            PrecoIngresso precoAtualizado = ingressoService.atualizarPreco(dia, novoPreco);
            return ResponseEntity.ok(precoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
