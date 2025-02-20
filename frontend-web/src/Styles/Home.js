// src/Styles/Home.js
import styled from 'styled-components';
import { Row, Col, Table } from 'react-bootstrap';

// Tabela estilizada
export const TableWrapper = styled(Table)`
  background-color: #21262D;
  border: 1px solid #ffffff;
  color: #ffffff;
  width: 100%;
  .table-home {
    background-color: #21262D;
    color: #fff;
    width: 100%;
  }
`;

// Cabeçalhos de "Dublado", "VIP", "Legendado"
export const HeaderRow = styled(Row)`
  margin-bottom: 8px;
  text-align: center;
`;

export const HeaderCol = styled(Col)`
  font-weight: bold;
  font-size: 0.7rem;
  color: #ffffff;
  background-color: rgb(22, 32, 216);
  border: 1px solid rgb(22, 32, 216);
  border-radius: 10px;
  padding: 3px;
  flex: 0 0 120px;
  text-align: center;
margin-left: 18px;
`;

// Linha de horários
export const TimeRow = styled(Row)`
  text-align: center;
  margin-left: 10px;
`;

export const TimeCol = styled(Col)`
  color: #ffffff;
  padding: 0px 20px;
  text-align: center;
  font-size: 1rem;
  border: 1px solid #d8ce16;
  background-color:rgb(59, 64, 72);
  border-radius:5px;
  &:not(:last-child) {
    margin-right: 15px;
  }
  flex: 0 0 120px; /* Tamanho fixo para as colunas de horários */
  min-width: 120px; /* Garante que os horários não fiquem menores que 120px */
`;

const TableHome = styled.div`
  .table-home th, .table-home td {
    padding: 0.75rem;
    text-align: center;
  }
`;

export default TableHome;
