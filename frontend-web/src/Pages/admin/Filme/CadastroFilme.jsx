// src/Pages/admin/Filmes/CadastroFilme.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import { Form, Button, Container } from 'react-bootstrap';

const CadastroFilme = () => {
  const [titulo, setTitulo] = useState('');
  const [sinopse, setSinopse] = useState('');
  const [duracao, setDuracao] = useState('');
  const [classificacaoEtaria, setClassificacaoEtaria] = useState('');
  const [imagem, setImagem] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Usa FormData para enviar os dados com o arquivo
      const formData = new FormData();
      formData.append('titulo', titulo);
      formData.append('sinopse', sinopse);
      formData.append('duracao', duracao);
      formData.append('classificacaoEtaria', classificacaoEtaria);
      if (imagem) {
        formData.append('imagem', imagem);
      }
      await api.post('/api/filmes', formData);
      navigate('/admin/filmes');
    } catch (error) {
      console.error('Erro ao cadastrar filme:', error);
    }
  };

  return (
    <Container style={{ marginTop: '20px' }}>
      <h2>Cadastrar Filme</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="titulo">
          <Form.Label>Título</Form.Label>
          <Form.Control
            type="text"
            value={titulo}
            onChange={(e) => setTitulo(e.target.value)}
            required
          />
        </Form.Group>
        <Form.Group controlId="sinopse">
          <Form.Label>Sinopse</Form.Label>
          <Form.Control
            as="textarea"
            rows={3}
            value={sinopse}
            onChange={(e) => setSinopse(e.target.value)}
          />
        </Form.Group>
        <Form.Group controlId="duracao">
          <Form.Label>Duração</Form.Label>
          <Form.Control
            type="text"
            placeholder="Ex: 1h 55m"
            value={duracao}
            onChange={(e) => setDuracao(e.target.value)}
            required
          />
        </Form.Group>
        <Form.Group controlId="classificacaoEtaria">
          <Form.Label>Classificação Etária</Form.Label>
          <Form.Control
            type="text"
            value={classificacaoEtaria}
            onChange={(e) => setClassificacaoEtaria(e.target.value)}
            required
          />
        </Form.Group>
        <Form.Group controlId="imagem">
          <Form.Label>Imagem</Form.Label>
          <Form.Control
            type="file"
            onChange={(e) => setImagem(e.target.files[0])}
          />
        </Form.Group>
        <Button variant="primary" type="submit" style={{ marginTop: '10px' }}>
          Cadastrar
        </Button>
      </Form>
    </Container>
  );
};

export default CadastroFilme;
