import React from 'react';
import noImage from '../../images/noimage.png';
import {Link} from 'react-router-dom';
import '../../index.css';

function GoalListItem(props) {
    return (
        <div className="card mb-3">
            <h3 className="card-header">{props.account.goal}</h3>
            <h6 className="card-title">{props.account.userName}</h6>
            <div className="card-body">
                <div className="image-div">
                    <div className="cardBackground" style={{backgroundImage: `url(${props.account.imgUrl !== null ? props.account.imgUrl : noImage})`}}></div>

                </div>
                <div className="card-text">
                    {props.account.goalBody}
                </div>

            </div>
            <div className="card-footer text-muted">
                Funds: {props.account.funds} {props.account.currencyType}
                <Link to={'/transferFunds/' + props.id}>
                    {props.id=== props.myId ?
                        null :
                        <button type="button" className="btn btn-primary btn-sm orange-button">Transfer</button>}
                </Link>
            </div>
        </div>
    );
}

export default GoalListItem;