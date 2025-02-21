// src/Pages/admin/Ingresso/Precos.jsx
import React, { useEffect, useState } from 'react';
import api from '../../../services/api'; // ajuste o caminho conforme sua estrutura
import { Table, Button, Form, Container } from 'react-bootstrap';

const Precos = () => {
  const [precos, setPrecos] = useState([]);
  const [alteracoes, setAlteracoes] = useState({}); // para armazenar alterações temporárias

  // Carrega os preços ao montar o componente
  useEffect(() => {
    const fetchPrecos = async () => {
      try {
        const response = await api.get('/api/ingressos/precos');
        setPrecos(response.data);
      } catch (error) {
        console.error('Erro ao buscar preços:', error);
      }
    };
    fetchPrecos();
  }, []);

  // Atualiza o valor local ao digitar um novo preço
  const handleChange = (dia, value) => {
    setAlteracoes(prev => ({ ...prev, [dia]: value }));
  };

  // Salva as alterações: para cada dia que teve mudança, chama o endpoint PUT
  const handleSalvar = async () => {
    try {
      // Para cada dia alterado
      for (const precoObj of precos) {
        const dia = precoObj.diaSemana; // valor: 'domingo', 'segunda', etc.
        if (alteracoes[dia] && alteracoes[dia] !== precoObj.preco.toString()) {
          // Chama o endpoint PUT para atualizar
          await api.put('/api/ingressos/precos', null, {
            params: {
              dia,
              novoPreco: alteracoes[dia]
            }
          });
        }
      }
      // Após salvar, recarrega a lista de preços
      const response = await api.get('/api/ingressos/precos');
      setPrecos(response.data);
      setAlteracoes({});
      alert('Preços atualizados com sucesso!');
    } catch (error) {
      console.error('Erro ao atualizar preços:', error);
      alert('Erro ao atualizar preços');
    }
  };

  return (
    <Container style={{ marginTop: '20px' }}>
      <h2>Preços do Ingresso</h2>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Dia da Semana</th>
            <th>Preço Atual</th>
            <th>Novo Preço</th>
          </tr>
        </thead>
        <tbody>
          {precos.map((preco) => (
            <tr key={preco.id}>
              <td>{preco.diaSemana}</td>
              <td>{preco.preco}</td>
              <td>
                <Form.Control
                  type="number"
                  step="0.01"
                  placeholder="Novo preço"
                  value={alteracoes[preco.diaSemana] || ''}
                  onChange={(e) => handleChange(preco.diaSemana, e.target.value)}
                />
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
      <Button variant="success" onClick={handleSalvar}>
        Salvar Alterações
      </Button>
    </Container>
  );
};

export default Precos;
