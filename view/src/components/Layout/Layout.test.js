/* eslint-env jest */
/* eslint-disable padded-blocks, no-unused-expressions */

import React from 'react';
import renderer from 'react-test-renderer';
import configureStore from 'redux-mock-store';
import thunk from 'redux-thunk';
import App from '../App';
import Layout from './Layout';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);
const initialState = {
  menu: {
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
      {
        route: '/logout',
        roles: ['user'],
        _rev: '_Vzj_HTy---',
        entityId: 'e3',
        _id: 'menuItem/3',
        label: 'logout',
        _key: '3',
      },
    ],
    _rev: '_Vzj_HRG---',
    host: 'example.org',
    description: 'example site111',
    entityId: 'e2',
    _id: 'menu/example.org',
    _key: 'example.org',
  },
};

describe('Layout', () => {
  test('renders children correctly', () => {
    const store = mockStore(initialState);
    const wrapper = renderer
      .create(
        <App context={{ store }}>
          <Layout>
            <div className="child" />
          </Layout>
        </App>,
      )
      .toJSON();

    expect(wrapper).toMatchSnapshot();
  });
});
