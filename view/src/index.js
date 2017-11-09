// @flow
import React from 'react';
import ReactDOM from 'react-dom';
import store from './store/configureStore';

import App from './components/App';
import routes from './routes';
import registerServiceWorker from './registerServiceWorker';

import fetchMenuByHost from './api/menu';
import { getMenuService } from './actions/menu';

/**
 * Dispatch Menu Service onload
 */
store.dispatch(getMenuService());

/**
   * Define context for Application
   */
const context = {
  store,
  routes,
};

ReactDOM.render(
  <App context={context} />,
  document.getElementById('root'),
  async () => {
    const routesFromServer = await fetchMenuByHost();
    context.routes.push(routesFromServer.contains);
    console.log(context);
  },
);

registerServiceWorker();
