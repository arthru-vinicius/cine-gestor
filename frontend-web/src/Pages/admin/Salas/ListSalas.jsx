// src/Pages/admin/Salas/ListSalas.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import { Button, Table, Container } from 'react-bootstrap';

const ListSalas = () => {
  const [salas, setSalas] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchSalas = async () => {
      try {
        const response = await api.get('/api/salas');
        setSalas(response.data);
      } catch (error) {
        console.error('Erro ao buscar salas:', error);
      }
    };
    fetchSalas();
  }, []);

  return (
    <Container style={{ padding: '20px' }}>
      <h2>Salas</h2>
      <Button 
        variant="primary" 
        onClick={() => navigate('/admin/salas/cadastrar')} 
        style={{ marginBottom: '10px' }}
      >
        Cadastrar Sala
      </Button>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Número da Sala</th>
            <th>Quantidade de Cadeiras</th>
            <th>Linhas</th>
            <th>Colunas</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {salas.map((sala) => (
            <tr key={sala.id}>
              <td>{sala.id}</td>
              <td>{sala.numeroSala}</td>
              <td>{sala.qtdCadeiras}</td>
              <td>{sala.linhas}</td>
              <td>{sala.colunas}</td>
              <td>
                <Button 
                  variant="info" 
                  onClick={() => navigate(`/admin/salas/editar/${sala.id}`)}
                >
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

export default ListSalas;
