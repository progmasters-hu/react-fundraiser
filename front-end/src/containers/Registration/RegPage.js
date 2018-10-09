import React, {Component} from 'react';
import axios from 'axios';
import waiting from '../../images/Eclipse-0.6s-76px.gif';

class RegPage extends Component {

    state = {
        userName: {
            value: '',
            isValid: true,
            message: ''
        },
        password: {
            value: '',
            isValid: true,
            message: ''
        },
        goal: {
            value: '',
            isValid: true,
            message: ''
        },
        email: {
            value: '',
            isValid: true,
            message: ''
        },
        currencyType: {
            value: '',
            isValid: true,
            message: ''
        },
        goalBody: {
            value: '',
            isValid: true,
            message: ''
        },
        imgUrl: {
            value: '',
            isValid: true,
            message: ''
        },
        currencies: [],
        isPacmanActive: false
    };

    componentDidMount() {
        axios.get('/api/accounts/currencies')
            .then((response) => {
                this.setState({
                    currencies: response.data.currencyTypes
                })
            })
    }

    inputChangeHandler = (event) => {
        const updatedFromField = {};
        updatedFromField.value = event.target.value;
        updatedFromField.isValid = true;

        this.setState({
            [event.target.name]: updatedFromField
        })
    };

    doUpload = () => {
        this.setState({isPacmanActive: true});
        const formData = new FormData();
        let file = this.fileInput.files[0];

        formData.append('file', file);

        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        };

        return axios.post('/api/upload', formData, config)
            .catch(error => {
                console.log("doUpload", error.response);
                this.validationHandler(error.response);
                this.setState({isPacmanActive: false});
            });
    };

    checkPassword = (password) => {
        const re = new RegExp('(?=.{8,})((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])|(?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W_])|(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])).*');
        return password.match(re);
    };

    sendFormData = (resp) => {

        const data = {};

        for (let formElementName in this.state) {
            data[formElementName] = this.state[formElementName].value;
        }

        if (resp) {
            data.imgUrl = resp.data;
        }

        if (this.checkPassword(this.state.password.value) !== null) {
            axios.post('/api/accounts', data)
                .then(() => {
                    this.props.history.push('/login');
                })
                .catch(error => {
                    console.log(error.response);
                    this.setState({isPacmanActive: false});
                    this.validationHandler(error.response.data);

                })
        } else {
            let updatedPasswordState = {...this.state.password};

            updatedPasswordState.isValid = false;
            updatedPasswordState.message = 'Wrong format or password length (min.:8)! (Lowercase, uppercase, numbers are required)';
            this.setState({password: updatedPasswordState,isPacmanActive: false});
        }

    };

    formSubmit = (event) => {
        event.preventDefault();
        if (this.fileInput.files[0]) {
            this.doUpload()
                .then((response) => {
                    this.sendFormData(response);
                })
                .catch(error => {
                    console.log("if", error.response);
                });
        } else {
            this.sendFormData()
        }
    };

    validationHandler = (error) => {
        const updatedState = {
            ...this.state
        };

        for (let field in this.state) {
            const updatedFormElement = {
                ...updatedState[field]
            };
            updatedFormElement.isValid = true;
            updatedFormElement.message = '';
            updatedState[field] = updatedFormElement;
        }

        for (let fieldError of error.fieldErrors) {
            const updatedFormElement = {
                ...updatedState[fieldError.field]
            };
            updatedFormElement.isValid = false;
            updatedFormElement.message = fieldError.message;
            updatedState[fieldError.field] = updatedFormElement;
        }

        this.setState({
            userName: updatedState.userName,
            goal: updatedState.goal,
            email: updatedState.email,
            goalBody: updatedState.goalBody,
            imgUrl: updatedState.imgUrl,
        });
    };

    toLoginPage = () => {
        this.props.history.push('/login');
    };

    render() {
        return (
            <div className="container">
                <h2>Fundraiser Registration</h2>
                <hr/>
                <br/>
                <form onSubmit={this.formSubmit} encType="multipart/form-data">
                    <div className={this.state.userName.isValid ? null : "has-error"}>
                        <input className="my-input-field"
                               name="userName"
                               placeholder="User name"
                               value={this.state.userName.value}
                               onChange={this.inputChangeHandler}
                        />
                        <span className="help-block">{this.state.userName.message}</span>
                    </div>

                    <div className={this.state.password.isValid ? null : "has-error"}>

                        <input className="my-input-field"
                               type="password"
                               name="password"
                               placeholder="Password"
                               value={this.state.password.value}
                               onChange={this.inputChangeHandler}
                        />
                        <span className="help-block">{this.state.password.message}</span>
                    </div>

                    <div className={this.state.email.isValid ? null : "has-error"}>
                        <input className="my-input-field"
                               name="email"
                               placeholder="E-mail"
                               value={this.state.email.value}
                               onChange={this.inputChangeHandler}
                        />
                        <span className="help-block">{this.state.email.message}</span>
                    </div>

                    <div className={this.state.goal.isValid ? null : "has-error"}>

                        <input className="my-input-field"
                               name="goal"
                               placeholder="Goal"
                               value={this.state.goal.value}
                               onChange={this.inputChangeHandler}
                        />
                        <span className="help-block">{this.state.goal.message}</span>
                    </div>

                    <div className={this.state.goalBody.isValid ? null : "has-error"}>
                        <textarea
                            type="textarea"
                            className="form-control"
                            name="goalBody"
                            placeholder="Short description of goal"
                            rows="3"
                            value={this.state.goalBody.value}
                            onChange={this.inputChangeHandler}
                        />
                        <span className="help-block">{this.state.goalBody.message}</span>
                    </div>

                    <div className={this.state.imgUrl.isValid ? null : "has-error"}>
                        <input className="my-input-field" id="hidden-input"
                               name="imgUrl"
                               placeholder="Url"
                               value={this.state.imgUrl.value}
                               onChange={this.inputChangeHandler}
                        />
                        <span className="help-block">{this.state.imgUrl.message}</span>
                    </div>

                    <div className={this.state.currencyType.isValid ? null : "has-error"}>
                        <select name="currencyType"
                                value={this.state.currencyType.value}
                                onChange={this.inputChangeHandler}
                                placeholder="Currency"
                                className="my-input-field">
                            <option key="" value="" disabled>Currency</option>
                            {Object.entries(this.state.currencies).map(([key, value]) => {
                                return <option key={key} value={key}>{value}</option>
                            })}
                        </select>
                        <span className="help-block">{this.state.currencyType.message}</span>
                    </div>
                    <span>Add an image to your goal (optional):</span>
                    <div className="file-div">
                        <input className="my-input-field file-input-field"
                               type="file"
                               name="file"
                               ref={input => {
                                   this.fileInput = input;
                               }}
                        />
                        <span className="help-block">{this.state.imgUrl.message}</span>
                    </div>
                    {this.state.isPacmanActive ? <img src={waiting} width="70px" height="70px" alt="Loading..."/> : null }
                    <br/>

                    <button className="btn btn-primary my-buttons" type="submit">Register</button>
                    <button className="btn btn-warning my-buttons" onClick={this.toLoginPage}>Login</button>

                </form>

            </div>
        );
    };
}

export default RegPage;