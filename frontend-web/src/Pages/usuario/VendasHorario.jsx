// src/Pages/Dashboard.jsx (ou VendasHorario.jsx, conforme sua estrutura)
import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Card } from 'react-bootstrap';
import { FaArrowRight } from "react-icons/fa";
import { TableWrapper, HeaderRow, HeaderCol, TimeRow, TimeCol } from '../../Styles/VendasHorario';
import api from '../../services/api';

const VendasHorario = () => {
  const { filme } = useParams();
  const navigate = useNavigate();
  const [horarios, setHorarios] = useState([]);
  const [imagemFilme, setImagemFilme] = useState('');

  useEffect(() => {
    const fetchSessions = async () => {
      try {
        // Busca todas as sessões agendadas
        const response = await api.get('/api/sessoes/agendadas');
        // Filtra as sessões para o filme que corresponde ao parâmetro (supondo que 'filme' é o título)
        const sessionsForFilm = response.data.filter(session => 
          session.filme && session.filme.titulo === filme
        );
        // Mapeia cada sessão para o formato desejado para exibição
        const mappedSessions = sessionsForFilm.map(session => {
          // Converte o campo "horario" para um formato "HH:mm"
          const date = new Date(session.horario);
          const hora = date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
          return {
            hora: hora,
            tipo: session.filme.tipo || 'Dublado', // ou outro campo se existir
            sala: session.sala ? session.sala.numero_sala : 'Sala não definida',
            sessaoId: session.id
          };
        });
        setHorarios(mappedSessions);
        // Define a imagem do filme se houver (assumindo que todas as sessões para esse filme possuem a mesma imagem)
        if (sessionsForFilm.length > 0 && sessionsForFilm[0].filme.imagemCaminho) {
          setImagemFilme(sessionsForFilm[0].filme.imagemCaminho);
        }
      } catch (error) {
        console.error('Erro ao buscar sessões:', error);
      }
    };
    fetchSessions();
  }, [filme]);

  const handleHorarioClick = (horarioItem) => {
    // Redireciona para a rota de escolha da sala, passando parâmetros via query string
    navigate(`/venda-sala?filme=${encodeURIComponent(filme)}&hora=${encodeURIComponent(horarioItem.hora)}&tipo=${encodeURIComponent(horarioItem.tipo)}&sala=${encodeURIComponent(horarioItem.sala)}&sessaoId=${horarioItem.sessaoId}`);
  };

  return (
    <div className="vendas-horario" style={{ marginTop: '5%' }}>
      <h1>Escolha o Horário para o Filme {filme}</h1>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        {/* Exibe a imagem do filme, se disponível */}
        {imagemFilme && (
          <Card style={{ width: '16rem', marginRight: '20px', marginTop: '10%' }}>
            <Card.Img variant="top" src={imagemFilme} style={{ height: 'auto', maxWidth: '100%' }} />
          </Card>
        )}
        {/* Tabela com os horários */}
        <div style={{ flexGrow: 1, marginLeft: '10%' }}>
          <h3 style={{ marginLeft: '20%' }}>{filme} <FaArrowRight /> Sexta-feira</h3>
          <TableWrapper>
            <tbody>
              {horarios.map((horarioItem, index) => (
                <TimeRow key={index} onClick={() => handleHorarioClick(horarioItem)} style={{ cursor: 'pointer' }}>
                  <TimeCol>{horarioItem.hora}</TimeCol>
                  <HeaderRow>
                    <HeaderCol>{horarioItem.tipo}</HeaderCol>
                  </HeaderRow>
                  <TimeCol>{horarioItem.sala}</TimeCol>
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
