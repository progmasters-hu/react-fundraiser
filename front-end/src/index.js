import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import axios from 'axios';

axios.defaults.baseURL = 'http://fundraiser-demo.progmasters.hu:8080';
// axios.defaults.baseURL = 'http://localhost:8080';
axios.defaults.withCredentials = true;

ReactDOM.render(<App />, document.getElementById('root'));
registerServiceWorker();
