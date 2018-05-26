import { NavigationActions } from 'react-navigation';
import routes from '../routes';
import actionTypes from '../constants/actionTypes';
import { AppNavigator } from '../navigators/AppNavigator';

// Start with the home screen.
const firstAction = AppNavigator.router.getActionForPathAndParams(routes.home.routeName);
const initialNavState = AppNavigator.router.getStateForAction(firstAction);

export default function nav(state = initialNavState, action) {
  let nextState;
  switch (action.type) {
    case actionTypes.ROUTE_CHANGE:
      nextState = AppNavigator.router.getStateForAction(
        NavigationActions.navigate({
          routeName: action.routeName,
          params: { title: action.routeName },
        }),
        state,
      );
      break;
    default:
      nextState = AppNavigator.router.getStateForAction(action, state);
      break;
  }

  // Simply return the original `state` if `nextState` is null or undefined.
  return nextState || state;
}
