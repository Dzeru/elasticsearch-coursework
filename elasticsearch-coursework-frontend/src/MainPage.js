import React from 'react'
import './MainPage.css'

class MainPage extends React.Component {

    constructor() {
        super()
    }

    render() {
        return (
        <div className="main-container">
            <h1>Курсовая работа</h1>
            <ul>
                <li>Выгрузка статей с сайта habr.com</li>
                <li>Подсчет слов</li>
            </ul>
        </div>
        );
    }

}

export default MainPage