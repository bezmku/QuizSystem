const moduleBtns = document.querySelectorAll('.module-btn');

moduleBtns.forEach(btn  =>{
    btn.addEventListener('click' ,() =>{
        const subjectId = btn.getAttribute('data-subject');
        const subjectName = btn.innerText;

        localStorage.setItem('selectedSubjectName',subjectName);

        localStorage.setItem('selectedSubjectId',subjectId);

        window.location.href='quiz.html';

    })
})