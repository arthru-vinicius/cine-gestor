import React, { useState } from 'react';
import { Container, SubTitle, Select, Input, Button, List, ListItem } from './styles';

const SessionCRUD = () => {
  const [selectedMovie, setSelectedMovie] = useState('');
  const [selectedTime, setSelectedTime] = useState('');
  const [selectedRoom, setSelectedRoom] = useState('');
  const [sessions, setSessions] = useState([]);

  const movies = [
    { name: 'Filme 1', type: 'dublado' },
    { name: 'Filme 2', type: 'legendado' },
  ];

  const rooms = ['Sala 1', 'Sala 2', 'Sala 3'];

  const handleAddSession = () => {
    if (!selectedMovie || !selectedTime || !selectedRoom) {
      alert('Por favor, preencha todos os campos.');
      return;
    }

    setSessions([...sessions, { movie: selectedMovie, time: selectedTime, room: selectedRoom }]);
    setSelectedMovie('');
    setSelectedTime('');
    setSelectedRoom('');
  };

  return (
    <Container>
      <SubTitle>Adicionar Sessão</SubTitle>
      <Select
        value={selectedMovie}
        onChange={(e) => setSelectedMovie(e.target.value)}
      >
        <option value="">Escolha um Filme</option>
        {movies.map((movie, index) => (
          <option key={index} value={movie.name}>
            {movie.name} - {movie.type === 'dublado' ? 'Dublado' : 'Legendado'}
          </option>
        ))}
      </Select>
      <Input
        type="time"
        value={selectedTime}
        onChange={(e) => setSelectedTime(e.target.value)}
      />
      <Select
        value={selectedRoom}
        onChange={(e) => setSelectedRoom(e.target.value)}
      >
        <option value="">Escolha uma Sala</option>
        {rooms.map((room, index) => (
          <option key={index} value={room}>
            {room}
          </option>
        ))}
      </Select>
      <Button primary onClick={handleAddSession}>
        Adicionar Sessão
      </Button>

      <h3>Sessões Cadastradas</h3>
      <List>
        {sessions.map((session, index) => (
          <ListItem key={index}>
            {session.movie} - {session.time} - {session.room}
          </ListItem>
        ))}
      </List>
    </Container>
  );
};

export default SessionCRUD;
