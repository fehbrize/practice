import React, { Component } from 'react';
import {getDuplicates} from "../actions/retrieveDuplicates";

class DuplicateTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      entries: []
    };

  }

  componentDidMount() {
    this._isMounted = true;
    getDuplicates().then(entries => {
      if (this._isMounted)
        this.setState({entries});
    }).catch(() => {
      if (this._isMounted)
        this.setState({entries: ['The server did not respond so...hello from the client!']});
    });
  }

  render() {
    return (
      <div>
        <p>{this.state.entries}</p>
      </div>
      /*<div>
        <table width="100%">
          <tr>
            <th>Id</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Company</th>
            <th>Email</th>
            <th>Address 1</th>
            <th>Address 2</th>
            <th>Zip</th>
            <th>City</th>
            <th>State (Long)</th>
            <th>State (Abbr.)</th>
            <th>Phone</th>
          </tr>
          <tr>

          </tr>
        </table>
      </div>*/
    );
  }
}

export default DuplicateTable;
