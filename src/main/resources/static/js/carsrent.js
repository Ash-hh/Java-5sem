function carRentPageOnload(){

   // setMinDate();

    let curUrl =  document.URL.split('/')
    let carId = curUrl[4]

    fetch(`http://localhost:8080/getrentcar/${carId}`)
        .then(result=>{
            if(result.ok){
                return result.json()
            }
        }).then(data=>{
            CarForRent.innerHTML = `Model: ${data.carName} <br> 
                Type: ${data.type} <br> 
                Cost per day: ${data.costPerDay}`;
    })
}

async function sendRentRequest(){
    let rentStart = document.getElementById("RentStart").value;
    let rentEnd = document.getElementById("RentEnd").value;

    let res = await makeOrderForRent({
        carId:carId,
        dateRentStart:rentStart,
        dateRentEnd:rentEnd
    })

    

}
//TODO:calculate cost
function setMinDate(){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1;
    var yyyy = today.getFullYear();

    if (dd < 10) {
        dd = '0' + dd;
    }

    if (mm < 10) {
        mm = '0' + mm;
    }
    today = yyyy + '-' + mm + '-' + dd;
    RentStart.setAttribute("min", today);
    RentEnd.setAttribute("min", today);
}