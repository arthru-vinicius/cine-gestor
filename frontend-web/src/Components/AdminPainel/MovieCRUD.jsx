import React, { useState } from 'react';
import { Container, SubTitle, Input, Select, Button, List, ListItem } from './styles';

const MovieCRUD = () => {
  const [movieName, setMovieName] = useState('');
  const [movieType, setMovieType] = useState('dublado');
  const [movies, setMovies] = useState([]);

  const handleAddMovie = () => {
    if (!movieName) {
      alert('Por favor, insira o nome do filme.');
      return;
    }

    setMovies([...movies, { name: movieName, type: movieType }]);
    setMovieName('');
    setMovieType('dublado');
  };

  return (
    <Container>
      <SubTitle>Adicionar Filme</SubTitle>
      <Input
        type="text"
        value={movieName}
        onChange={(e) => setMovieName(e.target.value)}
        placeholder="Nome do Filme"
      />
      <Select
        value={movieType}
        onChange={(e) => setMovieType(e.target.value)}
      >
        <option value="dublado">Dublado</option>
        <option value="legendado">Legendado</option>
      </Select>
      <Button primary onClick={handleAddMovie}>
        Adicionar Filme
      </Button>

      <h3>Filmes Cadastrados</h3>
      <List>
        {movies.map((movie, index) => (
          <ListItem key={index}>
            {movie.name} - {movie.type === 'dublado' ? 'Dublado' : 'Legendado'}
          </ListItem>
        ))}
      </List>
    </Container>
  );
};

export default MovieCRUD;
