import React, { Component } from 'react';
import { Container } from 'reactstrap';
import DuplicateTable from '../components/DuplicateTable';
import {Link} from "react-router-dom";
import {Button} from "react-bootstrap";

class DuplicatesPage extends Component {
  render() {
    return (
      <div className='app'>
        <div className='app-body'>
          <Container fluid className='text-center'>
            <Link to="/"><Button>Back</Button></Link>
            <h1>Duplicate Finder</h1>
            <h3>Potential duplicate entries are highlighted in red. Click to show potential duplicates.</h3>
            <DuplicateTable />
          </Container>
        </div>
      </div>
    );
  }
}

export default DuplicatesPage;
