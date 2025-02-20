// src/Components/Menu/Sidebar.jsx
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { FaTicketAlt, FaHome, FaShoppingCart, FaEdit, FaSignOutAlt, FaBars,FaTimes } from 'react-icons/fa';

import { SidebarContainer, Logo, MenuItem, MenuBottom, MenuButton } from './styled'; // Importando estilos

const Sidebar = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false); // Estado para controlar se o menu está aberto
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
      navigate('/login'); // Redireciona para a tela de login
    }
  };

  return (
    <>
      {/* Botão de hambúrguer para telas pequenas */}
      <MenuButton onClick={toggleMenu}>
        <FaBars />
      </MenuButton>

      {/* Sidebar com menu */}
      <SidebarContainer isMenuOpen={isMenuOpen}>
        <Logo>
          <FaTicketAlt />
        </Logo>

        {/* Menu principal */}
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

        {/* Menu inferior */}
        <MenuBottom>
          <MenuItem as={Link} to="/editar-dados">
            <FaEdit />
            Editar Dados
          </MenuItem>
          <MenuItem as="button" onClick={handleLogout} style={{ border: 'none', background: 'none', cursor: 'pointer' }}>
            <FaSignOutAlt />
            Sair
          </MenuItem>
        </MenuBottom>
      </SidebarContainer>
    </>
  );
};

export default Sidebar;
