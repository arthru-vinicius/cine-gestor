// src/Components/VendaSala/VendaSalaStyles.js
import styled from 'styled-components';

export const SeatContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(10, 1fr); /* 10 assentos por linha */
  gap: 16px;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
`;

export const Seat = styled.div`
  width: 40px;
  height: 40px;
  border-radius: 5px;
  background-color: ${(props) =>
    props.selected ? '#007BFF' : props.reserved ? '#D3D3D3' : '#555'};
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: ${(props) => (props.reserved ? 'not-allowed' : 'pointer')};
  transition: background-color 0.3s ease;

  &:hover {
    background-color: ${(props) =>
      props.reserved ? '#D3D3D3' : '#0056b3'};
  }
`;

export const RowLabel = styled.div`
  text-align: center;
  margin-bottom: 10px;
  font-size: 14px;
  font-weight: bold;
  color: #fff;
`;

export const SeatLabel = styled.div`
  text-align: center;
  font-size: 12px;
  color: #fff;
  margin-top: 5px;
`;
