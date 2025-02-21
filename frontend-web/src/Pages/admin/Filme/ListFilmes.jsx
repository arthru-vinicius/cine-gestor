// src/Pages/admin/Filmes/ListFilmes.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import { Button, Table, Container } from 'react-bootstrap';

const ListFilmes = () => {
  const [filmes, setFilmes] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchFilmes = async () => {
      try {
        const response = await api.get('/api/filmes');
        setFilmes(response.data);
      } catch (error) {
        console.error('Erro ao buscar filmes:', error);
      }
    };
    fetchFilmes();
  }, []);

  return (
    <Container style={{ marginTop: '20px' }}>
      <h2>Filmes Cadastrados</h2>
      <Button variant="primary" onClick={() => navigate('/admin/filmes/cadastrar')} style={{ marginBottom: '10px' }}>
        Cadastrar Filme
      </Button>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Título</th>
            <th>Duração</th>
            <th>Classificação Etária</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {filmes.map((filme) => (
            <tr key={filme.id}>
              <td>{filme.id}</td>
              <td>{filme.titulo}</td>
              <td>{filme.duracao}</td>
              <td>{filme.classificacaoEtaria}</td>
              <td>
                <Button variant="info" onClick={() => navigate(`/admin/filmes/editar/${filme.id}`)}>
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

export default ListFilmes;
