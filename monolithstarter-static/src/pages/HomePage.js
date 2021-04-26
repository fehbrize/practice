import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import {Button} from "reactstrap";

class HomePage extends Component {
  render() {
    return (
        <div className="home-page">
          <Link to="/find-dupes">
            <Button className="start-button">Click to find duplicates.</Button>
          </Link>
        </div>
    );
  }
}

export default withRouter(HomePage);
