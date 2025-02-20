import styled from 'styled-components';

// Container principal
export const Container = styled.div`
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #121212; /* Fundo escuro */
  color: #e0e0e0; /* Texto claro para contraste */
  min-height: 100vh;
`;

// Títulos
export const Title = styled.h1`
  color: #f1f1f1; /* Título em branco suave */
  margin-bottom: 20px;
`;

export const SubTitle = styled.h2`
  color: #b3b3b3; /* Subtítulo mais suave */
`;

// Botões
export const TabButton = styled.button`
  padding: 10px 20px;
  margin-right: 10px;
  cursor: pointer;
  background-color: ${(props) => (props.active ? '#1E88E5' : '#333')}; /* Azul suave para o botão ativo */
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 16px;

  &:hover {
    background-color: #1976D2; /* Azul mais intenso no hover */
  }
`;

export const Button = styled.button`
  padding: 10px;
  background-color: ${(props) => (props.primary ? '#4CAF50' : '#6200EE')}; /* Verde ou roxo sofisticado */
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 100%;
  
  &:hover {
    background-color: ${(props) => (props.primary ? '#388E3C' : '#3700B3')}; /* Escurece a cor no hover */
  }
`;

// Inputs e selects
export const Input = styled.input`
  padding: 10px;
  margin-bottom: 10px;
  width: 100%;
  background-color: #333; /* Fundo escuro para os inputs */
  color: #e0e0e0; /* Texto claro nos inputs */
  border: 1px solid #555; /* Borda mais suave */
  border-radius: 5px;

  &:focus {
    outline: none;
    border-color: #1E88E5; /* Azul suave ao focar no input */
  }
`;

export const Select = styled.select`
  padding: 10px;
  margin-bottom: 10px;
  width: 100%;
  background-color: #333; /* Fundo escuro */
  color: #e0e0e0; /* Texto claro */
  border: 1px solid #555; /* Borda mais suave */
  border-radius: 5px;

  &:focus {
    outline: none;
    border-color: #1E88E5; /* Azul suave ao focar no select */
  }
`;

// Lista de itens
export const List = styled.ul`
  list-style: none;
  padding-left: 0;
`;

export const ListItem = styled.li`
  margin: 5px 0;
  color: #e0e0e0; /* Cor suave para itens da lista */
  font-size: 16px;
`;

// Links
export const Link = styled.a`
  color: #1E88E5; /* Azul suave para links */
  text-decoration: none;

  &:hover {
    text-decoration: underline; /* Sublinha o link no hover */
  }
`;
