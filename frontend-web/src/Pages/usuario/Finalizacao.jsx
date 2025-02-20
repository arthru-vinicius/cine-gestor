import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import ProgressMenu from '../../Components/MenuSuperior/MenuProgresso';

const Finalizacao = () => {
   const [currentStage, setCurrentStage] = useState(3);
  return (
    <div style={{
      display: 'flex',
      flexDirection: 'column',
      height: '100vh', // Isso vai garantir que o conteúdo ocupe toda a altura da tela
      padding: '20px',
    }}>
      <h1>Compra Finalizada</h1>
      <ProgressMenu currentStage={currentStage} />

      <div style={{ textAlign: 'center', marginTop: '20px', marginRight:'16%' }}>
        <h1>Pagamento Aprovado</h1>
        <h3>Venda Finalizada</h3>
      </div>
      <div style={{ marginTop: '20px', marginLeft:'33%' }}>
        <button style={{
          padding: '10px 20px',
          backgroundColor: '#007BFF',
          color: '#FFF',
          border: 'none',
          borderRadius: '8px',
          cursor: 'pointer',
        }}>
          IMPRIMIR COMPROVANTE
        </button>
      </div>
      <div style={{ marginTop: '20px',marginLeft:'35%' }}>
        <button style={{
          padding: '10px 20px',
          backgroundColor: '#28a745',
          color: '#FFF',
          border: 'none',
          borderRadius: '8px',
          cursor: 'pointer',
        }}>
          <Link to="/" style={{ color: 'inherit', textDecoration: 'none' }}>Voltar à tela inicial</Link>
        </button>
      </div>
    </div>
  );
};

export default Finalizacao;
