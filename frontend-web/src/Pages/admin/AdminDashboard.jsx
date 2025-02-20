import React from 'react';
import AdminPanel from '../../Components/AdminPainel/AdminPainel'; // Importa o painel administrativo

const AdminPage = () => {
  return (
    <div style={{ backgroundColor: '#2c3e50', color: '#ecf0f1', minHeight: '100vh' }}>
      <AdminPanel />
    </div>
  );
};

export default AdminPage;
