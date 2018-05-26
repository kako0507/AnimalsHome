import axios from 'axios';
import { AsyncStorage } from 'react-native';
import actionTypes from '../constants/actionTypes';

const oneHour = 60 * 60 * 1000;

const setRoute = (routeName, data) => ({
  type: actionTypes.ROUTE_CHANGE,
  routeName,
  data,
});

const setPetList = data => ({
  type: actionTypes.SET_PET_LIST,
  data,
});

const fetchPetList = () => async (dispatch, getState) => {
  const { petList } = getState().app;
  let data = petList;
  try {
    const value = await AsyncStorage.getItem('@AnimalsHome:PetList');
    if (value !== null) {
      // We have data!!
      data = JSON.parse(value);
    }
  } catch (error) {
    // Error retrieving data
  }
  const updated = new Date().getTime();
  if (!petList || (updated - petList.updated) > oneHour) {
    const response = await axios.get('http://163.29.36.110/asms/api/ParaAdmin?tableName=View_AnimalCages');
    data = {
      data: JSON.parse(response.data),
      updated,
    };
    try {
      await AsyncStorage.setItem('@AnimalsHome:PetList', JSON.stringify(data));
    } catch (error) {
      // Error saving data
    }
  }
  dispatch(setPetList(data));
};

export {
  setRoute,
  fetchPetList,
};
