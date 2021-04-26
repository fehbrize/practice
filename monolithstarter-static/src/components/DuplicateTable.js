import React, { Component } from 'react';
import {getDuplicates} from "../actions/retrieveDuplicates";
import { Table } from "react-bootstrap";
// Bootstrap CSS
import "bootstrap/dist/css/bootstrap.min.css";
// To make rows collapsible
import "bootstrap/js/src/collapse.js";

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

  getDataEntries(entries) {
    let id = 0;
    return entries.map((entryList) => {
      if(entryList.length === 1) {
        return  entryList.map((entry) => {
          id++;
          return(
            <tr key={id} className="no-dupes">
              <td>{entry.firstName}</td>
              <td>{entry.lastName}</td>
              <td>{entry.company}</td>
              <td>{entry.email}</td>
              <td>{entry.address1}</td>
              <td>{entry.address2}</td>
              <td>{entry.zip}</td>
              <td>{entry.city}</td>
              <td>{entry.longState}</td>
              <td>{entry.shortState}</td>
              <td>{entry.phone}</td>
            </tr>
          );
        });
      }
      else {
        id++;
        let parentId = id;
        let output = [];
        output[0] =
          <tr
            key={parentId}
            data-toggle="collapse"
            data-target={".multi-collapse" + parentId}
            aria-controls={"multiCollapseExample" + parentId}
            className="has-dupes"
          >
          <td>{entryList[0].firstName}</td>
          <td>{entryList[0].lastName}</td>
          <td>{entryList[0].company}</td>
          <td>{entryList[0].email}</td>
          <td>{entryList[0].address1}</td>
          <td>{entryList[0].address2}</td>
          <td>{entryList[0].zip}</td>
          <td>{entryList[0].city}</td>
          <td>{entryList[0].longState}</td>
          <td>{entryList[0].shortState}</td>
          <td>{entryList[0].phone}</td>
        </tr>;
        output[1] = entryList.splice(0, 1).map((entry) => {
          id++;
          return(
            <tr
              key={id}
              className={"dupe collapse multi-collapse" + parentId}
              id={"multiCollapseExample" + parentId}
              style={{background: "indianred"}}
            >
              <td>{entry.firstName}</td>
              <td>{entry.lastName}</td>
              <td>{entry.company}</td>
              <td>{entry.email}</td>
              <td>{entry.address1}</td>
              <td>{entry.address2}</td>
              <td>{entry.zip}</td>
              <td>{entry.city}</td>
              <td>{entry.longState}</td>
              <td>{entry.shortState}</td>
              <td>{entry.phone}</td>
            </tr>
          );
        });
        return output;
      }

    });

  }


  render() {
    return(
      <Table width="100%">
        <thead>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Company</th>
          <th>Email</th>
          <th>Address 1</th>
          <th>Address 2</th>
          <th>Zip/Postal Code</th>
          <th>State</th>
          <th>State (Abbr.)</th>
          <th>Phone</th>
        </thead>
        <tbody>{this.getDataEntries(this.state.entries)}</tbody>
      </Table>
    );
    /*<div>
      <table width="100%">
        <tr>w
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

  }
}

export default DuplicateTable;
