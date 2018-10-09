import React, {Component} from 'react';
import axios from 'axios';

import AccountListItem from '../../components/AccountListItem/AccountListItem';
import TransferListItem from '../../components/TransferListItems/TransferListItem';

class Accounts extends Component {

    state = {
        accounts: [],
        transfers: []
    };

    componentDidMount() {
        axios.get('/api/accounts')
            .then( response => {
                this.setState({
                    accounts: response.data
                });
            });

        axios.get('/api/transfers')
            .then( response => {
                this.setState({
                    transfers: response.data
                });
            })
    }

    render() {

        const accounts = this.state.accounts.map( account => {
            return <AccountListItem
                key={account.id}
                goal={account.goal}
                userName={account.userName}
                currency={account.currencyType}
                funds={account.funds}
            />
        });

        const transfers = this.state.transfers.map( transfer => {
            return <TransferListItem
                key={transfer.id}
                from={transfer.from}
                to={transfer.to}
                amount={transfer.amount}
                targetAmount={transfer.targetAmount}
                targetCurrency={transfer.targetCurrency}
                sourceCurrency={transfer.sourceCurrency}
                date={transfer.timeStamp}
                pending={transfer.pending}
                fulfilled={transfer.fulfilled}
                transactionDate={transfer.transactionDate}
            />
        });

        return (
            <div>
                <h2>Accounts</h2>
                <hr/><br/>
                <div className="container">
                    <table className="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th>Noble Goal</th>
                            <th>Owner of Account</th>
                            <th>Funds Raised</th>
                        </tr>
                        </thead>
                        <tbody>
                            {accounts}
                        </tbody>
                    </table>
                </div>
                <br/><br/><br/>
                <h2>All Transfers</h2>
                <hr/><br/>
                <div className="container">
                    <table className="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th className="col-md-2">From</th>
                            <th className="col-md-2">To</th>
                            <th className="col-md-2">Amount target</th>
                            <th className="col-md-2">Amount source</th>
                            <th className="col-md-2">Date</th>
                            <th className="col-md-2">Status</th>
                        </tr>
                        </thead>
                        <tbody>
                            {transfers}
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default Accounts;