import React from 'react';
import axios from 'axios';
import {Line} from 'react-chartjs-2';
import "./Chart.css"

class Chart extends React.Component {

    constructor(props) {
        super(props)

        this.state = {
            word: '',
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
    }

    handleChange(event) {
        this.setState({word: event.target.value})
    }

    handleSubmit(event) {
        event.preventDefault();
        const url = 'http://localhost:8080/api/chart/test2?words=' + this.state.word;

        axios.get(url)
            .then(res => {
                this.setState({data: res.data})
            })
    }

    render() {
        return (
            <div>
              <div className="container w60 fl">
                <Line data={this.state.data} />
              </div>
              <div className="container w30 fr">
                <form onSubmit={this.handleSubmit}>
                    <input className="settings-element input-light" type="text" placeholder="Слово для поиска" value={this.state.word} onChange={this.handleChange}/>
                    <div>
                        <p><input type="radio" name="stemmerType" value="Elasticsearch"/>Elasticsearch</p>
                        <p><input type="radio" name="stemmerType" value="Porter"/>Стеммер Портера</p>
                    </div>
                    <div>
                        <p>Подсчитывать количество вхождений слова в документе?</p>
                        <p><input type="radio" name="countWordInDocument" value="1"/>Да</p>
                        <p><input type="radio" name="countWordInDocument" value="0"/>Нет</p>
                    </div>
                    <button className="settings-element button-light" type="submit">Построить график</button>
                </form>
              </div>
            </div>
        );
    }
}

export default Chart