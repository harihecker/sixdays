const loginbutton = document.getElementById("loginbutton");

loginbutton.addEventListener('click', async function(event){
    event.preventDefault();
    const Username = document.getElementById("Username").value;
    const Password = document.getElementById("Password").value;
    console.log("username is ",Username);
    console.log("passwprd is ",Password);
    try{
    const response= await fetch("http://127.0.0.1:8080/in",{
        method:"GET",
        "headers":{
            "Content-type":"application/json"
        }
    });
    console.log( await response.text());
    }catch(error){
        console.log("login failed",error.message);
    }


})