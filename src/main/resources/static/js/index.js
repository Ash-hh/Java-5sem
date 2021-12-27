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
                    <a href="/loginin">Log Out</a>
                    <a href="/profile/${data.login}">Profile</a>`

                if(data.userRole == 'ROLE_ADMIN'){
                    index_header.innerHTML+=` <a href="admin/adminpage/NoTable">Admin</a>`
                }
                originRows = Array.from(document.getElementById("carsTable").rows);

                console.log(document.getElementById("carsTable"));
            });
    } else {

        index_header.innerHTML='<a href="/loginin">Login</a> <a href="/registration">Registration</a>'


    }

}

function openAdminPage(){
    fetch("/admin/getadminaccess",{
        headers:{'Authorization': `Bearer ${token}`},
        redirect:"follow"
    }).then(result=>{
        if(result.redirected){
            window.location.replace(result.url);
        }
    })

}


//очень стыдно, но как есть;
function filterChange(){
    
    let selectedType = filterSelect.value;
    let arr = Array.prototype.slice.call(originRows);
    let newRows = [];
    arr.forEach(element=>{
        if(element.children[3].innerText == selectedType){

            newRows.push(element);
        }
    })
    let table = document.getElementById("carsTable");

    let rows = table.rows.length;
    for(let i = 0;i<rows;i++){
        table.deleteRow(0);
    }

    newRows.forEach((element,index)=>{
        table.insertRow(index).append(element);
    })




}
