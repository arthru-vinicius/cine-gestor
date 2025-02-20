// src/Components/Menu/styled.js
import styled from 'styled-components';

// Container do Sidebar
export const SidebarContainer = styled.div`
  width: 180px;
  height: 100vh;
  background-color: #21262D;
  color: white;
  display: flex;
  flex-direction: column;
  padding: 20px;
  position: fixed;
  top: 0;
  left: 0;
  transition: transform 0.3s ease-in-out;
  z-index: 1000;

  @media (max-width: 768px) {
    width: 200px; 
  }

  @media (max-width: 480px) {
    transform: ${({ isMenuOpen }) => (isMenuOpen ? 'translateX(0)' : 'translateX(-100%)')};
  }
`;

// Logo
export const Logo = styled.div`
  font-size: 2.5rem;
  margin-bottom: 20px;
  display: flex;
  justify-content: center;
`;

// Estilo para cada item de menu
export const MenuItem = styled.div`
  text-decoration: none;
  color: white;
  font-size: 1.2rem;
  margin: 15px 0;
  display: flex;
  align-items: center;
  cursor: pointer;

  &:hover {
    color: #1e90ff;
  }

  svg {
    margin-right: 10px;
  }
`;

// Menu inferior
export const MenuBottom = styled.div`
  margin-top: auto;
  margin-bottom: 20px;
`;

// Botão para abrir o menu lateral (hamburguer)
export const MenuButton = styled.div`
  display: none;
  cursor: pointer;

  @media (max-width: 768px) {
    display: block;
    font-size: 2rem;
    color: white;
    position: absolute;
    top: 20px;
    left: 20px;
  }
`;
