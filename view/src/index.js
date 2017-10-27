// @flow
import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router } from 'react-router-dom';
import { renderRoutes } from 'react-router-config';
import store from './store/configureStore';

import App from './components/App';
import { routes } from './routes';
import registerServiceWorker from './registerServiceWorker';

const context = {
  store,
};

ReactDOM.render(
  <App context={context}>
    <Router>{renderRoutes(routes)}</Router>
  </App>,
  document.getElementById('root'),
);

registerServiceWorker();
