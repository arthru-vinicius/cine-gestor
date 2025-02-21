// src/Components/ProtectedRoute.jsx
import React from 'react';
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ children }) => {
  // Verifica se há um token no localStorage (ou utilize seu método de autenticação)
  const token = localStorage.getItem('token');

  if (!token) {
    // Se não estiver autenticado, redireciona para /login
    return <Navigate to="/login" replace />;
  }

  // Se autenticado, renderiza o componente filho
  return children;
};

export default ProtectedRoute;
