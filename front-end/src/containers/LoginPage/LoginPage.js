import React, {Component} from 'react';
import axios from 'axios';

class LoginPage extends Component {

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
        }
    };

    inputChangeHandler = (event) => {
        const updatedFromField = {};
        updatedFromField.value = event.target.value;
        updatedFromField.isValid = true;

        this.setState({
            [event.target.name]: updatedFromField
        })
    };

    formSubmit = (event) => {
        event.preventDefault();

        const formData = {};
        for (let formElementIdentifier in this.state) {
            formData[formElementIdentifier] = this.state[formElementIdentifier].value;
        }

        axios.get('/api/accounts/me', {
            auth: {
                username: this.state.userName.value,
                password: this.state.password.value
            }
        })
            .then(response => {
                localStorage.setItem('user', JSON.stringify(response.data));
                this.props.history.push('/');
            })
            .catch(() => {
                const errors = {
                    fieldErrors: [
                        {
                            field: 'userName',
                            message: 'Invalid username or password'
                        }
                    ]
                };
                this.validationHandler(errors);
            });
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
        });
    };

    toRegPage = () => {
        this.props.history.push('/registration');
    };

    render() {
        return (
            <div className="container">
                <h2>Fundraiser Login</h2>
                <hr/>
                <br/>
                <form onSubmit={this.formSubmit}>
                    <div className={this.state.userName.isValid ? null : "has-error"}>
                        <label></label>
                        <input className="my-input-field"
                               placeholder="Username"
                               name="userName"
                               value={this.state.userName.value}
                               onChange={this.inputChangeHandler}
                        />
                        <span className="help-block">{this.state.userName.message}</span>
                    </div>

                    <div className={this.state.password.isValid ? null : "has-error"}>
                        <label></label>
                        <br/>
                        <input className="my-input-field"
                               placeholder="Password"
                               type="password"
                               name="password"
                               value={this.state.password.value}
                               onChange={this.inputChangeHandler}
                        />
                        <span className="help-block">{this.state.password.message}</span>
                    </div>
                    <br/>
                    <button className="btn btn-warning my-buttons" type="submit">Login</button>
                    <button className="btn btn-primary my-buttons" onClick={this.toRegPage}>Registration</button>
                </form>
            </div>
        );
    };
}

export default LoginPage;