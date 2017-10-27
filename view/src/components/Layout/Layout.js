import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { renderRoutes } from 'react-router-config';
import './Layout.css';
import Header from '../Header/Header';

class Layout extends Component {
  static propTypes = {
    children: PropTypes.node.isRequired,
    route: PropTypes.shape({
      routes: PropTypes.array.isRequired,
    }).isRequired,
  };
  render() {
    return (
      <div className="App">
        <Header />
        {this.props.route ? renderRoutes(this.props.route.routes) : null}
        {this.props.children}
      </div>
    );
  }
}

export default Layout;
