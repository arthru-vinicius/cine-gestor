import React, { useState } from 'react';
import MovieCRUD from './MovieCRUD';
import SessionCRUD from './SessionCRUD';
import { Container, Title, TabButton } from './styles';

const AdminPanel = () => {
  const [selectedTab, setSelectedTab] = useState('movies');

  return (
    <Container>
      <Title>Painel Administrativo</Title>
      <div style={{ display: 'flex', marginBottom: '20px' }}>
        <TabButton
          active={selectedTab === 'movies'}
          onClick={() => setSelectedTab('movies')}
        >
          Gerenciar Filmes
        </TabButton>
        <TabButton
          active={selectedTab === 'sessions'}
          onClick={() => setSelectedTab('sessions')}
        >
          Gerenciar Sessões
        </TabButton>
      </div>

      {selectedTab === 'movies' ? <MovieCRUD /> : <SessionCRUD />}
    </Container>
  );
};

export default AdminPanel;
