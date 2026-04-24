document.addEventListener('DOMContentLoaded', () =>{

    const moduleBtns = document.querySelectorAll('.module-btn');
    console.log("found modules",moduleBtns.length)
    moduleBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            const subjectId = btn.getAttribute('data-subject');
            const subjectName = btn.getAttribute('data-name');

            localStorage.setItem('selectedSubjectName', subjectName);

            localStorage.setItem('selectedSubjectId', subjectId);
            console.log("this is what I got: ",subjectId);

            window.location.href = 'quiz.html';

            console.log("this is what I got: ",subjectId);
        });
    });
});

