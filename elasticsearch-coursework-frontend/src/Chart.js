import React from 'react';
import axios from 'axios';
import {Line} from 'react-chartjs-2';

import "./Chart.css"

class Chart extends React.Component {

  constructor(props) {
    super(props)

    this.state = {
      word: '',
      beginDate: '',
      endDate: '',
      countMode: 'ALL',
      stemmerType: 'Elasticsearch',
      countWordInDocument: 'CONTAINS',
      data: {
        labels: [],
        datasets: [
        {
          label: '',
          fill: false,
          lineTension: 0.1,
          backgroundColor: 'rgba(75,192,192,0.4)',
          borderColor: 'orange',
          borderCapStyle: 'butt',
          borderDash: [],
          borderDashOffset: 0.0,
          borderJoinStyle: 'miter',
          pointBorderColor: 'rgba(75,192,192,1)',
          pointBackgroundColor: '#fff',
          pointBorderWidth: 1,
          pointHoverRadius: 5,
          pointHoverBackgroundColor: 'rgba(75,192,192,1)',
          pointHoverBorderColor: 'rgba(220,220,220,1)',
          pointHoverBorderWidth: 2,
          pointRadius: 1,
          pointHitRadius: 10,
          data: []
        }
        ]
      }
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleBeginDateChange = this.handleBeginDateChange.bind(this);
    this.handleEndDateChange = this.handleEndDateChange.bind(this);
    this.handleCountModeChange = this.handleCountModeChange.bind(this);
    this.handleStemmerTypeChange = this.handleStemmerTypeChange.bind(this);
    this.handleCountWordInDocumentChange = this.handleCountWordInDocumentChange.bind(this);
  }

  handleChange(event) {
    this.setState({
      word: event.target.value
    });
  };

   handleBeginDateChange(event) {
     this.setState({
       beginDate: event.target.value
     });
   };

   handleEndDateChange(event) {
     this.setState({
       endDate: event.target.value
     });
   };

  handleCountModeChange(event) {
    this.setState({
      countMode: event.target.value
    });
  };

  handleStemmerTypeChange(event) {
    this.setState({
      stemmerType: event.target.value
    });
  }

  handleCountWordInDocumentChange(event) {
    this.setState({
        countWordInDocument: event.target.value
    });
  }

  handleSubmit(event) {
    event.preventDefault();
    const url = 'http://localhost:8080/api/chart/makeChart?words=' + this.state.word +
      '&countMode=' + this.state.countMode + '&stemmerType=' + this.state.stemmerType +
      '&countWordInDocument=' + this.state.countWordInDocument +
      '&beginDate=' + this.state.beginDate + '&endDate=' + this.state.endDate;

    axios.get(url)
      .then(res => {
        this.setState({data: res.data});
      })
  };

  render() {
    return (
      <div>
        <div className="container w60 fl">
          <Line data={this.state.data} />
        </div>
        <div className="container w30 fr">
        <form onSubmit={this.handleSubmit}>
          <table cellPadding="7" cellSpacing="2">
          <tbody>
            <tr>
              <td colSpan="2">
                <input
                  className="settings-element input-light"
                  type="text"
                  placeholder="Слово для поиска"
                  value={this.state.word}
                  onChange={this.handleChange}
                />
              </td>
            </tr>
            <tr>
              <td><span className="chart-form-label">Каталог:</span></td>
              <td>
                <select size="1" required>
                  <option value="habr" selected>Habr</option>
                </select>
              </td>
            </tr>
            <tr>
              <td><span className="chart-form-label">Начиная с:</span></td>
              <td>
                <input
                  type="date"
                  value={this.state.beginDate}
                  onChange={this.handleBeginDateChange}
                />
              </td>
            </tr>
            <tr>
              <td><span className="chart-form-label">Заканчивая:</span></td>
              <td>
                <input
                  type="date"
                  value={this.state.endDate}
                  onChange={this.handleEndDateChange}
                />
              </td>
            </tr>
            <tr>
              <td colSpan="2"><span className="chart-form-label">Вид поиска:</span></td>
            </tr>
            <tr>
              <td>
                <label>
                  <input
                    className="chart-form-radio"
                    type="radio"
                    name="countWordInDocument"
                    value="CONTAINS"
                    checked={this.state.countWordInDocument === 'CONTAINS'}
                    onChange={this.handleCountWordInDocumentChange}
                  />
                  Наличие
                </label>
              </td>
            </tr>
            <tr>
              <td>
                <label>
                  <input
                    className="chart-form-radio"
                    type="radio"
                    name="countWordInDocument"
                    value="HOW_MANY"
                    checked={this.state.countWordInDocument === 'HOW_MANY'}
                    onChange={this.handleCountWordInDocumentChange}
                  />
                  Подсчет
                </label>
              </td>
            </tr>
            <tr>
              <td colSpan="2"><span className="chart-form-label">Стеммер:</span></td>
            </tr>
            <tr>
              <td>
                <label>
                  <input
                    className="chart-form-radio"
                    type="radio"
                    name="stemmerType"
                    value="Elasticsearch"
                    checked={this.state.stemmerType === 'Elasticsearch'}
                    onChange={this.handleStemmerTypeChange}
                  />
                  Elasticsearch
                </label>
              </td>
            </tr>
            <tr>
              <td>
                <label>
                  <input
                    className="chart-form-radio"
                    type="radio"
                    name="stemmerType"
                    value="Porter"
                    checked={this.state.stemmerType === 'Porter'}
                    onChange={this.handleStemmerTypeChange}
                  />
                  Стеммер Портера
                </label>
               </td>
            </tr>
            <tr><td colSpan="2"><span className="chart-form-label">Учитывать:</span></td></tr>
            <tr>
              <td>
                <label>
                  <input
                    className="chart-form-radio"
                    type="radio"
                    name="countMode"
                    value="ALL"
                    checked={this.state.countMode === 'ALL'}
                    onChange={this.handleCountModeChange}
                  />
                  Все
                </label>
              </td>
            </tr>
            <tr>
              <td>
                <label>
                  <input
                    className="chart-form-radio"
                    type="radio"
                    name="countMode"
                    value="ONLY_BODY"
                    checked={this.state.countMode === 'ONLY_BODY'}
                    onChange={this.handleCountModeChange}
                  />
                  Только тело
                </label>
              </td>
            </tr>
            <tr>
              <td>
                <label>
                  <input
                    className="chart-form-radio"
                    type="radio"
                    name="countMode"
                    value="ONLY_HEADER"
                    checked={this.state.countMode === 'ONLY_HEADER'}
                    onChange={this.handleCountModeChange}
                  />
                  Только заголовок
                </label>
              </td>
            </tr>
          </tbody>
          </table>
          <button className="settings-element button-light" type="submit">Построить график</button>
        </form>
        </div>
      </div>
    );
  }
}

export default Chart