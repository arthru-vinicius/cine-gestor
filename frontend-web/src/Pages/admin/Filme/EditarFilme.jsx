// src/Pages/admin/Filmes/EditarFilme.jsx
import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import { Form, Button, Container } from 'react-bootstrap';

const EditarFilme = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [filme, setFilme] = useState({
    titulo: '',
    sinopse: '',
    duracao: '',
    classificacaoEtaria: '',
    imagem_caminho: '',
  });
  const [novaImagem, setNovaImagem] = useState(null);

  useEffect(() => {
    const fetchFilme = async () => {
      try {
        const response = await api.get(`/api/filmes/${id}`);
        setFilme(response.data);
      } catch (error) {
        console.error('Erro ao buscar filme:', error);
      }
    };
    fetchFilme();
  }, [id]);

  const handleChange = (e) => {
    setFilme({ ...filme, [e.target.name]: e.target.value });
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      // Se uma nova imagem for selecionada, você pode optar por enviar via FormData
      if (novaImagem) {
        const formData = new FormData();
        formData.append('titulo', filme.titulo);
        formData.append('sinopse', filme.sinopse);
        formData.append('duracao', filme.duracao);
        formData.append('classificacaoEtaria', filme.classificacaoEtaria);
        formData.append('imagem', novaImagem);
        await api.put(`/api/filmes/${id}`, formData);
      } else {
        await api.put(`/api/filmes/${id}`, filme);
      }
      navigate('/adm/filmes');
    } catch (error) {
      console.error('Erro ao atualizar filme:', error);
    }
  };

  const handleDelete = async () => {
    try {
      await api.delete(`/api/filmes/${id}`);
      navigate('/admin/filmes');
    } catch (error) {
      console.error('Erro ao excluir filme:', error);
    }
  };

  return (
    <Container style={{ marginTop: '20px' }}>
      <h2>Editar Filme</h2>
      <Form onSubmit={handleUpdate}>
        <Form.Group controlId="titulo">
          <Form.Label>Título</Form.Label>
          <Form.Control
            type="text"
            name="titulo"
            value={filme.titulo}
            onChange={handleChange}
            required
          />
        </Form.Group>
        <Form.Group controlId="sinopse">
          <Form.Label>Sinopse</Form.Label>
          <Form.Control
            as="textarea"
            rows={3}
            name="sinopse"
            value={filme.sinopse}
            onChange={handleChange}
          />
        </Form.Group>
        <Form.Group controlId="duracao">
          <Form.Label>Duração</Form.Label>
          <Form.Control
            type="text"
            name="duracao"
            placeholder="Ex: 1h 55m"
            value={filme.duracao}
            onChange={handleChange}
            required
          />
        </Form.Group>
        <Form.Group controlId="classificacaoEtaria">
          <Form.Label>Classificação Etária</Form.Label>
          <Form.Control
            type="text"
            name="classificacaoEtaria"
            value={filme.classificacaoEtaria}
            onChange={handleChange}
            required
          />
        </Form.Group>
        <Form.Group controlId="imagem">
          <Form.Label>Imagem (opcional)</Form.Label>
          <Form.Control
            type="file"
            onChange={(e) => setNovaImagem(e.target.files[0])}
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

export default EditarFilme;
