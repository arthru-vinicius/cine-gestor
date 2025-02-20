import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { FaArrowRight } from "react-icons/fa";
import { GoPerson } from 'react-icons/go'; // Importando ícone de pessoa
import ProgressMenu from '../../Components/MenuSuperior/MenuProgresso';
import { SeatContainer, Seat, RowLabel, SeatLabel } from '../../Styles/VendaSala'; // Importando os estilos de assentos

const VendaSala = () => {
  const location = useLocation();
  const query = new URLSearchParams(location.search);
  const navigate = useNavigate(); // Hook de navegação para mudar de página

  const [currentStage, setCurrentStage] = useState(0);
  const [selecionados, setSelecionados] = useState([]);
  const [assentosReservados] = useState([5, 15, 23, 30]); // Exemplo de assentos reservados

  const filme = query.get('filme');
  const hora = query.get('hora');
  const tipo = query.get('tipo');
  const sala = query.get('sala');

  if (!filme || !hora || !tipo || !sala) {
    return <h1>Informações incompletas. Retorne para a página anterior.</h1>;
  }

  const handleSelecionarAssento = (index) => {
    setSelecionados((prevSelecionados) =>
      prevSelecionados.includes(index)
        ? prevSelecionados.filter((item) => item !== index)
        : [...prevSelecionados, index]
    );
  };

  const renderizarAssentos = () => {
    const totalAssentos = 40; // Total de assentos para esse exemplo
    const linhas = [];
    for (let i = 0; i < totalAssentos; i++) {
      const reservado = assentosReservados.includes(i);
      const selecionado = selecionados.includes(i);
      linhas.push(
        <Seat
          key={i}
          selected={selecionado}
          reserved={reservado}
          onClick={() => !reservado && handleSelecionarAssento(i)}
        >
          {!reservado && !selecionado ? (
            <SeatLabel><GoPerson size={30} /></SeatLabel> // Ícone de pessoa aumentado
          ) : (
            <SeatLabel>✔</SeatLabel> // Ícone de check quando o assento é selecionado
          )}
        </Seat>
      );
    }
    return linhas;
  };

  // Função para lidar com o redirecionamento para a próxima página de pagamento
  const irParaPagamento = () => {
    const numeroAssentosSelecionados = selecionados.length;

    if (numeroAssentosSelecionados === 0) {
      alert('Por favor, selecione ao menos um assento.');
      return;
    }

    // Redireciona para a página de pagamento, passando as informações por estado
    navigate('/venda-ingresso', {
      state: {
        filme,
        hora,
        tipo,
        sala,
        numeroAssentosSelecionados
      }
    });
  };

  return (
    <div style={{ padding: '20px' }}>
      {/* Menu de Progresso */}
      <ProgressMenu  currentStage={currentStage} />

      {/* Informações do Filme */}
      <div style={{ marginTop: '20px', marginLeft: '16%' }}>
        <h1>{filme}<FaArrowRight /> {hora} <FaArrowRight /> {tipo} <FaArrowRight /> {sala}</h1>
      </div>

      {/* Seção de escolha de cadeiras */}
      <div style={{ marginTop: '40px' }}>
        <h2>Selecione um Assento</h2>
        <SeatContainer>{renderizarAssentos()}</SeatContainer>
      </div>

      {/* Botão no lado direito */}
      <button 
        style={{
          position: 'fixed', // Mantém o botão fixo enquanto o usuário rola a página
          right: '20px', // Distância do lado direito
          bottom: '20px', // Distância do fundo
          padding: '10px 20px',
          backgroundColor: '#007BFF',
          color: '#FFF',
          border: 'none',
          borderRadius: '8px',
          cursor: 'pointer',
          fontSize: '16px',
          boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)'
        }}
        onClick={irParaPagamento} // Função de redirecionamento
      >
        Confirmar
      </button>
    </div>
  );
};

export default VendaSala;
