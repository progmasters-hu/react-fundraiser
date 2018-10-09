import React, {Component} from 'react';
import {Switch, Route} from 'react-router-dom';
import MyAccount from '../containers/MyAccount/MyAccount';
import Transfer from '../containers/Transfer/Transfer';
import Accounts from '../containers/Accounts/Accounts';
import PrivateRoute from './PrivateRoute'
import RegPage from '../containers/Registration/RegPage';
import NavBar from './NavBar'
import LoginPage from "../containers/LoginPage/LoginPage";
import Logout from "../containers/Logout/Logout";
import EmailConfirmation from "../components/EmailConfirmation/EmailConfirmation";
import NoMatch from '../components/ErrorPage/ErrorPage';
import GoalList from "../containers/GoalList/GoalList";

class Layout extends Component {
    render() {
        return (
            <div>
                {JSON.parse(localStorage.getItem('user')) ? <NavBar/> : null}
                <div className="jumbotron">
                    <Switch>
                        <Route path="/emailConfirm/:code/:id" exact component={EmailConfirmation} />
                        <PrivateRoute path="/" exact component={GoalList} />
                        <PrivateRoute path="/goals" exact component={GoalList} />
                        <PrivateRoute path="/myAccount" exact component={MyAccount} />
                        <PrivateRoute path="/transferFunds" exact component={Transfer} />
                        <PrivateRoute path="/transferFunds/:id" exact component={Transfer} />
                        <PrivateRoute path="/accounts" exact component={Accounts} />
                        <Route path="/registration" exact component={RegPage} />
                        <Route path="/login" exact component={LoginPage} />
                        <Route path="/logout" exact component={Logout} />
                        <Route path="/" component={LoginPage} />
                        <Route component={NoMatch} />

                    </Switch>
                </div>

            </div>
        )
    }
}

export default Layout;