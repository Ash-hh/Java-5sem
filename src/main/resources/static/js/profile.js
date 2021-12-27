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
                <p>Balance: ${data.balance}</p>
                <a onclick="PopUpBalanceShow()">Top Up</a>
            `
            newBalance.value = data.balance;
    });
}
let orderInfo;

function popUpShow(orderId,dateStart,dateEnd,costPerDay){

    setMinDate(dateEnd);

    orderInfo={
        orderId:orderId,
        dateStart:dateStart,
        costPerDay:costPerDay
    }
    popUpRent.style.visibility = "visible"
}

function PopUpBalanceShow(){
    PopUpBalance.style.visibility = "visible"
}

function updateBalance(){

    fetch("/user/userUpdate",{
        method:'PUT',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify({
            newBalance:newBalance.value
        })
    }).then(result=>{
        if(result.ok){
            window.location.replace(document.URL)
        }
    })

}

function popUpHide(){
    popUp.style.visibility = "hidden";
}

function updateRentDate(){
    console.log( document.getElementById("newRentDate").value);
    let sum = costCalculate(orderInfo.dateStart,
        document.getElementById("newRentDate").value,orderInfo.costPerDay)

    if(confirm(`New rent cost:${sum}`)){

        fetch('/user/updateOrder',{
            method:'PUT',
            headers:{'Content-Type':'application/json', 'Authorization': `Bearer ${token}`},
            body:JSON.stringify({
                orderId:orderInfo.orderId,
                newRentDateEnd: document.getElementById("newRentDate").value,
                newRentCost:sum
            })
        }).then(result=>{
            if(result.ok){
                popUpHide();
                window.location.replace(document.URL);
            }
        })
    }
}

function endRent(orderId,dateRentStart,costPerDay){

    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1;
    var yyyy = today.getFullYear();

    today = yyyy + '-' + mm + '-' + dd;

    let newSumRentCost
    let status='Rent_End'
    if(Date.parse(today) > Date.parse(dateRentStart) || Date.parse(today) == Date.parse(dateRentStart) ){
        let timeDiff = Math.abs(Date.parse(today) - Date.parse(dateRentStart));
        let diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));

        newSumRentCost = diffDays == 0 ? costPerDay : diffDays * costPerDay;
    } else {
        newSumRentCost = 0;
        status = 'Rent_End_Before_Start';
    }

    fetch('/user/updateOrder',{
        method:'PUT',
        headers:{'Content-Type':'application/json', 'Authorization': `Bearer ${token}`},
        body:JSON.stringify({
            orderId:orderId,
            newStatus:status,
            newRentCost:newSumRentCost,
            newRentDateEnd:today
        })
    }).then(result=>{
        if(result.ok){
            window.location.replace(document.URL);
        } else {
            alert('Rent Not ended!')
        }
    })


}

function deleteRent(orderId){
    console.log("delete");
    fetch(`/user/updateOrder`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`},
        body:JSON.stringify({
            orderId:orderId,
            newStatus:'Deleted'
        })
    }).then(result=>{
        if(result.ok){
            window.location.replace(document.URL);
        }
    })
}

function setMinDate(date){
    console.log(date);
    document.getElementById("newRentDate").setAttribute("min", date);
}

function costCalculate(dateStart,dateEnd,carCostPerDay){
    let start = Date.parse(dateStart);
    let end = Date.parse(dateEnd);

    let timeDiff = Math.abs(end - start);
    let diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));


    return diffDays == 0 ? carCostPerDay : diffDays * carCostPerDay;
}