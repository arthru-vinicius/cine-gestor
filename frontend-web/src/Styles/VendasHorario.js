import styled from 'styled-components';

// Componente para a tabela
export const TableWrapper = styled.table`
  width: 70%;
  margin-top: 20px;
  border-collapse: collapse;
  background-color: #222;
  color: white;
`;

// Cabeçalho da tabela
export const HeaderRow = styled.div`
  color: white;
  border-radius: 5px;
  margin-top: 3%;
  margin-left: 30%;
`;

export const HeaderCol = styled.th`
  font-weight: bold;
  font-size: 0.7rem;
  color: #ffffff;
  cursor: pointer;
  background-color: rgb(22, 32, 216);
  border-radius: 3px;
  padding: 3px 10px;
  margin-top: 10%;
  text-align: center;
margin-left: 18px;

`;


export const TimeRow = styled.tr`
  border-bottom: 1px solid #444;
`;

export const TimeCol = styled.td`
  padding: 10px;
  text-align: center;
  cursor: pointer;
`;




