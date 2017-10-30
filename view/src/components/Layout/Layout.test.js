/* eslint-env jest */
/* eslint-disable padded-blocks, no-unused-expressions */

import React from 'react';
import renderer from 'react-test-renderer';
import configureStore from 'redux-mock-store';
import thunk from 'redux-thunk';
import mockResponse from '../../tools/mockResponse';
import App from '../App';
import Home from '../../routes/home/Home';
import Login from '../../routes/login/Login';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);

const initialState = {
  menu: {
    name: 'Test',
    routes: [],
  },
};

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
        component: Login,
      },
    ],
  },
];

const menuByHostResponse = {
  contains: [
    {
      route: '/login',
      roles: ['user'],
      _rev: '_Vzj_HTu---',
      entityId: 'e2',
      _id: 'menuItem/2',
      label: 'login',
      _key: '2',
    },
  ],
  _rev: '_Vzj_HRG---',
  host: 'example.org',
  description: 'example site111',
  entityId: 'e2',
  _id: 'menu/example.org',
  _key: 'example.org',
};

describe('Layout', () => {
  beforeEach(() => {
    window.fetch = jest
      .fn()
      .mockImplementation(() =>
        Promise.resolve(
          mockResponse(200, 'ok', JSON.stringify(menuByHostResponse)),
        ),
      );
  });

  test('renders children correctly', () => {
    const store = mockStore(initialState);
    const wrapper = renderer
      .create(<App context={{ store, routes }} />)
      .toJSON();

    expect(wrapper).toMatchSnapshot();
  });
});
