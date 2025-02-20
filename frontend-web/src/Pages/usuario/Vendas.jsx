// src/Pages/Dashboard.jsx
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Dashboard = () => {
  const [filmes, setFilmes] = useState([]);

  useEffect(() => {
    const fetchFilmes = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/filmes/com-sessoes');
        setFilmes(response.data);
      } catch (error) {
        console.error('Erro ao buscar filmes:', error);
      }
    };
    fetchFilmes();
  }, []);

  return (
    <div className="row">
      {filmes.map((filme, index) => (
        <div className="col-3" key={index}>
          <Card variant="dark" style={{ width: '50%', backgroundColor: '#21262D', marginTop: '40px' }}>
            <Card.Img variant="top" src={filme.imagemCaminho} style={{ width: '100%' }} />
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

export default Dashboard;
