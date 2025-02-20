// src/Components/Home/Home.js
import { Link } from 'react-router-dom';
import { Container, Row, Col } from 'react-bootstrap';
import TableHome, { TableWrapper, HeaderRow, HeaderCol, TimeRow, TimeCol } from '../../Styles/Home';

const Home = () => {
  const vendasRoute = "/vendas";

  return (
    <div>
      <h1>Tela Inicial</h1>
      <Link to={vendasRoute}>Ir para vendas</Link>

      <Container fluid style={{ height: '70vh' }} className="d-flex justify-content-center align-items-center">
        <Row className="text-center" style={{ width: '50%' }}>
          <Col>
            <h1>Programação - Sexta 14/02</h1>
            <TableHome>
              <div style={{ overflowX: 'auto' }}>
                <TableWrapper variant="dark">
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>Filmes</th>
                      <th>Horários</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>1</td>
                      <td>Wicked</td>
                      <td>
                        <Container>
                          <HeaderRow>
                            <HeaderCol>Dublado</HeaderCol>
                            <HeaderCol>VIP Legendado</HeaderCol>
                          </HeaderRow>
                          <TimeRow>
                            <TimeCol>14:00</TimeCol>
                            <TimeCol>16:00</TimeCol>
                          </TimeRow>
                        </Container>
                      </td>
                    </tr>
                    <tr>
                      <td>2</td>
                      <td>Herege</td>
                      <td>
                        <Container>
                          <HeaderRow>
                            <HeaderCol>Dublado</HeaderCol>
                            <HeaderCol>Dublado</HeaderCol>
                            <HeaderCol>VIP Legendado</HeaderCol>
                          </HeaderRow>
                          <TimeRow>
                            <TimeCol>15:00</TimeCol>
                            <TimeCol>17:00</TimeCol>
                            <TimeCol>19:00</TimeCol>
                          </TimeRow>
                        </Container>
                      </td>
                    </tr>
                  </tbody>
                </TableWrapper>
              </div>
            </TableHome>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default Home;
