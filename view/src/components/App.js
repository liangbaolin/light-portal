import React from 'react';
import PropTypes from 'prop-types';
import { BrowserRouter as Router } from 'react-router-dom';
import { renderRoutes } from 'react-router-config';

const ContextType = {
  // Redux store
  store: PropTypes.object.isRequired,
  routes: PropTypes.array.isRequired,
};

class App extends React.PureComponent {
  static propTypes = {
    context: PropTypes.shape(ContextType).isRequired,
  };

  static childContextTypes = ContextType;

  getChildContext() {
    return this.props.context;
  }

  render() {
    // NOTE: If you need to add or modify header, footer etc. of the app,
    // please do that inside the Layout component.
    return React.Children.only(
      <Router>{renderRoutes(this.props.context.routes[0].routes)}</Router>,
    );
  }
}

export default App;
