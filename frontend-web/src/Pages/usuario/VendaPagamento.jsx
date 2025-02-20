import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Button } from 'react-bootstrap';
import CardsInfor from '../../Components/CardsInfor/CardsInfor';
import ProgressMenu from '../../Components/MenuSuperior/MenuProgresso';

const VendaResumo = () => {
  const location = useLocation();
  const [currentStage, setCurrentStage] = useState(2);
  const navigate = useNavigate();
  const {
    filme,
    hora,
    tipo,
    sala,
    meiaEntrada,
    inteira,
  } = location.state || {};

  const [formaPagamento, setFormaPagamento] = useState(''); // Inicializando formaPagamento
  const [pagamentoFinalizado, setPagamentoFinalizado] = useState(false);

  const confirmarPagamento = () => {
    if (!formaPagamento) {
      alert('Por favor, selecione uma forma de pagamento.');
      return;
    }

    if (formaPagamento === 'pix') {
      setPagamentoFinalizado(true);
    } else {
      alert('Pagamento com cartão confirmado!');
      navigate('/finalizacao');
    }
  };

  return (
    <div style={{ padding: '20px', display: 'flex', flexDirection: 'column' }}>
      <h1 style={{ marginBottom: '20px' }}>Resumo da Venda</h1>
      <ProgressMenu style={{ marginTop: '10%' }} currentStage={currentStage} />
      <div style={{ display: 'flex', width: '100%', marginTop: '4%' }}>
        <div style={{ width: '48%' }}>
          <CardsInfor
            filme={filme}
            hora={hora}
            tipo={tipo}
            sala={sala}
            meiaEntrada={meiaEntrada}
            inteira={inteira}
          />
        </div>

        <div style={{ width: '48%', paddingLeft: '20px' }}>
          <div style={{ marginBottom: '20px' }}>
            <h2>Pagamento</h2>
            <p>Forma de pagamento: {formaPagamento === 'pix' ? 'PIX' : 'Cartão de Crédito'}</p>
          </div>

          <div style={{ marginTop: '20px' }}>
            <h2>Ingressos Selecionados</h2>
            <p>Meia-Entrada: {meiaEntrada}</p>
            <p>Inteira: {inteira}</p>
            <p>Total: {meiaEntrada + inteira} ingresso(s)</p>
          </div>

          {!pagamentoFinalizado ? (
            <>
              <div style={{ display: 'flex', gap: '20px', marginTop: '20px' }}>
                <Button
                  variant={formaPagamento === 'pix' ? 'success' : 'outline-success'}
                  onClick={() => setFormaPagamento('pix')}
                >
                  PIX
                </Button>
                <Button
                  variant={formaPagamento === 'cartao' ? 'primary' : 'outline-primary'}
                  onClick={() => setFormaPagamento('cartao')}
                >
                  Cartão de Crédito
                </Button>
              </div>
              <Button
                style={{
                  marginTop: '40%',
                  backgroundColor: '#007BFF',
                  color: '#FFF',
                  border: 'none',
                  padding: '10px 20px',
                  borderRadius: '8px',
                  display: 'block',
                  margin: '20px auto',
                }}
                onClick={confirmarPagamento}
              >
                Confirmar Pagamento
              </Button>
            </>
          ) : (
            <div style={{ marginTop: '20px', display: 'flex', flexDirection: 'column' }}>
              <h2>Pagamento via PIX</h2>
              <p>Leia o QR Code abaixo para finalizar o pagamento:</p>
              <img
                src="./qrcode.svg"
                alt="QR Code"
                style={{ border: '1px solid #ccc', borderRadius: '8px', width: '40%' }}
              />
              <p style={{fontSize:'0.8rem', marginTop:'5%'}}>00020126580014BR.GOV.BCB.PIX0114+5521987654320215204000053039865802BR5913Nome Fictício6009Sao Paulo62070503***6304ABCD
              </p>
              <Button
                style={{
                  marginTop: '20px',
                  backgroundColor: '#28a745',
                  color: '#FFF',
                  border: 'none',
                  padding: '10px 20px',
                  borderRadius: '8px', width: '40%'
                }}
                onClick={() => navigate('/finalizacao')}
              >
                Concluir
              </Button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default VendaResumo;
