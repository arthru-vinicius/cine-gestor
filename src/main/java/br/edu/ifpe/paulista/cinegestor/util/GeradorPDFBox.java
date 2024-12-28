package br.edu.ifpe.paulista.cinegestor.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import br.edu.ifpe.paulista.cinegestor.model.Ingresso;

import java.io.IOException;

public class GeradorPDFBox {

    public static void gerarPDF(String caminhoArquivo, String conteudo) {
        try (PDDocument document = new PDDocument()) {
            // Criar uma nova página
            PDPage page = new PDPage();
            document.addPage(page);

            // Escrever conteúdo na página
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText(conteudo);
            contentStream.endText();
            contentStream.close();

            // Salvar o documento
            document.save(caminhoArquivo);
            System.out.println("PDF gerado com sucesso em: " + caminhoArquivo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void gerarPDF(Ingresso ingresso, String caminhoArquivo) {
        try (PDDocument document = new PDDocument()) {
            // Criar uma nova página
            PDPage page = new PDPage();
            document.addPage(page);

            // Escrever conteúdo na página
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);

            // Adicionar conteúdo
            contentStream.showText("Ingresso #" + ingresso.getId());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Filme: " + ingresso.getNomeFilme());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Horário: " + ingresso.getHorarioSessao());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Sala: " + ingresso.getNomeSala());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Assento: " + ingresso.getAssento());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Preço: R$ " + ingresso.getPrecoPago());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Tipo: " + ingresso.getTipo());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Data da Venda: " + ingresso.getDataVenda());

            contentStream.endText();
            contentStream.close();

            // Salvar o documento
            document.save(caminhoArquivo);
            System.out.println("PDF do ingresso gerado com sucesso: " + caminhoArquivo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
