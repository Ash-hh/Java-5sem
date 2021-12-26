
function showTable(TableName){

    if(TableName != 'NoTable'){
        UsersInfo.style.visibility = "hidden";
        CarsInfo.style.visibility = "hidden";
        OrdersInfo.style.visibility = "hidden";

        document.getElementById(TableName).style.visibility = "visible";
    }

}

function HideAll(){
    UsersInfo.style.visibility = "hidden";
    CarsInfo.style.visibility = "hidden";
    OrdersInfo.style.visibility = "hidden";
}

function DeleteUser(userId){
    if(confirm('You cant rollback delete, are you sure?')){
        fetch(`/admin/deleteUser/${userId}`,{
            method:'DELETE'
        }).then(result=>{
            if(result.ok){
                window.location.replace(document.location.origin+'/admin/adminpage/UsersInfo');;
            }
        })
    }
}

function ChangeActivity(userId,IsActive){
    let newActivity = IsActive ? false : true;
    fetch("/admin/updateUser",{
        method:'PUT',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify({
            userId:userId,
            newActivity:newActivity
        })
    }).then(result=>{
        if(result.ok){
            window.location.replace(document.URL);
        }
    })
}

function ChangeRole(userId,userRole){

    let newRole = userRole == 'ROLE_ADMIN' ? 'ROLE_USER' : 'ROLE_ADMIN';

    fetch("/admin/updateUser",{
        method:'PUT',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify({
            userId:userId,
            newRole:newRole
        })
    }).then(result=>{
        if(result.ok){
            window.location.replace(document.URL);
        }
    })
}

let carId;
function PopUpShow(car_id, carName,carType,costPerDay){
    HideAll();
    if(car_id){
        carId = car_id;
        let form = document.forms.CarEditForm;
        form.CarName.value = carName;
        form.CarCost.value = costPerDay;
        form.type.value = carType;
        popUpEdit.style.visibility = "visible"
    }else{
        popUpAdd.style.visibility="visible";
    }


}

function PopUpHide(){
    popUpEdit.style.visibility = "hidden";
    popUpAdd.style.visibility = "hidden"
}

function EditCar(){

    let form = document.forms.CarEditForm;

    fetch('/admin/updateCar',{
        method:'POST',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify({
            carId:carId,
            carName:form.CarName.value,
            type:form.type.value,
            costPerDay:form.CarCost.value
        })
    }).then(result=>{
        if(result.ok){
            PopUpHide();
            window.location.replace(document.location.origin+'/admin/adminpage/CarsInfo');
        }
    })
}

function carAdd(){
    let form = document.forms.CarAddForm;
    fetch('/admin/addCar',{
        method:'POST',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify({
            carName:form.CarName.value,
            type:form.type.value,
            costPerDay:form.CarCost.value
        })
    }).then(result=>{
        if(result.ok){
            PopUpHide();
            window.location.replace(document.URL);
        }
    })
}

function DeleteCar(carId){
    if(confirm('You cant rollback delete, are you sure?')){
        fetch(`/admin/deleteCar/${carId}`,{
            method:'DELETE'
        }).then(result=>{
            if(result.ok){
                window.location.replace(document.location.origin+'/admin/adminpage/CarsInfo');
            }
        })
    }
}

async function EndRent(orderId){
    let result = await fetch(`/admin/endRent/${orderId}`)

    if(result.ok){
        PopUpHide();
        window.location.replace(document.location.origin+'/admin/adminpage/OrdersInfo');
    }

}

async function DeleteOrder(orderId){

    if(confirm('You cant rollback delete, are you sure?')){
        let result = await fetch(`/admin/deleteOrder/${orderId}`,{
            method:'DELETE'
        })

        if(result.ok){
            PopUpHide();
            window.location.replace(document.location.origin+'/admin/adminpage/OrdersInfo');
        }
    }

}