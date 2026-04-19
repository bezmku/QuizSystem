const questionElement = document.getElementById('question');
const choices = document.querySelectorAll('.choice-btn');
const nexBtn = document.getElementById('next-btn');
const quitBtn = document.getElementById('quit');
const subjectHeader = document.getElementById('subject');
const finishBtn = document.getElementById('finish');
const selectedSubjectName = localStorage.getItem('selectedSubjectName');
const selectedSubjectId = localStorage.getItem('selectedSubjectId');


let currentIndex = 0;
let listOfQuestions =[];
let userAnswer =[];

if(selectedSubjectName && subjectHeader){
    subjectHeader.innerText = selectedSubjectName;
}


async function loadQuestionsFromServer(){
    try{
        const response = await fetch('api/questions');
        const data = await response.json();
        listOfQuestions = data.map(q => ({

            question:q.text,
            answers:[q.optA,q.optB,q.optC, q.optD],
            correctAnswer:q.correctAnswer

        }));
        userAnswer = new Array(listOfQuestions.length).fill(null);
        if(listOfQuestions.length>0){

            showQuestion();
        }
        else{
            questionElement.innerText="no question found from database";
        }
    }catch(error){
        console.error("Failed to load questions:",error);
        questionElement.innerText = "Error loading element from database";
    }
}

function calculateResult(){
    let score=0;
    for(let i=0; i<userAnswer.length;i++){
        let checkQuestion = listOfQuestions[i]
        if(userAnswer[i]===checkQuestion.correctAnswer){
            score++;
        }

    }
    return score;
}
function showQuestion(){

    let currentQuestion=listOfQuestions[currentIndex];

    questionElement.innerText=currentQuestion.question;

 choices.forEach((btn, i) => {
    btn.innerText = currentQuestion.answers[i];
    let letters =['A','B', 'C','D'];
    if(letters[i]===userAnswer[currentIndex]){
        btn.style.backgroundColor='rgb(0, 204, 255)';
        btn.style.color='black';
    }
    else {
        btn.style.backgroundColor=' rgba(22, 13, 122, 0.5)';
        btn.style.color='white';
    }

 });

 if(currentIndex===listOfQuestions.length-1){

    finishBtn.style.visibility='visible';
    nexBtn.style.visibility='hidden';
 }
 else{

     nexBtn.style.visibility='visible';
    finishBtn.style.visibility='hidden';
 }
    

}
choices.forEach((btn,i) =>{

    btn.addEventListener('click', () => {
        const letters =['A', 'B', 'C','D']
        userAnswer[currentIndex] = letters[i];
        showQuestion();
    })
})
nexBtn.addEventListener('click',() =>{
    if(currentIndex<listOfQuestions.length-1){

        currentIndex++;

    }
    else {
        currentIndex=0;
    }
    showQuestion();
    console.log(userAnswer)
});

finishBtn.addEventListener('click', () =>{
    const score = calculateResult();

    const card = document.getElementById('full-card');

    card.innerHTML = `
    <div style="text-align: center; padding:40px">
    <h1 style="color: #0884a3"> Quize Complete!</h1>
    <p style="color: white; font-size: 1.5rem;">You scored: ${score} / ${listOfQuestions.length}</p>
            <button id="quit" onclick="location.reload()" style="margin-top: 20px; width:auto; height:auto;">Try Again</button>
    </div>
    `
});
if(quitBtn){
quitBtn.addEventListener('click' , ()=>{
    if(confirm("Are you suer you wanna quit? all progress will be lost")){
        localStorage.removeItem('selectedSubjectId');
        localStorage.removeItem('selectedSubjectName');

        window.location.href='studentHome.html';
    }
});
}

loadQuestionsFromServer();
