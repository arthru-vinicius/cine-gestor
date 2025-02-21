// src/Pages/admin/Relatorio/Relatorio.jsx
import React, { useState } from 'react';
import api from '../../../services/api';
import { Button, Form, Container } from 'react-bootstrap';

const Relatorio = () => {
  const [tipo, setTipo] = useState('diario'); // Valor padrão
  const [pdfUrl, setPdfUrl] = useState('');

  const handleGenerate = async () => {
    try {
      // Chama o endpoint com o tipo escolhido e espera um blob (PDF)
      const response = await api.get(`/api/relatorios/${tipo}`, { responseType: 'blob' });
      // Cria uma URL a partir do blob recebido
      const url = window.URL.createObjectURL(new Blob([response.data], { type: 'application/pdf' }));
      setPdfUrl(url);
    } catch (error) {
      console.error('Erro ao gerar relatório:', error);
      alert('Erro ao gerar relatório');
    }
  };

  const handleDownload = () => {
    if (pdfUrl) {
      const link = document.createElement('a');
      link.href = pdfUrl;
      link.setAttribute('download', `relatorio_${tipo}.pdf`);
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    }
  };

  return (
    <Container style={{ marginTop: '20px' }}>
      <h2>Gerar Relatório</h2>
      <Form>
        <Form.Group controlId="tipoRelatorio">
          <Form.Label>Tipo de Relatório</Form.Label>
          <Form.Select value={tipo} onChange={(e) => setTipo(e.target.value)}>
            <option value="diario">Diário</option>
            <option value="mensal">Mensal</option>
            <option value="total">Total</option>
          </Form.Select>
        </Form.Group>
        <Button variant="primary" onClick={handleGenerate} style={{ marginTop: '10px' }}>
          Gerar Relatório
        </Button>
      </Form>

      {pdfUrl && (
        <div style={{ marginTop: '20px' }}>
          <h3>Visualização do Relatório</h3>
          <object data={pdfUrl} type="application/pdf" width="100%" height="600px">
            <p>Seu navegador não suporta exibição de PDF. Faça o download para visualizar.</p>
          </object>
          <Button variant="success" onClick={handleDownload} style={{ marginTop: '10px' }}>
            Download do Relatório
          </Button>
        </div>
      )}
    </Container>
  );
};

export default Relatorio;
