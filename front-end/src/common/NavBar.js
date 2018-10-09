import React from 'react';
import {Link} from 'react-router-dom';

function NavBar() {
    return (
        <nav className="navbar navbar-default navbar-fixed-top">
            <div className="container">
                <div className="navbar-header">
                    <button type="button" className="navbar-toggle collapsed" data-toggle="collapse"
                            data-target="#myNavbar"
                            aria-expanded="false" aria-controls="navbar">
                        <span className="sr-only">Toggle navigation</span>
                        <span className="icon-bar"/>
                        <span className="icon-bar"/>
                        <span className="icon-bar"/>
                    </button>
                    <a className="navbar-brand" href="/">FUNDRAISER</a>
                </div>
                <div id="myNavbar" className="navbar-collapse collapse">
                    <ul className="nav navbar-nav">
                        <li><Link to="/goals">Goals</Link></li>
                        <li><Link to="/myAccount">My Account</Link></li>
                        <li><Link to="/transferFunds">Transfer Funds</Link></li>
                        <li><Link to="/accounts">Accounts</Link></li>
                        <li><Link to="/logout">Logout</Link></li>
                    </ul>
                </div>
            </div>
        </nav>
    )
}

export default NavBar;