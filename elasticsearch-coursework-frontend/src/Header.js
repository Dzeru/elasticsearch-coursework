import React from 'react'
import './Header.css'
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import Chart from './Chart.js'
import MainPage from './MainPage.js'
import Extractor from './Extractor.js'

class Header extends React.Component {
    constructor() {
        super()
    }

    render() {
        return (
            <Router>
              <div>
                <nav className="nav">
                  <ul>
                    <li className="logo">
                      <Link to="/">ECW</Link>
                    </li>
                    <li className="nav-el">
                      <Link to="/charts">Графики</Link>
                    </li>
                    <li className="nav-el">
                      <Link to="/extract">Выгрузка</Link>
                    </li>
                  </ul>
                </nav>

                {/* A <Switch> looks through its children <Route>s and
                    renders the first one that matches the current URL. */}
                <Switch>
                  <Route path="/charts">
                    <Chart />
                  </Route>
                  <Route path="/extract">
                    <Extractor />
                  </Route>
                  <Route path="/">
                    <MainPage />
                  </Route>
                </Switch>
              </div>
            </Router>
         );
    }

}

export default Header