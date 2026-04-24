document.addEventListener('DOMContentLoaded', () =>{

    const authForm = document.getElementById('signup-form');

    authForm.addEventListener('submit', async (e) =>{

        e.preventDefault();

        const formData = new FormData(authForm);

        const data = Object.fromEntries(formData.entries());

        const headerText = document.getElementById('signup-text').innerText;

        const endPoint = (headerText==="SIGN UP")? 'api/signup' : 'api/signin';


        try{
            const response = await fetch(endPoint , {
                method: 'POST',
                headers:{
                    'Content-Type':'application/json'
                },
                body:JSON.stringify(data)
            });
            const result = await response.json();

            if(result.success){
                window.location.href=result.redirectURL;
            }
            else{
                alert('Error: '+result.message);
            }
        } catch(error){
            console.error("The 'Handshake' failed",error);
            alert("something went wrong, make sure the server is running!");
        }
    });
});