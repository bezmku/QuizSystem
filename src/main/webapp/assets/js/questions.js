document.addEventListener('DOMContentLoaded', async () =>{
    const questionContainer = document.getElementById('questions-container');

    try{
        const response = await fetch('api/teacher/get-questions')
        const questions = await response.json();
        if(questions.length <= 0){
            questionContainer.innerHTML = `<p style="color:red;">no questions found!!!</p>`
            return;
        }
        questionContainer.innerHTML ='';
        questions.forEach(que => {
            const subject = que.subjectName;
            const questionId = que.id;
            const text = que.text;
            const optA = que.optA;
            const optB = que.optB;
            const optC = que.optC;
            const optD = que.optD;
            const correctAnswer = que.correctAnswer;

            const card = document.createElement('div');
            card.className ='question-card';
            card.innerHTML = `
            <h3 class="question-text"> ${text}<span class="subject">${subject}</span></h3>
                <ul class="option-list">
                    <li class="options">A. ${optA} ${(correctAnswer === 'A')?'<span class="correct">correc</span>' : ''}</li>
                    <li class="options">B. ${optB} ${(correctAnswer === 'B')?'<span class="correct">correc</span>' : ''}</li>
                    <li class="options">C. ${optC} ${(correctAnswer === 'C')?'<span class="correct">correc</span>' : ''}</li>
                    <li class="options">D. ${optD} ${(correctAnswer === 'D')?'<span class="correct">correc</span>' : ''}</li>
                </ul>
            `
            questionContainer.appendChild(card);
        });
    }catch(error){
        console.error("Failed to load questions ", error);
        questionContainer.innerHTML='<p>could\'t load questions from database</p>';
    }
})