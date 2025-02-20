package br.edu.ifpe.paulista.cinegestor.util;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import com.google.zxing.WriterException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class GeradorPDFBox {

    public byte[] gerarRelatorioPDF(String titulo, String conteudo) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText(titulo);
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                float yPosition = 650;

                for (String line : conteudo.split("\n")) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(line);
                    contentStream.endText();
                    yPosition -= 20;
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }
    
    public byte[] gerarIngressoPDF(String titulo, String conteudo, String ingressoId) throws IOException, WriterException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText(titulo);
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                float yPosition = 650;

                for (String line : conteudo.split("\n")) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(line);
                    contentStream.endText();
                    yPosition -= 20;
                }

                // Gerando o QR Code com o ID do ingresso
                byte[] qrCodeBytes = QRCodeGenerator.generateQRCode(ingressoId);
                PDImageXObject qrImage = PDImageXObject.createFromByteArray(document, qrCodeBytes, "qrcode");

                // Adicionando o QR Code ao PDF
                contentStream.drawImage(qrImage, 400, 500, 100, 100);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }

}
