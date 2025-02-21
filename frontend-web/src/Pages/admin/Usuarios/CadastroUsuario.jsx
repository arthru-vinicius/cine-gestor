// src/Pages/admin/Usuarios/CadastroUsuario.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import { Form, Button, Container } from 'react-bootstrap';

const CadastroUsuario = () => {
  const [usuario, setUsuario] = useState({
    nomeCompleto: '',
    cpf: '',
    contato: '',
    login: '',
    senha: '',
    tipo: 'administrador' // valor default; pode ser "administrador" ou "funcionario"
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setUsuario({ ...usuario, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post('/api/usuarios', usuario);
      navigate('/admin/usuarios');
    } catch (error) {
      console.error('Erro ao cadastrar usuário:', error);
    }
  };

  return (
    <Container style={{ marginTop: '20px' }}>
      <h2>Cadastrar Usuário</h2>
      <Form onSubmit={handleSubmit}>
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
          Cadastrar
        </Button>
      </Form>
    </Container>
  );
};

export default CadastroUsuario;
