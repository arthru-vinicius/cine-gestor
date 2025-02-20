import React, { useState } from 'react';
import axios from 'axios';
import { LoginContainer, LoginCard, Title, Label, Input, Button } from '../../Styles/Login';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Mapeia "email" para "login" e "password" para "senha"
      const response = await axios.post('http://localhost:8080/api/auth/login', {
        login: email,
        senha: password
      });

      const { token, tipo } = response.data;
      // Armazena o token para uso posterior (ex: localStorage, context, etc.)
      localStorage.setItem('token', token);
      alert(`Login efetuado com sucesso! Tipo: ${tipo}`);

      // Redireciona para o dashboard
      history.push('/dashboard');

    } catch (err) {
      console.error(err);
      // Exibe a mensagem de erro retornada pelo backend (se houver)
      setError(err.response && err.response.data ? err.response.data : 'Erro na requisição de login.');
    }
  };

  return (
      <LoginContainer>
        <LoginCard>
          <Title>Login</Title>
          <form onSubmit={handleSubmit}>
            <div>
              <Label htmlFor="email">Email</Label>
              <Input
                  type="email"
                  id="email"
                  name="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
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

            {/* Exibe mensagem de erro, se houver */}
            {error && <p style={{ color: 'red' }}>{error}</p>}

            <Button type="submit">Entrar</Button>
          </form>
        </LoginCard>
      </LoginContainer>
  );
};

export default Login;
