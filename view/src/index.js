// @flow
import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import store from './store/configureStore';
import './index.css';
import App from './components/App';
import Home from './routes/home/Home';
import Login from './routes/login/Login';
import registerServiceWorker from './registerServiceWorker';

/**
 * Not being used yet
 */
const routes = [
  {
    component: Home,
    routes: [
      {
        path: '/',
        exact: true,
        component: Home,
      },
      {
        path: '/login',
        exact: true,
        component: Login,
      },
    ],
  },
];

const context = {
  store,
};

ReactDOM.render(
  <App context={context}>
    <BrowserRouter>
      <Switch>
        <Route exact path="/" component={Home} />
        <Route path="/login" component={Login} />
      </Switch>
    </BrowserRouter>
  </App>,
  document.getElementById('root'),
);

registerServiceWorker();
