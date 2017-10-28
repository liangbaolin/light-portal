import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './Layout.css';
import Header from '../Header/Header';

class Layout extends Component {
  static propTypes = {
    children: PropTypes.node.isRequired,
  };
  render() {
    return (
      <div className="App">
        <Header />
        {this.props.children}
      </div>
    );
  }
}

export default Layout;
