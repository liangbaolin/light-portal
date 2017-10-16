/* eslint-disable import/prefer-default-export */

/**
 * https://github.com/kriasoft/react-starter-kit/blob/feature/react-intl/src/actions/intl.js
 */
import fetch from 'isomorphic-fetch';
import {
  MENU_SERVICE_GET,
  MENU_SERVICE_SUCCESS,
  MENU_SERVICE_ERROR,
} from '../constants';

function client(payload) {
  return new Promise(resolve => {
    fetch(`https://jsonplaceholder.typicode.com/${payload}`)
      .then(response => response.json())
      .then(data => {
        resolve(data);
      });
  });
}

export function getMenuService({ host, service, action, version }) {
  return async dispatch => {
    dispatch({
      type: MENU_SERVICE_GET,
    });

    try {
      const { data } = await client({
        host,
        service,
        action,
        version,
      });

      return dispatch({
        type: MENU_SERVICE_SUCCESS,
        payload: {
          data,
        },
      });
    } catch (error) {
      dispatch({
        type: MENU_SERVICE_ERROR,
      });
      return null;
    }
  };
}
