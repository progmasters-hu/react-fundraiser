import React from 'react';

import './myTransfers.css'
import TransferListItem from '../TransferListItems/TransferListItem';

function myTransfers(props) {

    let incomingTransfers;
    if (props.incoming) {
        incomingTransfers = props.incoming.map( incomingTransfer => {
            return (
                <TransferListItem
                    key={incomingTransfer.id}
                    id={incomingTransfer.id}
                    from={incomingTransfer.from}
                    to={incomingTransfer.to}
                    targetAmount={incomingTransfer.amount}
                    amount={incomingTransfer.targetAmount}
                    sourceCurrency={incomingTransfer.targetCurrency}
                    targetCurrency={incomingTransfer.sourceCurrency}
                    date={incomingTransfer.timeStamp}
                    pending={incomingTransfer.pending}
                    fulfilled={incomingTransfer.fulfilled}
                    transactionDate={incomingTransfer.transactionDate}
                />

            )
        });
    }

    let outgoingTransfers;
    if (props.outgoing) {
        outgoingTransfers = props.outgoing.map( outgoingTransfer => {
            return (
                <TransferListItem
                    key={outgoingTransfer.id}
                    id={outgoingTransfer.id}
                    from={outgoingTransfer.from}
                    to={outgoingTransfer.to}
                    amount={outgoingTransfer.amount}
                    targetAmount={outgoingTransfer.targetAmount}
                    targetCurrency={outgoingTransfer.targetCurrency}
                    sourceCurrency={props.myCurrency}
                    date={outgoingTransfer.timeStamp}
                    pending={outgoingTransfer.pending}
                    fulfilled={outgoingTransfer.fulfilled}
                    transactionDate={outgoingTransfer.transactionDate}
                />
            )
        })
    }

    return (
        <div>
            <h2>My Transfers</h2>
            <hr/>

            <div>
                <input className="form-control my-input-field"
                       name="searchFor"
                       type="text"
                       placeholder="Filter transactions"
                       onChange={props.searchFieldChangeHandler}
                       value={props.searchFieldValue}
                />
            </div>

            <h4 className="my-transfers-h4">Incoming:</h4>
            <table className="table table-bordered table-striped">
                <thead>
                    <tr className="success">
                        <th className="col-md-2">From</th>
                        <th className="col-md-2">To</th>
                        <th className="col-md-2">Amount source</th>
                        <th className="col-md-2">Amount target</th>
                        <th className="col-md-2">Date</th>
                        <th className="col-md-2">Status</th>
                    </tr>
                </thead>
                <tbody>
                    {incomingTransfers}
                </tbody>
            </table>
            <br/>
            <h4 className="my-transfers-h4">Outgoing:</h4>
            <table className="table table-bordered table-striped">
                <thead>
                    <tr className="danger">
                        <th className="col-md-2">From</th>
                        <th className="col-md-2">To</th>
                        <th className="col-md-2">Amount source</th>
                        <th className="col-md-2">Amount target</th>
                        <th className="col-md-2">Date</th>
                        <th className="col-md-2">Status</th>
                    </tr>
                </thead>
                <tbody>
                    {outgoingTransfers}
                </tbody>
            </table>
        </div>
    )
}

export default myTransfers;