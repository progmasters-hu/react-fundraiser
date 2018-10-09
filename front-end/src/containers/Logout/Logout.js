import { Component } from 'react'
import axios from "axios";

class Logout extends Component {
    componentDidMount() {
        axios.post('/logout')
            .then(() => {
                localStorage.removeItem('user');
                this.props.history.push('/login')
            })
            .catch(error => {
                console.log(error.response);
            });
    }

    render() {
        return null
    }
}

export default Logout;