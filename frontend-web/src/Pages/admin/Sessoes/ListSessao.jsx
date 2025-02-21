// src/Pages/admin/Sessoes/ListSessao.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import { Button, Table, Container } from 'react-bootstrap';

const ListSessao = () => {
  const [sessoes, setSessoes] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchSessoes = async () => {
      try {
        const response = await api.get('/api/sessoes');
        setSessoes(response.data);
      } catch (error) {
        console.error('Erro ao buscar sessões:', error);
      }
    };
    fetchSessoes();
  }, []);

  return (
    <Container style={{ marginTop: '20px' }}>
      <h2>Sessões</h2>
      <Button
        variant="primary"
        onClick={() => navigate('/admin/sessoes/cadastrar')}
        style={{ marginBottom: '10px' }}
      >
        Cadastrar Sessão
      </Button>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Filme</th>
            <th>Sala</th>
            <th>Horário</th>
            <th>Status</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {sessoes.map((sessao) => (
            <tr key={sessao.id}>
              <td>{sessao.id}</td>
              <td>{sessao.filme?.titulo || 'N/A'}</td>
              <td>{sessao.sala?.numeroSala || 'N/A'}</td>
              <td>{new Date(sessao.horario).toLocaleString()}</td>
              <td>{sessao.status}</td>
              <td>
                <Button variant="info" onClick={() => navigate(`/admin/sessoes/editar/${sessao.id}`)}>
                  Detalhes
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </Container>
  );
};

export default ListSessao;
