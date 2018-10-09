import React, {Component} from 'react';
import axios from 'axios';

import AccountDetails from '../../components/AccountDetails/AccountDetails';
import MyTransfers from '../../components/MyTransfers/MyTransfers';

class MyAccount extends Component {

    MY_ACCOUNT_URL = '/api/accounts/myAccountDetails';

    state = {
        myAccountDetails: {},
        filteredTargetTransfers: [],
        filteredSourceTransfers: [],
        searchFieldValue: '',
        myCurrency: ''
    };

    componentDidMount() {
        axios.get(this.MY_ACCOUNT_URL)
            .then( (response) => {
                this.setState({
                    myAccountDetails: response.data,
                    filteredTargetTransfers: [...response.data.targetTransfers],
                    filteredSourceTransfers: [...response.data.sourceTransfers],
                    myCurrency: response.data.currencyType
                });
            })
            .catch( error => {
                console.log(error);
            })
    };

    searchFieldChangeHandler = (event) => {
        let updatedSearchFieldValue = event.target.value;

        let updatedFilteredTargetTransfers = this.state.myAccountDetails.targetTransfers.filter((transfer) =>
            this.searchForMatches(transfer, updatedSearchFieldValue));

        let updatedFilteredSourceTransfers = this.state.myAccountDetails.sourceTransfers.filter((transfer) =>
            this.searchForMatches(transfer, updatedSearchFieldValue));

        this.setState({
            filteredTargetTransfers: updatedFilteredTargetTransfers,
            filteredSourceTransfers: updatedFilteredSourceTransfers,
            searchFieldValue: updatedSearchFieldValue
        });
    };

    searchForMatches = (transfer, updatedSearchFieldValue) => {
        let foundMatch = false;

        for (let property in transfer) {
            if (transfer.hasOwnProperty(property)) {
                if ( !(property === 'id') && !(property === 'pending') ) {
                    let searchFor = new RegExp(updatedSearchFieldValue, 'i');

                    if (searchFor.test(transfer[property])) {
                        foundMatch = true;
                        break;
                    }
                }
            }
        }

        return foundMatch;
    };

    render() {

        return (
            <div className="container">
                <AccountDetails
                    username={this.state.myAccountDetails.userName}
                    goal={this.state.myAccountDetails.goal}
                    balance={this.state.myAccountDetails.balance}
                    funds={this.state.myAccountDetails.funds}
                    currency={this.state.myCurrency}
                />
                <br/><br/><br/>
                <MyTransfers
                    incoming={this.state.filteredTargetTransfers}
                    outgoing={this.state.filteredSourceTransfers}
                    myCurrency={this.state.myCurrency}
                    searchFieldChangeHandler={this.searchFieldChangeHandler}
                    searchFieldValue={this.state.searchFieldValue}
                />
            </div>
        )
    }

}

export default MyAccount;