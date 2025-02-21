// src/Components/AdminRoute.jsx
import React from 'react';
import { Navigate } from 'react-router-dom';

const AdminRoute = ({ children }) => {
  const userRole = localStorage.getItem('userRole');

  // Se não houver informação sobre o usuário, redireciona para o login.
  if (!userRole) {
    return <Navigate to="/login" replace />;
  }
  // Se o usuário não for administrador (considerando que o valor armazenado deve ser "administrador")
  if (userRole.toLowerCase() !== 'administrador') {
    return <Navigate to="/not-authorized" replace />;
  }
  return children;
};

export default AdminRoute;
