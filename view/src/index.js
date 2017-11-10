// @flow
import React from 'react';
import ReactDOM from 'react-dom';
import store from './store/configureStore';

import App from './components/App';
import Spinner from './components/Spinner/Spinner';
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

/**
 * Mount Point
 */
const elementMountPoint = document.getElementById('root');

/**
 * Async IIFE React
 * Renders Spinner then tries to fetch routes from server.
 * If routes are not available it renders application
 * with default routes.
 */
(async () => {
  ReactDOM.render(<Spinner />, elementMountPoint);
  try {
    const newContext = {
      store,
      routes: dynamicRoutes(context.routes, await fetchMenuByHost()),
    };
    ReactDOM.render(<App context={newContext} />, elementMountPoint);
  } catch (e) {
    ReactDOM.render(<App context={context} />, elementMountPoint);
  }
})();

registerServiceWorker();
