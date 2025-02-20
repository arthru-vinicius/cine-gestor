import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Layout from './Components/Layout/Layout';
import Home from './Pages/usuario/Home';
import Vendas from './Pages/usuario/Vendas';
import VendasHorario from './Pages/usuario/VendasHorario';
import VendaSala from './Pages/usuario/VendaSala';
import VendaResumo from './Pages/usuario/VendaPagamento';
import VendaPagamento from './Pages/usuario/VendaIngresso';
import Finalizacao from './Pages/usuario/Finalizacao';
import Login from './Pages/admin/Login';
import AdminPage from './Pages/admin/AdminDashboard';

function App() {
  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={
            <Layout>
              <Home />
            </Layout>
          }
        />
        <Route
          path="/login"
          element={
            <Login />
          }
        />
          <Route
          path="/adm"
          element={
            <AdminPage />
          }
        />
        <Route
          path="/vendas"
          element={
            <Layout>
              <Vendas />
            </Layout>
          }
        />
        <Route path="/vendas/:filme" element={<Layout><VendasHorario /></Layout>} />
        <Route
          path="/venda-sala"
          element={
            <Layout>
              <VendaSala />
            </Layout>
          }
        />

        <Route
          path="/venda-pagamento"
          element={
            <Layout>
              <VendaResumo />
            </Layout>
          }
        />
        <Route
          path="/venda-ingresso"
          element={
            <Layout>
              <VendaPagamento />
            </Layout>
          }
        />
        <Route
          path="/finalizacao"
          element={
            <Layout>
              <Finalizacao />
            </Layout>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
