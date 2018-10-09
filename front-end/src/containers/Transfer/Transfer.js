import React, {Component} from 'react';
import axios from 'axios';
import waiting from '../../images/Eclipse-0.6s-76px.gif';

class Transfer extends Component {

    state = {
        sourceAccountName: '',
        balance: 0,
        accounts: [],
        currencies: [],
        pending: false,
        transferId: 0,
        confirmationCode: '',
        currencyForTransfer: '',
        toAmount: 0,
        myCurrency: '',
        invalidConfirmationCodeMessage: '',
        destinationGoal: '',
        timedTransfer: false,
        date: new Date().toISOString().substr(0, 10),
        time: '',
        transferValues: null,
        rates: [],
        transferForm: {
            amount: {
                value: '',
                isValid: true,
                message: ''
            },
            to: {
                value: '',
                isValid: true,
                message: ''
            }
        }
    };

    componentDidMount() {
        axios.get('/api/rates')
            .then(response => {
                this.setState({rates: response.data.rates});
            })
            .then(() => {
                if (this.props.match.params.id) {
                    let recentForm = {...this.state.transferForm};
                    recentForm.to.value = this.props.match.params.id;

                    this.setState({transferForm: recentForm});
                }
                axios.get('/api/transfers/newTransferData')
                    .then(response => {

                        let usd = this.state.rates.USD;
                        let actualCurrency = this.state.rates[response.data.myCurrency];

                        let minTransferValue = actualCurrency/usd*50;
                        let maxTransferValue = actualCurrency/usd*1000;

                        let transferValues = {};
                        transferValues.min = minTransferValue;
                        transferValues.max = maxTransferValue;

                        let currencyForTransfer = response.data.targetCurrencies[this.state.transferForm.to.value];

                        this.setState({
                            sourceAccountName: response.data.sourceAccountName,
                            balance: response.data.balance,
                            accounts: response.data.targetAccounts,
                            currencies: response.data.targetCurrencies,
                            myCurrency: response.data.myCurrency,
                            currencyForTransfer: currencyForTransfer,
                            transferValues: transferValues
                        });
                    })
                    .catch(error => {
                        console.log(error)
                    });
            })
    }

    inputChangeHandler = (event) => {
        let formElementName = event.target.name;

        if (formElementName === 'confirmationCode') {
            this.setState({confirmationCode: event.target.value})


        } else if (formElementName === 'timedTransfer') {
            this.setState((prevState) => {
                return ({
                    timedTransfer: !prevState.timedTransfer,
                    date: new Date().toISOString().substr(0, 10),
                    time: ''
                });
            })


        } else if (formElementName === 'date') {
            this.setState({date: event.target.value})


        } else if (formElementName === 'time') {
            this.setState({time: event.target.value})


        } else if(formElementName === 'amount' && this.state.transferForm.to.value !== ''){
            const updatedTransferForm = {...this.state.transferForm};
            updatedTransferForm.amount.value = event.target.value;
            const calculateValue = updatedTransferForm['to'].value === '' ? '' :
                (Math.round(event.target.value * this.calculatingExchangeRate(this.state.currencyForTransfer)* 100) / 100).toFixed(2);
            this.setState({transferForm: updatedTransferForm,
                toAmount: calculateValue});


        } else if (formElementName === 'to'){
            const updatedTransferForm = {...this.state.transferForm};
            const updatedFormElement = {...updatedTransferForm[formElementName]};
            let recentAmount = this.state.toAmount;

            updatedFormElement.value = event.target.value;
            updatedTransferForm[formElementName] = updatedFormElement;

            let currencyForTransfer = this.state.currencies[event.target.value];

            if(this.state.transferForm.amount.value !== ''){
                recentAmount = (Math.round(this.state.transferForm.amount.value * this.calculatingExchangeRate(this.state.currencies[event.target.value])* 100) / 100).toFixed(2);
            }

            this.setState({transferForm: updatedTransferForm,
                currencyForTransfer: currencyForTransfer,
                toAmount: recentAmount})
        }
        else {
            const updatedTransferForm = {...this.state.transferForm};
            const updatedFormElement = {...updatedTransferForm[formElementName]};
            updatedFormElement.value = event.target.value;
            updatedTransferForm[formElementName] = updatedFormElement;
            this.setState({transferForm: updatedTransferForm});
        }

    };

    transferSubmit = (event) => {
        this.setState({isPacmanActive: true});
        event.preventDefault();

        let transactionTime = '';

        if (this.state.timedTransfer === true) {
            let dateOfTransaction = this.state.date;
            let timeOfTransaction = this.state.time;

            if (timeOfTransaction.length === 5) {
                timeOfTransaction += ':00';
            }

            transactionTime = dateOfTransaction + ' ' + timeOfTransaction;
        }

        let formData = {transactionDate: transactionTime};

        for (let formElementID in this.state.transferForm) {
            if (this.state.transferForm.hasOwnProperty(formElementID)) {
                formData[formElementID] = this.state.transferForm[formElementID].value;
            }
        }

        formData.targetAmount = this.state.toAmount;

        axios({
            method: 'POST',
            url: '/api/transfers',
            data: formData
        })
            .then((response) => {
                let destinationGoal = response.data.to;
                let transferId = response.data.id;

                this.setState({
                    pending: true,
                    destinationGoal: destinationGoal,
                    transferId: transferId
                });
            }).catch((error) => {
            this.setState({isPacmanActive: false});
            this.validationHandler(error.response.data);
        })
    };

    confirmSubmit = (event) => {
        event.preventDefault();

        const formData = {
            id: this.state.transferId,
            confirmationCode: this.state.confirmationCode
        };

        axios({
            method: 'POST',
            url: '/api/transfers/confirmation',
            data: formData
        }).then(() => {
            this.props.history.push('/')
        }).catch((error) => {
            this.setState({isPacmanActive: false});
            console.log(error.response);
            this.invalidConfirmationCodeHandler(error.response.data);
        })
    };

    invalidConfirmationCodeHandler = (error) => {
        setTimeout(
            () => this.props.history.push('/'),
            5000);

        this.setState({invalidConfirmationCodeMessage: error.message});


    };

    validationHandler = (error) => {
        const updatedTransferForm = {...this.state.transferForm};

        for (let field in this.state.transferForm) {
            const updatedFormElement = {
                ...updatedTransferForm[field]
            };
            updatedFormElement.isValid = true;
            updatedFormElement.message = '';
            updatedTransferForm[field] = updatedFormElement;
        }

        for (let fieldError of error.fieldErrors) {
            const updatedFormElement = {
                ...updatedTransferForm[fieldError.field]
            };
            updatedFormElement.isValid = false;
            updatedFormElement.message = fieldError.message;
            updatedTransferForm[fieldError.field] = updatedFormElement;
        }

        this.setState({transferForm: updatedTransferForm});
    };

    calculatingExchangeRate = (currency) => {

        const myCurrency = '' + this.state.myCurrency;
        if (currency === myCurrency) {
            return 1;
        } else if (currency === 'HUF' && myCurrency === 'USD') {
            return (this.state.rates.HUF / this.state.rates.USD);
        } else if (currency === 'HUF' && myCurrency === 'EUR') {
            return this.state.rates.HUF;
        } else if (currency === 'HUF' && myCurrency === 'GBP') {
            return (this.state.rates.HUF / this.state.rates.GBP);
        }
        else if (currency === 'USD' && myCurrency === 'HUF') {
            return (this.state.rates.USD / this.state.rates.HUF);
        } else if (currency === 'USD' && myCurrency === 'EUR') {
            return this.state.rates.USD;
        } else if (currency === 'USD' && myCurrency === 'GBP') {
            return (this.state.rates.USD / this.state.rates.GBP);
        }
        else if (currency === 'EUR' && myCurrency === 'HUF') {
            return (1 / this.state.rates.HUF);
        } else if (currency === 'EUR' && myCurrency === 'USD') {
            return (1 / this.state.rates.USD);
        } else if (currency === 'EUR' && myCurrency === 'GBP') {
            return (1 / this.state.rates.GBP);
        }
        else if (currency === 'GBP' && myCurrency === 'HUF') {
            return (this.state.rates.GBP / this.state.rates.HUF);
        } else if (currency === 'GBP' && myCurrency === 'USD') {
            return (this.state.rates.GBP / this.state.rates.USD);
        } else if (currency === 'GBP' && myCurrency === 'EUR') {
            return this.state.rates.GBP;
        }
        return -1;
    };

    render() {
        let balanceText = this.state.balance + " " + this.state.myCurrency;
        let amountPlaceholder="";

        if (this.state.transferValues){
            amountPlaceholder = Math.ceil(this.state.transferValues.min) + " " + this.state.myCurrency + " - "
                + Math.ceil(this.state.transferValues.max) + " " + this.state.myCurrency;
        }

        return (
            <div className="container">
                <h2>Transfer Funds</h2>
                <hr/>
                <br/>

                {this.state.pending
                    ? <div>
                        <h5>You have just received an email with a confirmation code.<br/>
                        To complete the transfer process please copy it below.</h5>
                        <div className="invalidconfirmationcode">
                            If you leave this page, the transaction will be terminated.
                        </div>

                        <div>
                            From: {this.state.sourceAccountName}<br/>
                            To: {this.state.destinationGoal}<br/>
                            Amount: {this.state.transferForm.amount.value}<br/>
                            <br/>
                        </div>

                        <form onSubmit={this.confirmSubmit}>
                            <div>
                                <label>Confirmation code:</label>
                                <br/>
                                <input className="form-control my-input-field"
                                       name="confirmationCode"
                                       placeholder="Insert confirmation code here"
                                       onChange={this.inputChangeHandler}
                                       value={this.state.confirmationCode}/>
                            </div>
                            <br/>

                            {this.state.invalidConfirmationCodeMessage === ''
                                ?
                                <button className="btn btn-primary my-buttonsx" type="submit">
                                    Confirm transfer
                                </button>
                                :
                                <span className="invalidconfirmationcode">
                                    {this.state.invalidConfirmationCodeMessage + 'Redirecting to My account in 5 seconds.'}
                                    </span>
                            }
                            </form>
                    </div>

                    : <form onSubmit={this.transferSubmit}>
                        <div>
                            <label className="input-label">From:</label>
                            <br/>
                            <input className="my-input-field my-input-field-disabled" name="from" disabled="true"
                                   placeholder={this.state.sourceAccountName}/>
                            <span className="invalid-field-help-block"></span>
                        </div>

                        <div className={this.state.transferForm.to.isValid ? null : "has-error"}>
                            <label className="control-label input-label">To:</label>
                            <br/>
                            <select className="form-control my-input-field"
                                    name="to"
                                    onChange={this.inputChangeHandler}
                                    value={this.state.transferForm.to.value || ''}>
                                <option key="" value="" disabled>-- Choose a goal to support --</option>
                                {Object.entries(this.state.accounts).map(([key, value]) => {
                                    if (this.props.match.params.name !== null && this.props.match.params.name === value){
                                        return <option key={key} value={key} selected>{value}</option>
                                    } else {
                                        return <option key={key} value={key}>{value}</option>

                                    }
                                })}
                            </select>
                            <span className="help-block">{this.state.transferForm.to.message}</span>
                        </div>

                        <div className={this.state.transferForm.amount.isValid ? null : "has-error"}>
                            <label className="input-label">Amount: ({this.state.myCurrency})</label>
                            <br/>
                            <input className="form-control my-input-field"
                                   name="amount"
                                   type="number"
                                   placeholder={amountPlaceholder}
                                   onChange={this.inputChangeHandler}
                                   value={this.state.transferForm.amount.value}/>
                            <span className="help-block">{this.state.transferForm.amount.message}</span>
                            <div>{this.state.transferForm.to.value === '' ? '' : this.state.toAmount} {this.state.currencyForTransfer}</div>
                            <br/>
                        </div>

                        <div>
                            <label className="input-label">Timed transfer:</label>
                            <br/>
                            <input type="checkbox"
                                   name="timedTransfer"
                                   onChange={this.inputChangeHandler}
                                   checked={this.state.timedTransfer}/>
                        </div>

                        {this.state.timedTransfer
                            ? <div>
                                <div>
                                    <div className="invalid-field-help-block">(Be careful about the format
                                        mm/dd/yyyy)
                                    </div>
                                    <label className="input-label">Choose date :</label>
                                    <br/>
                                    <input className="form-control my-input-field"
                                           type="date"
                                           name="date"
                                           onChange={this.inputChangeHandler}
                                           value={this.state.date}/>
                                </div>

                                <div>
                                    <div className="invalid-field-help-block">(Do not forget to set AM/PM at the
                                        end)
                                    </div>
                                    <label className="input-label">Choose time :</label>
                                    <br/>
                                    <input className="form-control my-input-field"
                                           type="time"
                                           name="time"
                                           step="1"
                                           onChange={this.inputChangeHandler}
                                           value={this.state.time}/>
                                </div>
                            </div>
                            : null}

                        <div>
                            <label className="input-label">My Balance:</label>
                            <br/>
                            <input className="my-input-field my-input-field-disabled" name="my-balance"
                                   disabled="true"
                                   placeholder={balanceText}/>
                            <span className="invalid-field-help-block"></span>
                        </div>
                        {this.state.isPacmanActive ? <img src={waiting} width="70px" height="70px" alt="Loading..."/> : null }
                        <br/>
                        <button className="btn btn-primary my-buttons" type="submit">Transfer</button>
                    </form>
                }
            </div>
        );

    }
}

export default Transfer;