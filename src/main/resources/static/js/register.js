function validatePassword(){

}

function sendAuthRequest(){

    let form = document.forms.RegForm;
    let UserRegObject = {
        name:form.elements.name.value,
        surname:form.elements.surname.value,
        login:form.elements.surname.value,
        password:form.elements.password.value,
        email:form.elements.email.value
    }

    console.log(UserRegObject);

    fetch('http://localhost:8080/register',{
        method:'POST',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify(UserRegObject)
    }).then(result=>{
        if(result.ok){
            window.location.replace('http://localhost:8080/loginin');
        } else {
            alert("this user already exist");
        }
    })
}