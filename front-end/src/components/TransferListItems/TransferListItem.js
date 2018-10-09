import React from 'react';

function TransferListItem(props) {
    let status;
    let color;

    if (props.pending) {
        status = 'Unsuccessful';
        color = "red";
    } else if (!props.fulfilled) {
        status = props.transactionDate.substr(0, props.transactionDate.length - 2);
        color = "rgb(200, 200, 200)";
    } else {
        status = 'Successful';
        color = "rgb(204, 255, 17";
    }

    return (
            <tr className="table-cell" >
                <td>{props.from}</td>
                <td>{props.to}</td>
                <td>{props.amount} {props.sourceCurrency}</td>
                <td>{props.targetAmount} {props.targetCurrency}</td>
                <td>{props.date.substr(0, props.date.length - 2)}</td>
                <td style={{color:color}}>{status}</td>
            </tr>
        )
}

export default TransferListItem;