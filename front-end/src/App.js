import React, { Component } from 'react';
import './App.css';
import { BrowserRouter } from 'react-router-dom';
import Layout from './common/Layout';

class App extends Component {
  render() {
    return (
        <BrowserRouter>
            <div className="App">
                <Layout />
            </div>
        </BrowserRouter>
    );
  }
}

export default App;
