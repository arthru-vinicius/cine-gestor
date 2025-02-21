// src/Pages/admin/Sessoes/CadastroSessao.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import { Form, Button, Container } from 'react-bootstrap';

const CadastroSessao = () => {
  const [filmes, setFilmes] = useState([]);
  const [salas, setSalas] = useState([]);
  const [sessao, setSessao] = useState({
    filmeId: '',
    salaId: '',
    data: '',
    hora: ''
  });
  const navigate = useNavigate();

  useEffect(() => {
    const fetchDados = async () => {
      try {
        const resFilmes = await api.get('/api/filmes');
        setFilmes(resFilmes.data);
        const resSalas = await api.get('/api/salas');
        setSalas(resSalas.data);
      } catch (error) {
        console.error('Erro ao carregar dados:', error);
      }
    };
    fetchDados();
  }, []);

  const handleChange = (e) => {
    setSessao({ ...sessao, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Combina data e hora em um datetime ISO
      const horario = new Date(`${sessao.data}T${sessao.hora}`);
      // Monta o objeto Sessao conforme esperado pelo backend
      const novaSessao = {
        filme: { id: parseInt(sessao.filmeId, 10) },
        sala: { id: parseInt(sessao.salaId, 10) },
        horario: horario.toISOString()
        // O status será definido como "agendada" automaticamente se não enviado
      };
      await api.post('/api/sessoes', novaSessao);
      navigate('/admin/sessoes');
    } catch (error) {
      console.error('Erro ao cadastrar sessão:', error);
    }
  };

  return (
    <Container style={{ marginTop: '20px' }}>
      <h2>Cadastrar Sessão</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="filme">
          <Form.Label>Filme</Form.Label>
          <Form.Select name="filmeId" value={sessao.filmeId} onChange={handleChange} required>
            <option value="">Selecione um filme</option>
            {filmes.map((filme) => (
              <option key={filme.id} value={filme.id}>{filme.titulo}</option>
            ))}
          </Form.Select>
        </Form.Group>

        <Form.Group controlId="sala">
          <Form.Label>Sala</Form.Label>
          <Form.Select name="salaId" value={sessao.salaId} onChange={handleChange} required>
            <option value="">Selecione uma sala</option>
            {salas.map((sala) => (
              <option key={sala.id} value={sala.id}>{sala.numeroSala}</option>
            ))}
          </Form.Select>
        </Form.Group>

        <Form.Group controlId="data">
          <Form.Label>Data</Form.Label>
          <Form.Control 
            type="date" 
            name="data" 
            value={sessao.data} 
            onChange={handleChange} 
            required 
          />
        </Form.Group>

        <Form.Group controlId="hora">
          <Form.Label>Hora</Form.Label>
          <Form.Control 
            type="time" 
            name="hora" 
            value={sessao.hora} 
            onChange={handleChange} 
            required 
          />
        </Form.Group>

        <Button variant="primary" type="submit" style={{ marginTop: '10px' }}>
          Cadastrar Sessão
        </Button>
      </Form>
    </Container>
  );
};

export default CadastroSessao;
