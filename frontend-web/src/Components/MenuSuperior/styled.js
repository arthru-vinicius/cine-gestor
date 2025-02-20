// src/Components/MenuSuperior/ProgressMenuStyles.js
import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
 margin-left: 25%;
  align-items: center;
  gap: 16px;
  margin-top: 5%;
`;

export const Circle = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 48px;
  height: 48px;
  border-radius: 50%;
  background-color: ${(props) => (props.active ? '#007BFF' : '#555')};
  color: white;
  font-size: 24px;
  transition: background-color 0.3s ease;
`;

export const Line = styled.div`
  width: 64px;
  height: 4px;
  background-color: ${(props) => (props.active ? '#007BFF' : '#555')};
  transition: background-color 0.3s ease;
`;
