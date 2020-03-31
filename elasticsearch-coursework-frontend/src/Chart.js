import React from 'react';
import axios from 'axios';
import {Line} from 'react-chartjs-2';
import DatePicker from "react-datepicker";

import "react-datepicker/dist/react-datepicker.css";
import "./Chart.css"

class Chart extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            word: '',
            startDate: new Date(),
            endDate: new Date(),
            data:
            {
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
        this.handleStartDateChange = this.handleStartDateChange.bind(this);
        this.handleEndDateChange = this.handleEndDateChange.bind(this);
    }

    handleChange(event) {
        this.setState({
        word: event.target.value
        });
    };

    handleStartDateChange = date => {
        this.setState({
          startDate: date
        });
    };

    handleEndDateChange = date => {
        this.setState({
            endDate: date
        });
    };

    handleSubmit(event) {
        event.preventDefault();
        const url = 'http://localhost:8080/api/chart/test2?words=' + this.state.word;

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
                                <input className="settings-element input-light" type="text" placeholder="Слово для поиска" value={this.state.word} onChange={this.handleChange}/>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="chart-form-label">Каталог:</span></td>
                            <td>
                                <select size="1" required>
                                    <option value="habr" selected>Habr</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="chart-form-label">Начиная с:</span></td>
                            <td>
                                <DatePicker
                                  selected={this.state.startDate}
                                  onChange={this.handleStartDateChange}
                                  dateFormat="yyyy-MM-dd"
                                />
                            </td>
                        </tr>
                        <tr>
                            <td><span class="chart-form-label">Заканчивая:</span></td>
                            <td>
                                <DatePicker
                                  selected={this.state.endDate}
                                  onChange={this.handleEndDateChange}
                                  dateFormat="yyyy-MM-dd"
                                />
                            </td>
                        </tr>
                        <tr>
                            <td colSpan="2"><span class="chart-form-label">Вид поиска:</span></td>
                        </tr>
                        <tr>
                            <td>
                                <p><input class="chart-form-radio" type="radio" name="countWordInDocument" value="1"/>Наличие</p>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p><input class="chart-form-radio" type="radio" name="countWordInDocument" value="0"/>Подсчет</p>
                            </td>
                        </tr>
                        <tr>
                            <td colSpan="2"><span class="chart-form-label">Стеммер:</span></td>
                        </tr>
                        <tr>
                            <td>
                                <p><input class="chart-form-radio" type="radio" name="stemmerType" value="Elasticsearch"/>Elasticsearch</p>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p><input class="chart-form-radio" type="radio" name="stemmerType" value="Porter"/>Стеммер Портера</p>
                             </td>
                        </tr>
                        <tr><td colSpan="2"><span class="chart-form-label">Учитывать:</span></td></tr>
                        <tr>
                            <td>
                                <p><input class="chart-form-radio" type="radio" name="countMode" value="ALL"/>Все</p>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p><input class="chart-form-radio" type="radio" name="countMode" value="ONLY_BODY"/>Только тело</p>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <p><input class="chart-form-radio" type="radio" name="countMode" value="ONLY_HEADER"/>Только заголовок</p>
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