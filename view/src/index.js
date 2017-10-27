// @flow
import React from 'react';
import ReactDOM from 'react-dom';
// import { BrowserRouter } from 'react-router-dom';
import { Router } from 'react-router';
import { syncHistoryWithStore } from 'react-router-redux';
import browserHistory from './history';
import store from './store/configureStore';
import './index.css';
import App from './components/App';
import Layout from './components/Layout/Layout';
import Login from './routes/login/Login';
import registerServiceWorker from './registerServiceWorker';

const history = syncHistoryWithStore(browserHistory, store);

const routes = {
  path: '/',
  component: Login,
  indexRoute: { component: Login },
};

const context = {
  store,
  // routes,
};

ReactDOM.render(
  <App context={context}>
    {/* <Router history={history} routes={routes} /> */}
    <Login />
  </App>,
  document.getElementById('root'),
);

registerServiceWorker();
