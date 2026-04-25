document.addEventListener('DOMContentLoaded' , async () =>{
    try{
        const response = await fetch('api/profile');
        const user = await response.json();

        document.querySelector('.display-username').innerText = user.username;
        document.querySelector('.email').innerText = user.email;
        document.querySelector('.role').innerText = user.role;
        document.querySelector('.profile-avatar').innerText = user.username.charAt(0).toUpperCase();

        const dashboard = document.getElementById('dashboard');
            if(user.role == 'TEACHER'){
                dashboard.href = 'teacherHome.html';
            }
            else dashboard.href = 'index.html';
    }catch(error){
        console.error("Failed to fetch credentials " , error);
        return''
    }
});