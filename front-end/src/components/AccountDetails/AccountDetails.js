import React from 'react';

function accountDetails(props) {
    let balanceText = props.balance + " " + props.currency;

    return (
        <div>
            <h2>My Account</h2>
            <hr/><br/>
            <label className="input-label">User Name:</label>
                <br/>
            <input className="my-input-field my-input-field-disabled" name="username" disabled="true"
                   placeholder={props.username} />
            <br/>
            <label className="input-label">Goal:</label>
            <br/>
            <input className="my-input-field my-input-field-disabled" name="goal"  disabled="true"
                   placeholder={props.goal} />
            <br/>
            <label className="input-label">Balance:</label>
            <br/>
            <input className="my-input-field my-input-field-disabled" name="balance"  disabled="true"
                   placeholder={balanceText} />
            <br/>
            <label className="input-label">Funds Raised:</label>
            <br/>
            <input className="my-input-field my-input-field-disabled" name="funds"  disabled="true"
                   placeholder={props.funds} />
        </div>
    )
}

export default accountDetails;