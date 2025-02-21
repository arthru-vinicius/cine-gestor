// src/Pages/admin/Sessoes/EditarSessao.jsx
import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import { Form, Button, Container } from 'react-bootstrap';

const EditarSessao = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [sessao, setSessao] = useState({
    filmeId: '',
    salaId: '',
    data: '',
    hora: '',
    status: ''
  });
  const [filmes, setFilmes] = useState([]);
  const [salas, setSalas] = useState([]);

  useEffect(() => {
    const fetchDados = async () => {
      try {
        // Buscar os dados da sessão
        const response = await api.get(`/api/sessoes/${id}`);
        const sessaoData = response.data;
        // Carregar os filmes e salas para os selects
        const resFilmes = await api.get('/api/filmes');
        const resSalas = await api.get('/api/salas');
        setFilmes(resFilmes.data);
        setSalas(resSalas.data);
        // Converter o horário para data e hora separadas
        const dt = new Date(sessaoData.horario);
        setSessao({
          filmeId: sessaoData.filme?.id || '',
          salaId: sessaoData.sala?.id || '',
          data: dt.toISOString().split('T')[0],
          hora: dt.toTimeString().split(' ')[0].slice(0,5), // HH:mm
          status: sessaoData.status
        });
      } catch (error) {
        console.error('Erro ao buscar sessão:', error);
      }
    };
    fetchDados();
  }, [id]);

  const handleChange = (e) => {
    setSessao({ ...sessao, [e.target.name]: e.target.value });
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      const horario = new Date(`${sessao.data}T${sessao.hora}`);
      const sessaoAtualizada = {
        filme: { id: parseInt(sessao.filmeId, 10) },
        sala: { id: parseInt(sessao.salaId, 10) },
        horario: horario.toISOString(),
        status: sessao.status
      };
      await api.put(`/api/sessoes/${id}`, sessaoAtualizada);
      navigate('/admin/sessoes');
    } catch (error) {
      console.error('Erro ao atualizar sessão:', error);
    }
  };

  const handleDelete = async () => {
    try {
      await api.delete(`/api/sessoes/${id}`);
      navigate('/adm/sessoes');
    } catch (error) {
      console.error('Erro ao excluir sessão:', error);
    }
  };

  return (
    <Container style={{ marginTop: '20px' }}>
      <h2>Editar Sessão</h2>
      <Form onSubmit={handleUpdate}>
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
          Atualizar
        </Button>
        <Button variant="danger" onClick={handleDelete} style={{ marginTop: '10px', marginLeft: '10px' }}>
          Excluir
        </Button>
      </Form>
    </Container>
  );
};

export default EditarSessao;
