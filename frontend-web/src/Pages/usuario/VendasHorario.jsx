import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Card } from 'react-bootstrap';
import { FaArrowRight } from "react-icons/fa";
import { TableWrapper, HeaderRow, HeaderCol, TimeRow, TimeCol, TimeColTipo } from '../../Styles/VendasHorario';

const horarios = [
  { hora: '14:00', tipo: 'Dublado', sala: 'Sala 1' },
  { hora: '16:00', tipo: 'Legendado', sala: 'Sala 2' },
  { hora: '18:00', tipo: 'Dublado', sala: 'Sala 3' },
  { hora: '20:00', tipo: 'VIP Legendado', sala: 'Sala 4' },
];

const VendasHorario = () => {
  const { filme } = useParams();
  const navigate = useNavigate();

  const imagensFilmes = {
    'Wicked': require('../../Imagens/wicked.png'),
    'Herege': require('../../Imagens/herege.jpg'),
    'Aquaman 2': require('../../Imagens/aquaman2.jpg'),
    'Vingadores': require('../../Imagens/vingadores.jpg'),
    'Batman': require('../../Imagens/batman.jpg'),
    'Operação Natal': require('../../Imagens/operacaonatal.jpg'),
    'Venom': require('../../Imagens/venom.jpg'),
    'Ainda estou aqui': require('../../Imagens/aindaestouaqui.jpg'),
  };

  const imagemFilme = imagensFilmes[filme];
  const handleHorarioClick = (horario) => {
    navigate(`/venda-sala?filme=Herege&hora=${horario.hora}&tipo=${horario.tipo}&sala=${horario.sala}`);
  };

  return (
    <div className="vendas-horario" style={{ marginTop: '5%' }}>
      <h1>Escolha o Horário para o Filme {filme}</h1>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        {/* Exibindo a imagem do filme */}
        <Card style={{ width: '16rem', marginRight: '20px', marginTop: '10%' }}>
          <Card.Img variant="top" src={imagemFilme} style={{ height: 'auto', maxWidth: '100%' }} />
        </Card>

        {/* Tabela com os horários */}
        <div style={{ flexGrow: 1, marginLeft: '10%' }}>
          <h3 style={{ marginLeft: '20%' }}>{filme} <FaArrowRight /> Sexta-feira </h3>
          <TableWrapper>
            <tbody>
              {horarios.map((horario, index) => (
                <TimeRow key={index} onClick={() => handleHorarioClick(horario)} style={{ cursor: 'pointer' }}>
                  <TimeCol>{horario.hora}</TimeCol>
                  <HeaderRow>
                    <HeaderCol>{horario.tipo}</HeaderCol>
                  </HeaderRow>
                  <TimeCol>{horario.sala}</TimeCol>
                </TimeRow>
              ))}
            </tbody>
          </TableWrapper>
        </div>
      </div>
    </div>
  );
};

export default VendasHorario;
