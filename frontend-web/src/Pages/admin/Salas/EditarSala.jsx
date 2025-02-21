// src/Pages/admin/Salas/EditarSala.jsx
import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import { Form, Button, Container } from 'react-bootstrap';

const EditarSala = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [sala, setSala] = useState({
    numeroSala: '',
    qtdCadeiras: '',
    linhas: '',
    colunas: ''
  });

  useEffect(() => {
    const fetchSala = async () => {
      try {
        const response = await api.get(`/api/salas/${id}`);
        setSala(response.data);
      } catch (error) {
        console.error('Erro ao buscar sala:', error);
      }
    };
    fetchSala();
  }, [id]);

  const handleChange = (e) => {
    setSala({ ...sala, [e.target.name]: e.target.value });
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await api.put(`/api/salas/${id}`, sala);
      // Após atualizar, redireciona para a listagem administrativa
      navigate('/admin/salas');
    } catch (error) {
      console.error('Erro ao atualizar sala:', error);
    }
  };

  const handleDelete = async () => {
    try {
      await api.delete(`/api/salas/${id}`);
      navigate('/adm/salas');
    } catch (error) {
      console.error('Erro ao excluir sala:', error);
    }
  };

  return (
    <Container style={{ marginTop: '20px' }}>
      <h2>Editar Sala</h2>
      <Form onSubmit={handleUpdate}>
        <Form.Group controlId="numeroSala">
          <Form.Label>Número da Sala</Form.Label>
          <Form.Control 
            type="text" 
            name="numeroSala" 
            value={sala.numeroSala} 
            onChange={handleChange} 
            required 
          />
        </Form.Group>

        <Form.Group controlId="qtdCadeiras">
          <Form.Label>Quantidade de Cadeiras</Form.Label>
          <Form.Control 
            type="number" 
            name="qtdCadeiras" 
            value={sala.qtdCadeiras} 
            onChange={handleChange} 
            required 
          />
        </Form.Group>

        <Form.Group controlId="linhas">
          <Form.Label>Linhas</Form.Label>
          <Form.Control 
            type="number" 
            name="linhas" 
            value={sala.linhas} 
            onChange={handleChange} 
            required 
          />
        </Form.Group>

        <Form.Group controlId="colunas">
          <Form.Label>Colunas</Form.Label>
          <Form.Control 
            type="number" 
            name="colunas" 
            value={sala.colunas} 
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

export default EditarSala;
