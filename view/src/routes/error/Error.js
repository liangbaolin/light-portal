import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Layout from '../../components/Layout/Layout';

class Error extends Component {
  static propTypes = {
    error: PropTypes.shape({
      name: PropTypes.string.isRequired,
      message: PropTypes.string.isRequired,
      stack: PropTypes.string.isRequired,
    }),
  };
  static defaultProps = {
    error: null,
  };
  render() {
    if (this.props.error) {
      return (
        <Layout>
          <h1>{this.props.error.name}</h1>
          <pre>{this.props.error.stack}</pre>
        </Layout>
      );
    }
    return (
      <Layout>
        <h1>Error</h1>
        <p>Sorry, a critical error occurred on this page.</p>
      </Layout>
    );
  }
}

export default Error;
