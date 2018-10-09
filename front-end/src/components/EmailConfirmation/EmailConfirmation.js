import React from 'react';
import axios from "axios/index";

function emailConfirmation(props) {

    if(props.match.params.code !== "" && props.match.params.id !== ""){
        const formData = {
            id: props.match.params.id,
            confirmationCode: props.match.params.code
        };

        axios({
            method: 'POST',
            url: '/api/transfers/confirmation',
            data: formData
        }).then( () => {
            props.history.push('/myAccount')
        }).catch( (error) => {
            console.log(error.response);
        })
    } else {
        props.history.push("/");
    }

    return <div/>;
}

export default emailConfirmation;