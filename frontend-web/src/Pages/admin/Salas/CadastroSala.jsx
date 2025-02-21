// src/Pages/admin/Salas/CadastroSala.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import { Form, Button, Container } from 'react-bootstrap';

const CadastroSala = () => {
  const [numeroSala, setNumeroSala] = useState('');
  const [qtdCadeiras, setQtdCadeiras] = useState('');
  const [linhas, setLinhas] = useState('');
  const [colunas, setColunas] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const sala = {
        numeroSala,
        qtdCadeiras: parseInt(qtdCadeiras, 10),
        linhas: parseInt(linhas, 10),
        colunas: parseInt(colunas, 10)
      };
      await api.post('/api/salas', sala);
      // Após cadastrar, redireciona para a listagem de salas (rota administrativa)
      navigate('/admin/salas');
    } catch (error) {
      console.error('Erro ao cadastrar sala:', error);
    }
  };

  return (
    <Container style={{ marginTop: '20px' }}>
      <h2>Cadastrar Sala</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="numeroSala">
          <Form.Label>Número da Sala</Form.Label>
          <Form.Control 
            type="text" 
            value={numeroSala} 
            onChange={(e) => setNumeroSala(e.target.value)} 
            required 
          />
        </Form.Group>

        <Form.Group controlId="qtdCadeiras">
          <Form.Label>Quantidade de Cadeiras</Form.Label>
          <Form.Control 
            type="number" 
            value={qtdCadeiras} 
            onChange={(e) => setQtdCadeiras(e.target.value)} 
            required 
          />
        </Form.Group>

        <Form.Group controlId="linhas">
          <Form.Label>Linhas</Form.Label>
          <Form.Control 
            type="number" 
            value={linhas} 
            onChange={(e) => setLinhas(e.target.value)} 
            required 
          />
        </Form.Group>

        <Form.Group controlId="colunas">
          <Form.Label>Colunas</Form.Label>
          <Form.Control 
            type="number" 
            value={colunas} 
            onChange={(e) => setColunas(e.target.value)} 
            required 
          />
        </Form.Group>

        <Button variant="primary" type="submit" style={{ marginTop: '10px' }}>
          Cadastrar
        </Button>
      </Form>
    </Container>
  );
};

export default CadastroSala;
