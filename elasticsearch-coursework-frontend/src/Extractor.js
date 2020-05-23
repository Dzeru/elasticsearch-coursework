import React from 'react';
import axios from 'axios';
import './Extractor.css';

class Extractor extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      habrIds: ''
    }

    this.handleSubmitHabr = this.handleSubmitHabr.bind(this);
    this.handleChangeHabr = this.handleChangeHabr.bind(this);
  };

  handleChangeHabr(event) {
    this.setState({habrIds: event.target.value});
  }

  handleSubmitHabr(event) {
    event.preventDefault();
    const url = 'http://localhost:8080/api/extract/habr?' + 'postIds=' + this.state.habrIds;

    axios.get(url)
      .then(res => {
        alert('Обработано документов: ' + res.data + '\nНекоторые документы могли быть недоступны или удалены.');
      })
  }

  render() {
    return (
      <div className="extractor-container">
        <div>
          <h1>Выгрузка Habr</h1>
          <p>Введите id одним из следующих способов:</p>
            <ul className="ex-ul">
              <li>Одно число - один документ с указанным id;</li>
              <li>Числа через запятую без пробелов: несколько документов с указанными id;</li>
              <li>Два числа через дефис: диапазон документов, включая указанные id.</li>
            </ul>

          <form onSubmit={this.handleSubmitHabr}>
            <input className="settings-element input-light" type="text" value={this.state.habrIds} placeholder="IDs" onChange={this.handleChangeHabr}/>
            <button className="settings-element button-light" type="submit">Выгрузить</button>
          </form>
        </div>
      </div>
    );
  }
}

export default Extractor