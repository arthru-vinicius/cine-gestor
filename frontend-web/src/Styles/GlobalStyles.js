// src/styles/GlobalStyles.js
import { createGlobalStyle } from 'styled-components';

const GlobalStyles = createGlobalStyle`
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  body {
    background-color: #041218; 
    font-family: 'Arial', sans-serif;
    color: white; 
    min-height: 100vh; 
  }
`;

export default GlobalStyles;
