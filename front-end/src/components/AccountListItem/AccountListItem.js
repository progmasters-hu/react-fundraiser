import React from 'react';

function accountListItem(props) {
    return (
        <tr className="table-cell">
            <td>{props.goal}</td>
            <td>{props.userName}</td>
            <td>{props.funds}  {props.currency}</td>

        </tr>
    )
}

export default accountListItem;