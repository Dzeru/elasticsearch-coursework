import React from 'react';
import axios from 'axios';
import './Extractor.css';

class Extractor extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            habrIds: '',
            vkAuthorName: '',
            vkAuthorUrl: '',
            vkAuthorId: '',
        }

        this.handleSubmitHabr = this.handleSubmitHabr.bind(this);
        this.handleChangeHabr = this.handleChangeHabr.bind(this);
        this.handleSubmitVkAuthor = this.handleSubmitVkAuthor.bind(this);
        this.handleChangeVkAuthorName = this.handleChangeVkAuthorName.bind(this);
        this.handleChangeVkAuthorUrl = this.handleChangeVkAuthorUrl.bind(this);
        this.handleChangeVkAuthorId = this.handleChangeVkAuthorId.bind(this);
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

    handleChangeVkAuthorName(event) {
        this.setState({vkAuthorName: event.target.value})
    }

    handleChangeVkAuthorUrl(event) {
        this.setState({vkAuthorUrl: event.target.value})
    }

    handleChangeVkAuthorId(event) {
        this.setState({vkAuthorId: event.target.value})
    }

    handleSubmitVkAuthor(event) {
            event.preventDefault();
            const url = 'http://localhost:8080/api/extract/vk/author?' + 'name=' + this.state.vkAuthorName +
            '&url=' + this.state.vkAuthorUrl + "&authorId=" + this.state.vkAuthorId;

            axios.get(url)
                .then(res => {
                    alert(res.data);
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
                        <input className="settings-element input-light" type="text" value={this.state.habrIds} onChange={this.handleChangeHabr}/>
                        <button className="settings-element button-light" type="submit">Выгрузить</button>
                    </form>
                </div>
                <div>
                    <h1>Добавление авторов VK</h1>
                    <form onSubmit={this.handleSubmitVkAuthor}>
                        <input className="settings-element input-light" type="text" value={this.state.vkAuthorName} placeHolder="Название" onChange={this.handleChangeVkAuthorName}/>
                        <input className="settings-element input-light" type="text" value={this.state.vkAuthorUrl} placeHolder="URL" onChange={this.handleChangeVkAuthorUrl}/>
                        <input className="settings-element input-light" type="text" value={this.state.vkAuthorId} placeHolder="ID" onChange={this.handleChangeVkAuthorId}/>
                        <button className="settings-element button-light" type="submit">Добавить</button>
                    </form>
                </div>
            </div>
        );
    }
}

export default Extractor