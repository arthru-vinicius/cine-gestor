import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { LoginContainer, LoginCard, Title, Label, Input, Button } from '../Styles/Login';
import api from '../services/api';

const Login = () => {
  const [login, setLogin] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Envia login e senha conforme o backend espera
      const response = await api.post('/api/auth/login', { login, senha: password });
      const { token, tipo } = response.data;
      // Armazena token e papel do usuário
      localStorage.setItem('token', token);
      localStorage.setItem('userRole', tipo); 
      alert(`Login efetuado com sucesso! Tipo: ${tipo}`);
      // Redireciona para a rota protegida (ex: vendas)
      navigate('/vendas');
    } catch (err) {
      console.error(err);
      setError(err.response && err.response.data ? err.response.data : 'Erro na requisição de login.');
    }
  };
  

  return (
    <LoginContainer>
      <LoginCard>
        <Title>Login</Title>
        <form onSubmit={handleSubmit}>
          <div>
            <Label htmlFor="login">Login</Label>
            <Input
              type="text"          // Alterado para "text"
              id="login"
              name="login"
              value={login}
              onChange={(e) => setLogin(e.target.value)}
              required
            />
          </div>
          <div>
            <Label htmlFor="password">Senha</Label>
            <Input
              type="password"
              id="password"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          {error && <p style={{ color: 'red' }}>{error}</p>}
          <Button type="submit">Entrar</Button>
        </form>
      </LoginCard>
    </LoginContainer>
  );
};

export default Login;
