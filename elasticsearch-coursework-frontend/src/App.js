import React from 'react'
import logo from './logo.svg'
import './App.css';
import Header from './Header.js'

class App extends React.Component {

    constructor() {
        super()
    }

    render() {
        return (
            <div>
                <Header/>
            </div>
        );
    }
}

export default App;
