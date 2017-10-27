/* eslint no-underscore-dangle: 0 */
import { MENU_SERVICE_SUCCESS } from '../constants';

const initialState = {
  name: '',
  routes: [],
};

export default function runtime(state = initialState, action) {
  switch (action.type) {
    case MENU_SERVICE_SUCCESS:
      return {
        ...state,
        name: action.payload._key,
        routes: action.payload.contains,
        id: action.payload._id,
      };
    default:
      return state;
  }
}
