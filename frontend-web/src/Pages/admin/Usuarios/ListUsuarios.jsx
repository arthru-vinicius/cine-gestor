// src/Pages/admin/Usuarios/ListUsuarios.jsx
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import { Button, Table, Container } from 'react-bootstrap';

const ListUsuarios = () => {
  const [usuarios, setUsuarios] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUsuarios = async () => {
      try {
        const response = await api.get('/api/usuarios');
        setUsuarios(response.data);
      } catch (error) {
        console.error('Erro ao buscar usuários:', error);
      }
    };
    fetchUsuarios();
  }, []);

  return (
    <Container style={{ marginTop: '20px' }}>
      <h2>Usuários Cadastrados</h2>
      <Button 
        variant="primary" 
        onClick={() => navigate('/admin/usuarios/cadastrar')} 
        style={{ marginBottom: '10px' }}
      >
        Cadastrar Usuário
      </Button>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nome Completo</th>
            <th>CPF</th>
            <th>Contato</th>
            <th>Login</th>
            <th>Tipo</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {usuarios.map((usuario) => (
            <tr key={usuario.id}>
              <td>{usuario.id}</td>
              <td>{usuario.nomeCompleto}</td>
              <td>{usuario.cpf}</td>
              <td>{usuario.contato}</td>
              <td>{usuario.login}</td>
              <td>{usuario.tipo}</td>
              <td>
                <Button 
                  variant="info" 
                  onClick={() => navigate(`/admin/usuarios/editar/${usuario.id}`)}
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

export default ListUsuarios;
