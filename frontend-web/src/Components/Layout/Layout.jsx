// src/Components/Layout/Layout.jsx
import React from 'react';
import Sidebar from '../Menu/SideBar'; // Importando o Sidebar
import { MainContainer, ContentArea } from './styled'; // Importando estilos

const Layout = ({ children }) => {
  return (
    <MainContainer>
      <Sidebar /> {/* Menu lateral */}
      <ContentArea>
        {children} {/* Conteúdo principal, passado como prop */}
      </ContentArea>
    </MainContainer>
  );
};

export default Layout;
