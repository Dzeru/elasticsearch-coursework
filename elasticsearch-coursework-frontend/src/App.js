import React from 'react'
import logo from './logo.svg'
import './Reset.css'
import './Global.css'
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
