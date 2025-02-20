// src/Components/MenuSuperior/ProgressMenu.jsx
import React from 'react';
import { FaChair, FaTicketAlt, FaCreditCard, FaCheckCircle } from 'react-icons/fa';
import { Container, Circle, Line } from './styled'; // Importando os estilos

const ProgressMenu = ({ currentStage }) => {
  const stages = [
    { label: 'Seleção de Assentos', icon: <FaChair />, path: '/venda-assentos' },
    { label: 'Ingressos', icon: <FaTicketAlt />, path: '/venda-ingressos' },
    { label: 'Pagamento', icon: <FaCreditCard />, path: '/venda-pagamento' },
    { label: 'Finalização', icon: <FaCheckCircle />, path: '/venda-finalizacao' },
  ];

  return (
    <Container>
      {stages.map((stage, index) => (
        <div key={stage.label} style={{ display: 'flex', alignItems: 'center' }}>
          <Circle active={index <= currentStage}>{stage.icon}</Circle>
          {index < stages.length - 1 && (
            <Line active={index < currentStage} />
          )}
        </div>
      ))}
    </Container>
  );
};

export default ProgressMenu;
