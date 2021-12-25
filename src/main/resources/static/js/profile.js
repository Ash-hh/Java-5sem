let token = localStorage.getItem('token');

function profileOnLoad(){
    fetch('/getUserInfo',{
        headers:{'Authorization': `Bearer ${token}`}
    })
        .then(result=>{
            if(result.ok){
                return result.json()
            }
        }).then(data=>{
            profile_info.innerHTML=`
                <p>Account Status: ${data.userRole == 'ROLE_ADMIN' ? 'Admin' : 'User'}</p>
                <p>Login: ${data.login} </p>
                <p>Name: ${data.name}</p>
                <p>Surname: ${data.surname}</p>
                <p>Email: ${data.email}</p>
            `
    });
}