let token = localStorage.getItem('token');

function indexOnLoadFunc(){
    if(token){
        fetch('/getUserInfo',{
            headers:{'Authorization': `Bearer ${token}`}
        })
            .then(result=>{

                if(result.ok){
                    return result.json()
                }
            }).then(data=>{
                console.log(data);
                index_header.innerHTML=`
                    <a href="/logout">Log Out</a>
                    <a href="/profile/${data.login}">Profile</a>`

                if(data.userRole == 'ROLE_ADMIN'){
                    index_header.innerHTML+=` <a href="/admin">Admin</a>`
                }
            });
    } else {

        index_header.innerHTML='<a href="/loginin">Login</a> <a href="/registration">Registration</a>'
            

    }

}
