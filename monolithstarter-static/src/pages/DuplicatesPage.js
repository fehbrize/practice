import React, { Component } from 'react';
import { Container } from 'reactstrap';
import DuplicateTable from '../components/DuplicateTable';

class DuplicatesPage extends Component {
  render() {
    return (
      <div className='app'>
        <div className='app-body'>
          <Container fluid className='text-center'>
            <DuplicateTable />
          </Container>
        </div>
      </div>
    );
  }
}

export default DuplicatesPage;
