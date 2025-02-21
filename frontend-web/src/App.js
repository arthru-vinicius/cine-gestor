import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import ProtectedRoute from './Components/ProtectedRoute';
import AdminRoute from './Components/AdminRoute';

// Páginas de usuário
import Home from './Pages/usuario/Home';
import Vendas from './Pages/usuario/Vendas';
import VendasHorario from './Pages/usuario/VendasHorario';
import VendaSala from './Pages/usuario/VendaSala';
import VendaResumo from './Pages/usuario/VendaPagamento';
import VendaPagamento from './Pages/usuario/VendaIngresso';
import Finalizacao from './Pages/usuario/Finalizacao';

// Páginas de admin
import Login from './Pages/Login';
import AdminPage from './Pages/admin/AdminDashboard';
import NotAuthorized from './Pages/usuario/NotAuthorized';

// CRUDs (Admin)
import ListSalas from './Pages/admin/Salas/ListSalas';
import CadastroSala from './Pages/admin/Salas/CadastroSala';
import EditarSala from './Pages/admin/Salas/EditarSala';
import Precos from './Pages/admin/Ingresso/Precos';
import ListUsuarios from './Pages/admin/Usuarios/ListUsuarios';
import CadastroUsuario from './Pages/admin/Usuarios/CadastroUsuario';
import EditarUsuario from './Pages/admin/Usuarios/EditarUsuario';
import ListFilmes from './Pages/admin/Filme/ListFilmes';
import CadastroFilme from './Pages/admin/Filme/CadastroFilme';
import EditarFilme from './Pages/admin/Filme/EditarFilme';
import ListSessao from './Pages/admin/Sessoes/ListSessao';
import CadastroSessao from './Pages/admin/Sessoes/CadastroSessao';
import EditarSessao from './Pages/admin/Sessoes/EditarSessao';
import Relatorio from './Pages/admin/Relatorio/Relatorio';

function App() {
  return (
    <Router>
      <Routes>
        {/* Redireciona a página inicial para /vendas */}
        <Route path="/" element={<Navigate to="/vendas" />} />

        <Route path="/login" element={<Login />} />
        <Route path="/not-authorized" element={<NotAuthorized />} />

        {/* Rotas protegidas para usuários */}
        <Route path="/vendas" element={<ProtectedRoute><Vendas /></ProtectedRoute>} />
        <Route path="/vendas/:filme" element={<ProtectedRoute><VendasHorario /></ProtectedRoute>} />
        <Route path="/venda-sala" element={<ProtectedRoute><VendaSala /></ProtectedRoute>} />
        <Route path="/venda-pagamento" element={<ProtectedRoute><VendaResumo /></ProtectedRoute>} />
        <Route path="/venda-ingresso" element={<ProtectedRoute><VendaPagamento /></ProtectedRoute>} />
        <Route path="/finalizacao" element={<ProtectedRoute><Finalizacao /></ProtectedRoute>} />

        {/* Rotas protegidas para administradores */}
        <Route path="/admin" element={<ProtectedRoute><AdminRoute><AdminPage /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/precos" element={<ProtectedRoute><AdminRoute><Precos /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/salas" element={<ProtectedRoute><AdminRoute><ListSalas /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/salas/cadastrar" element={<ProtectedRoute><AdminRoute><CadastroSala /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/salas/editar/:id" element={<ProtectedRoute><AdminRoute><EditarSala /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/filmes" element={<ProtectedRoute><AdminRoute><ListFilmes /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/filmes/cadastrar" element={<ProtectedRoute><AdminRoute><CadastroFilme /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/filmes/editar/:id" element={<ProtectedRoute><AdminRoute><EditarFilme /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/usuarios" element={<ProtectedRoute><AdminRoute><ListUsuarios /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/usuarios/cadastrar" element={<ProtectedRoute><AdminRoute><CadastroUsuario /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/usuarios/editar/:id" element={<ProtectedRoute><AdminRoute><EditarUsuario /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/sessoes" element={<ProtectedRoute><AdminRoute><ListSessao /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/sessoes/cadastrar" element={<ProtectedRoute><AdminRoute><CadastroSessao /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/sessoes/editar/:id" element={<ProtectedRoute><AdminRoute><EditarSessao /></AdminRoute></ProtectedRoute>} />
        <Route path="/admin/relatorio" element={<ProtectedRoute><AdminRoute><Relatorio /></AdminRoute></ProtectedRoute>} />
      </Routes>
    </Router>
  );
}

export default App;
