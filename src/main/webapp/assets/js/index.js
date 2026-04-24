document.addEventListener('DOMContentLoaded', async () =>{

    const container = document.getElementById('module-container');
    try{

        const response = await fetch('api/subjects');
        const subjects = await response.json();
        container.innerHTML='';
        subjects.forEach(s =>{
            const card = document.createElement('div');
            card.className = 'card';
            card.innerHTML=`<h3>${s.name}</h3><p>Start Quiz</p>`
            card.addEventListener('click',() =>{
                window.localStorage.setItem('selectedSubjectId' ,s.id);
                window.localStorage.setItem('selectedSubjectName',s.name);
                window.location.href='quiz.html';
            });
                container.appendChild(card);
        })
    }catch(error){
        console.error("database failed!!");

    }

});

