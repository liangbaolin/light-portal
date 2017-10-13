import {
  MENU_SERVICE_GET,
  MENU_SERVICE_SUCCESS,
  MENU_SERVICE_ERROR,
} from '../constants';

const initialState = {
  data: [],
};

export default function menu(state = initialState, action) {
  switch (action.type) {
    case MENU_SERVICE_GET:
      return {
        ...state,
        [action.payload.name]: action.payload.value,
      };
    case MENU_SERVICE_SUCCESS:
      return {
        ...state,
        [action.payload.name]: action.payload.value,
      };
    case MENU_SERVICE_ERROR:
      return {
        ...state,
        [action.payload.name]: action.payload.value,
      };
    default:
      return state;
  }
}
