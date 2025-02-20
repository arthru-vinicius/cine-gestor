import styled from 'styled-components';

export const LoginContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #121212;
`;

export const LoginCard = styled.div`
  width: 300px;
  padding: 20px;
  background-color: #1e1e1e;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
`;

export const Title = styled.h2`
  text-align: center;
  color: #fff;
`;

export const Label = styled.label`
  display: block;
  color: #fff;
  margin-bottom: 5px;
`;

export const Input = styled.input`
  width: 100%;
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #333;
  background-color: #2e2e2e;
  color: #fff;
  font-size: 16px;
  margin-bottom: 15px;
`;

export const Button = styled.button`
  width: 100%;
  padding: 10px;
  background-color: #6200ee;
  color: #fff;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 15px;
`;
