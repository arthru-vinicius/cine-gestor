import { useLocation, useNavigate } from 'react-router-dom';
import { Card, Button } from 'react-bootstrap';
import { useState } from 'react';
import { FaTicketAlt } from 'react-icons/fa';
import CardsInfor from '../../Components/CardsInfor/CardsInfor';
import ProgressMenu from '../../Components/MenuSuperior/MenuProgresso';

const VendaPagamento = () => {
  const location = useLocation();
  const query = new URLSearchParams(location.search);
  const navigate = useNavigate(); // Hook de navegação para mudar de página

const [currentStage, setCurrentStage] = useState(1);
  const [meiaEntrada, setMeiaEntrada] = useState(0);
  const [inteira, setInteira] = useState(0);

  // Funções para manipular os contadores
  const incrementarMeia = () => setMeiaEntrada((prev) => prev + 1);
  const decrementarMeia = () => setMeiaEntrada((prev) => Math.max(0, prev - 1));
  const incrementarInteira = () => setInteira((prev) => prev + 1);
  const decrementarInteira = () => setInteira((prev) => Math.max(0, prev - 1));

  const irParaPagamento = () => {
    const filme = query.get('filme');
    const hora = query.get('hora');
    const tipo = query.get('tipo');
    const sala = query.get('sala');

    // Soma total de ingressos
    const totalIngressos = meiaEntrada + inteira;

    if (totalIngressos === 0) {
      alert('Por favor, selecione ao menos um ingresso.');
      return;
    }

    // Redireciona para a página de pagamento, passando as informações por estado
    navigate('/venda-pagamento', {
      state: {
        filme,
        hora,
        tipo,
        sala,
        totalIngressos,
        meiaEntrada,
        inteira,
      },
    });
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1>Pagamento</h1>
      <ProgressMenu currentStage={currentStage} />
      <div style={{ display: 'flex', gap: '20px', marginTop: '40px' }}>
        {/* Card com informações do filme */}
        <CardsInfor />

        {/* Cards adicionais: Meia-Entrada e Inteira */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '20px', marginTop: '40px' }}>
          {/* Card de Meia-Entrada */}
          <Card
            style={{
              width: '38rem',
              boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
              backgroundColor: '#21262D',
              color: '#fff',
            }}
          >
            <Card.Body>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <Card.Title>
                    <FaTicketAlt /> Meia-Entrada
                  </Card.Title>
                  <Card.Text style={{ fontSize: '14px', color: 'gray' }}>R$ 23,25</Card.Text>
                </div>
                <div style={{ display: 'flex', alignItems: 'center', gap: '5px' }}>
                  <Button
                    variant="danger"
                    size="sm"
                    onClick={decrementarMeia}
                    style={{ width: '30px', height: '30px' }}
                  >
                    -
                  </Button>
                  <span style={{ fontSize: '14px', fontWeight: 'bold' }}>{meiaEntrada}</span>
                  <Button
                    variant="success"
                    size="sm"
                    onClick={incrementarMeia}
                    style={{ width: '30px', height: '30px' }}
                  >
                    +
                  </Button>
                </div>
              </div>
            </Card.Body>
          </Card>

          {/* Card de Inteira */}
          <Card
            style={{
              width: '38rem',
              boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
              backgroundColor: '#21262D',
              color: '#fff',
            }}
          >
            <Card.Body>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <Card.Title>
                    <FaTicketAlt /> Inteira
                  </Card.Title>
                  <Card.Text style={{ fontSize: '14px', color: 'gray' }}>R$ 46,50</Card.Text>
                </div>
                <div style={{ display: 'flex', alignItems: 'center', gap: '5px' }}>
                  <Button
                    variant="danger"
                    size="sm"
                    onClick={decrementarInteira}
                    style={{ width: '30px', height: '30px' }}
                  >
                    -
                  </Button>
                  <span style={{ fontSize: '14px', fontWeight: 'bold' }}>{inteira}</span>
                  <Button
                    variant="success"
                    size="sm"
                    onClick={incrementarInteira}
                    style={{ width: '30px', height: '30px' }}
                  >
                    +
                  </Button>
                </div>
              </div>
            </Card.Body>
          </Card>

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
              boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
            }}
            onClick={irParaPagamento}
          >
            Confirmar
          </button>
        </div>
      </div>
    </div>
  );
};

export default VendaPagamento;
