import React, {Component} from 'react';
import axios from 'axios/index';

import GoalListItem from '../../components/GoalListItem/GoalListItem';

class GoalList extends Component {

    state = {
        goals: [],
        myId: 0
    };

    componentDidMount() {
        console.log('in goallist cdm');
        axios.get('/api/accounts/myId')
            .then(res => {
                this.setState({myId: res.data})
            })
            .then(() => {
                axios.get('/api/accounts')
                    .then(response => {
                        this.setState({
                            goals: response.data
                        })
                    })
                    .catch(error => {
                        console.log(error.response);
                    });
            })
    }

    render() {

        let goalList = this.state.goals.map(account => {
            return (
                <GoalListItem
                    key={account.id}
                    account={account}
                    id={account.id}
                    myId={this.state.myId}
                />
            )
        });
        return (
            <div className="card-list-item">
                {goalList}
            </div>
        )
    }
    ;

}

export default GoalList;