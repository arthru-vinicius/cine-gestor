// src/Components/Menu/Sidebar.jsx
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { 
  FaTicketAlt, 
  FaHome, 
  FaShoppingCart, 
  FaEdit, 
  FaSignOutAlt, 
  FaBars, 
  FaTimes,
  FaChair,
  FaFilm,
  FaCalendarAlt,
  FaUserAlt,
  FaMoneyBillWave,
  FaFileAlt
} from 'react-icons/fa';
import { SidebarContainer, Logo, MenuItem, MenuBottom, MenuButton } from './styled';

const Sidebar = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const navigate = useNavigate();

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const handleLogout = async () => {
    try {
      const token = localStorage.getItem('token');
      if (token) {
        await axios.post('http://localhost:8080/api/auth/logout', null, {
          headers: { Authorization: `Bearer ${token}` },
        });
      }
    } catch (error) {
      console.error('Erro ao fazer logout:', error);
    } finally {
      localStorage.removeItem('token');
      localStorage.removeItem('userRole');
      navigate('/login');
    }
  };

  // Verifica se o usuário é administrador (ajuste conforme o valor armazenado, ex.: "administrador")
  const userRole = localStorage.getItem('userRole');

  return (
    <>
      {/* Botão de hambúrguer para telas pequenas */}
      <MenuButton onClick={toggleMenu}>
        <FaBars />
      </MenuButton>

      <SidebarContainer isMenuOpen={isMenuOpen}>
        <Logo>
          <FaTicketAlt />
        </Logo>

        {/* Itens de menu para todos os usuários */}
        <MenuItem as={Link} to="/">
          <FaHome />
          Tela Inicial
        </MenuItem>
        <MenuItem as={Link} to="/vendas">
          <FaShoppingCart />
          Vendas
        </MenuItem>
        <MenuItem as={Link} to="/cancelar">
          <FaTimes />
          Cancelar
        </MenuItem>

        {/* Itens de menu exclusivos para administradores */}
        {userRole && userRole.toLowerCase() === 'administrador' && (
          <>
            <MenuItem as={Link} to="/admin/salas">
              <FaChair />
              Salas
            </MenuItem>
            <MenuItem as={Link} to="/admin/filmes">
              <FaFilm />
              Filmes
            </MenuItem>
            <MenuItem as={Link} to="/admin/sessoes">
              <FaCalendarAlt />
              Sessões
            </MenuItem>
            <MenuItem as={Link} to="/admin/usuarios">
              <FaUserAlt />
              Usuários
            </MenuItem>
            <MenuItem as={Link} to="/admin/precos">
              <FaMoneyBillWave />
              Preço do Ingresso
            </MenuItem>
            <MenuItem as={Link} to="/admin/relatorio">
              <FaFileAlt />
              Relatório
            </MenuItem>
          </>
        )}

        {/* Menu inferior */}
        <MenuBottom>
          <MenuItem as={Link} to="/editar-dados">
            <FaEdit />
            Editar Dados
          </MenuItem>
          <MenuItem
            as="button"
            onClick={handleLogout}
            style={{ border: 'none', background: 'none', cursor: 'pointer' }}
          >
            <FaSignOutAlt />
            Sair
          </MenuItem>
        </MenuBottom>
      </SidebarContainer>
    </>
  );
};

export default Sidebar;
