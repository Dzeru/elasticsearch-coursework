import React from 'react';
import axios from 'axios';

class Extractor extends React.Component {
    constructor(props) {
        super(props);
        this.state = {habrIds: ''};


        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        this.setState({habrIds: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        const url = 'http://localhost:8080/api/extract/habr?' + 'postIds=' + this.state.habrIds;

        axios.get(url)
            .then(res => {
                console.log(res);
                console.log(res.data);
                alert('Обработано документов: ' + res.data);
            })
    }

    render() {
        return (
            <div>
                <div>
                    <h1>Выгрузка Habr</h1>
                    <p>Введите id одним из следующих способов:
                        <ul>
                            <li>Одно число - один документ с указанным id</li>
                            <li>Числа через запятую без пробелов: несколько документов с указанными id</li>
                            <li>Два числа через дефис: диапазон документов, включая указанные id</li>
                        </ul>
                    </p>
                    <form onSubmit={this.handleSubmit}>
                        <input type="text" value={this.state.habrIds} onChange={this.handleChange}/>
                        <button type="submit">Выгрузить</button>
                    </form>
                </div>
            </div>
        );
    }
}

export default Extractor