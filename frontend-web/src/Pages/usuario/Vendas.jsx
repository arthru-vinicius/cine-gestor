import React from 'react';
import { Link } from 'react-router-dom';
import { Card } from 'react-bootstrap';

// Array de filmes para exibição
export const filmes = [
  { titulo: 'Wicked', imagem: require('../../Imagens/wicked.png'), descricao: 'Filme 1' },
  { titulo: 'Herege', imagem: require('../../Imagens/herege.jpg'), descricao: 'Filme 2' },
  { titulo: 'Aquaman 2', imagem: require('../../Imagens/aquaman2.jpg'), descricao: 'Filme 3' },
  { titulo: 'Vingadores', imagem: require('../../Imagens/vingadores.jpg'), descricao: 'Filme 4' },
  { titulo: 'Batman', imagem: require('../../Imagens/batman.jpg'), descricao: 'Filme 5' },
  { titulo: 'Operação Natal', imagem: require('../../Imagens/operacaonatal.jpg'), descricao: 'Filme 6' },
  { titulo: 'Venom', imagem: require('../../Imagens/venom.jpg'), descricao: 'Filme 7' },
  { titulo: 'Ainda estou aqui', imagem: require('../../Imagens/aindaestouaqui.jpg'), descricao: 'Filme 8' },
];

const Vendas = () => {
  return (
    <div className="row">
      {filmes.map((filme, index) => (
        <div className="col-3" key={index}>
          <Card variant="dark" style={{ width: '50%', backgroundColor: '#21262D', height: 'auto', marginTop: '40px' }}>
            <Card.Img variant="top" src={filme.imagem} style={{ width: '100%' }} />
            <Card.Body style={{ padding: '5px' }}>
              <Link to={`/vendas/${encodeURIComponent(filme.titulo)}`} style={{ textDecoration: 'none', color: 'white' }}>
                <Card.Title style={{ color: '#fff', fontSize: '0.9rem' }}>{filme.titulo}</Card.Title>
              </Link>
            </Card.Body>
          </Card>
        </div>
      ))}
    </div>
  );
};

export default Vendas;
