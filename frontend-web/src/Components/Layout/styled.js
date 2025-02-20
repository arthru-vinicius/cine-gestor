
import styled from 'styled-components';


export const MainContainer = styled.div`
  display: flex;
  min-height: 100vh;
  transition: margin-left 0.3s;
  background-color: #041218; 
  color: #fff;
  @media (max-width: 768px) {
    flex-direction: column;
  }
`;


export const ContentArea = styled.div`
  flex: 1;
  padding: 20px;
  transition: margin-left 0.3s;
  margin-left: 250px; 
  background-color: #041218; 
  color: #fff;
  @media (max-width: 768px) {
    margin-left: 0;
    text-align: center;
  }
`;
