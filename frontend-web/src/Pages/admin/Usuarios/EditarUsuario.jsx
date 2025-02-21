// src/Pages/admin/Usuarios/EditarUsuario.jsx
import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import { Form, Button, Container } from 'react-bootstrap';

const EditarUsuario = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [usuario, setUsuario] = useState({
    nomeCompleto: '',
    cpf: '',
    contato: '',
    login: '',
    senha: '',
    tipo: ''
  });

  useEffect(() => {
    const fetchUsuario = async () => {
      try {
        const response = await api.get(`/api/usuarios/${id}`);
        setUsuario(response.data);
      } catch (error) {
        console.error('Erro ao buscar usuário:', error);
      }
    };
    fetchUsuario();
  }, [id]);

  const handleChange = (e) => {
    setUsuario({ ...usuario, [e.target.name]: e.target.value });
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await api.put(`/api/usuarios/${id}`, usuario);
      navigate('/admin/usuarios');
    } catch (error) {
      console.error('Erro ao atualizar usuário:', error);
    }
  };

  const handleDelete = async () => {
    try {
      await api.delete(`/api/usuarios/${id}`);
      navigate('/adm/usuarios');
    } catch (error) {
      console.error('Erro ao excluir usuário:', error);
    }
  };

  return (
    <Container style={{ marginTop: '20px' }}>
      <h2>Editar Usuário</h2>
      <Form onSubmit={handleUpdate}>
        <Form.Group controlId="nomeCompleto">
          <Form.Label>Nome Completo</Form.Label>
          <Form.Control 
            type="text" 
            name="nomeCompleto" 
            value={usuario.nomeCompleto} 
            onChange={handleChange} 
            required 
          />
        </Form.Group>
        <Form.Group controlId="cpf">
          <Form.Label>CPF</Form.Label>
          <Form.Control 
            type="text" 
            name="cpf" 
            value={usuario.cpf} 
            onChange={handleChange} 
            required 
          />
        </Form.Group>
        <Form.Group controlId="contato">
          <Form.Label>Contato</Form.Label>
          <Form.Control 
            type="text" 
            name="contato" 
            value={usuario.contato} 
            onChange={handleChange} 
          />
        </Form.Group>
        <Form.Group controlId="login">
          <Form.Label>Login</Form.Label>
          <Form.Control 
            type="text" 
            name="login" 
            value={usuario.login} 
            onChange={handleChange} 
            required 
          />
        </Form.Group>
        <Form.Group controlId="senha">
          <Form.Label>Senha</Form.Label>
          <Form.Control 
            type="password" 
            name="senha" 
            value={usuario.senha} 
            onChange={handleChange} 
            required 
          />
        </Form.Group>
        <Form.Group controlId="tipo">
          <Form.Label>Tipo de Usuário</Form.Label>
          <Form.Select name="tipo" value={usuario.tipo} onChange={handleChange}>
            <option value="administrador">administrador</option>
            <option value="funcionario">funcionario</option>
          </Form.Select>
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

export default EditarUsuario;
