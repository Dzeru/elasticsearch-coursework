import React from 'react';
import logo from './logo.svg';
import './App.css';
import Header from './Header.js'
import Chart from './Chart.js'

class App extends React.Component {

    constructor() {
        super()
    }

    render() {
        return (
            <div>
                <Header></Header>
                <div>Hello world!</div>
                <Chart/>
            </div>

        );
    }
}

export default App;
