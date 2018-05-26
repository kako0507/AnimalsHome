import React from 'react';
import PropTypes from 'prop-types';
import { WebView } from 'react-native';
import { connect } from 'react-redux';

const PetDetail = ({ Id, Tid }) => (
  <WebView
    scalesPageToFit={false}
    source={{ uri: `http://163.29.36.110/html/Aml_animalCon.aspx?Aid=${Id}&Tid=${Tid}` }}
  />
);
PetDetail.propTypes = {
  Id: PropTypes.string,
  Tid: PropTypes.string,
};
PetDetail.navigationOptions = {
  title: '寵物資訊',
};

const mapStateToProps = state => state.app.selectedPet;
export default connect(mapStateToProps)(PetDetail);
