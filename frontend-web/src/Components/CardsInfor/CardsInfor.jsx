import { useLocation } from 'react-router-dom';
import { Card } from 'react-bootstrap';

const CardsInfor = () => {
  const location = useLocation();
  const { filme = 'Filme não especificado', hora = 'Hora não disponível', tipo = 'Tipo não informado', sala = 'Sala não definida', numeroAssentosSelecionados = 0 } = location.state || {};

  // Mapeamento de imagens dos filmes
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

  // Seleção da imagem ou fallback padrão
  const imagemFilme = imagensFilmes[filme] || 'https://via.placeholder.com/180x270';

  return (
    <div style={{ display: 'flex', justifyContent: 'center', padding: '20px' }}>
      <Card 
        style={{ width: '78%', boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)', backgroundColor: '#21262D', color: '#fff' }}
      >
        <Card.Img 
          variant="top" 
          src={imagemFilme} 
          alt={`Poster do filme ${filme}`} 
          style={{ maxHeight: '400px', objectFit: 'cover' }} 
        />
        <Card.Body>
          <Card.Title>{filme}</Card.Title>
          <Card.Text>
            <p><strong>Hora:</strong> {hora}</p>
            <p><strong>Tipo:</strong> {tipo}</p>
            <p><strong>Sala:</strong> {sala}</p>
            <p><strong>Assentos:</strong> {numeroAssentosSelecionados}</p>
          </Card.Text>
        </Card.Body>
      </Card>
    </div>
  );
};

export default CardsInfor;
