import actionTypes from '../constants/actionTypes';
import routes from '../routes';

const initState = { params: {} };

export default function app(state = initState, action) {
  let nextState;
  switch (action.type) {
    case actionTypes.ROUTE_CHANGE: {
      let { params } = state;
      let { filteredData } = state.petList;
      let selectedPet;
      if (action.data.currentRoute) {
        const { param } = routes[action.data.currentRoute];
        if (param) {
          params = {
            ...state.params,
            [param]: action.data.param,
          };
        }
      }
      if (action.routeName === routes.petChoice.routeName) {
        filteredData = state.petList.data.filter(pet => (
          parseInt(pet.TypeId, 10) === params.type &&
          parseInt(pet.Sex, 10) === params.sex &&
          pet.Age === params.age
        ));
      } else if (action.routeName === routes.petDetail.routeName) {
        selectedPet = action.data;
      }
      nextState = {
        ...state,
        params,
        petList: {
          ...state.petList,
          filteredData,
        },
        selectedPet,
      };
      break;
    }
    case actionTypes.SET_PET_LIST:
      nextState = {
        ...state,
        petList: {
          ...state.petList,
          ...action.data,
        },
      };
      break;
    default:
  }

  // Simply return the original `state` if `nextState` is null or undefined.
  return nextState || state;
}
