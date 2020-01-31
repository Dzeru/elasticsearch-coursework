import React from 'react'
import './Header.css'

class Header extends React.Component {
    constructor() {
        super()
    }

    render() {
        return <div className="header"><span className="logo">ECW</span> <span className="nav-el">Графики</span> <span className="nav-el">Выгрузка</span></div>
    }
}

export default Header