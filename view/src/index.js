// @flow
import React from 'react';
import ReactDOM from 'react-dom';
import store from './store/configureStore';

import App from './components/App';
import { globalRoutes, dynamicRoutes } from './routes';
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
  routes: globalRoutes,
};

const elementMountPoint = document.getElementById('root');

ReactDOM.render(<App context={context} />, elementMountPoint, async () => {
  const newContext = {
    store,
    routes: dynamicRoutes(context.routes, await fetchMenuByHost()),
  };
  ReactDOM.render(<App context={newContext} />, elementMountPoint);
});

registerServiceWorker();
