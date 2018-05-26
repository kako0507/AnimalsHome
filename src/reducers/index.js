import { combineReducers } from 'redux';
import nav from './nav';
import app from './app';

const AppReducer = combineReducers({
  nav,
  app,
});

export default AppReducer;
