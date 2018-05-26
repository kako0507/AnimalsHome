import _ from 'lodash';
import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { StackNavigator } from 'react-navigation';
import routes from '../routes';
import { addListener } from '../utils/redux';

export const AppNavigator = StackNavigator(_.mapValues(routes, ({ component }) => ({
  screen: component,
})));

const AppWithNavigationState = ({ dispatch, nav }) => (
  <AppNavigator
    navigation={{
      dispatch,
      state: nav,
      addListener,
    }}
  />
);
AppWithNavigationState.propTypes = {
  dispatch: PropTypes.func.isRequired,
  nav: PropTypes.object.isRequired,
};

const mapStateToProps = state => ({
  nav: state.nav,
});
export default connect(mapStateToProps)(AppWithNavigationState);
