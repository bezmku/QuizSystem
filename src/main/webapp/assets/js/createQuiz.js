const inputGroup = document.querySelector('.input-group');
const previewSection = document.getElementById('preview-section');
const questionCount = document.getElementById('question-count');
const questionList = document.getElementById('question-list');
const emptyMessage = document.getElementById('empty-message');
const addQuestion = document.getElementById('add-question');
const clearForm = document.getElementById('clear');
const sendToDatabase = document.getElementById('submit');

let allQuestions = [];

addQuestion.addEventListener('click', (e) =>{
    e.preventDefault();
const questionText = document.getElementById('question-text').value;

const optA = document.getElementById('option-a').value;
const optB = document.getElementById('option-b').value;
const optC = document.getElementById('option-c').value;
const optD = document.getElementById('option-d').value;

const selectedRadio =document.querySelector('input[name="correct-answer"]:checked');

if(!selectedRadio){
    alert("please give the answer");
    return;
}
const correct =selectedRadio.value;
let currentAnswer={
    text:questionText,
    optA:optA,
    optB:optB,
    optC:optC,
    optD:optD,
    correctAnswer:correct
};
allQuestions.push(currentAnswer);
updatePreview();
resetForm();
});

// ------UPDATE THE PRIVEW-----

function updatePreview(){
questionCount.innerText=allQuestions.length;

if(allQuestions.length>0){
    emptyMessage.style.display='none';
}
const latestQ = allQuestions[allQuestions.length-1];

const previewItem = document.createElement('div');

previewItem.classList.add('preview-item');
previewItem.innerHTML= `
<div class="preview-text">
<strong>${allQuestions.length}.&nbsp; </strong> ${latestQ.text}
</div>
<div class="preview-badge">Correct Answer: ${latestQ.correctAnswer}</div>

`;
questionList.appendChild(previewItem);
}

//---- THE RESETIN FUCNTION------
function resetForm(){
    document.getElementById('question-text').value=""
    document.getElementById('option-a').value="";
    document.getElementById('option-b').value="";
    document.getElementById('option-c').value="";
    document.getElementById('option-d').value="";

   const radio = document.querySelectorAll('input[name="correct-answer"]')

   radio.forEach(r =>{
    r.checked=false;
   });
}

//-------- HANDLE THE CLEAR BUTTON ------
clearForm.addEventListener('click', (e) =>{
    if(confirm("Are you sure you delete all the question created so far?")){
        allQuestions=[];
        questionCount.innerText="0";
        emptyMessage.style.display='block';
        const preview = document.querySelectorAll('.preview-item');
        preview.forEach(p => p.remove());
        resetForm();
    }
});

//----- the logic of sending what the teacher have created so far to database 
sendToDatabase.addEventListener('click', async (e) =>{
    e.preventDefault();
    if(allQuestions.length===0){
        alert("You have not created Any questions yer!");
        return;
    }
    const subject = document.getElementById('subject-id').value;
    const subjectSelect = document.getElementById('subject-id');
    
    if(!subject){
        alert("Please selct subject for the questions");
        return;
    }
    
    const quizData ={
        subjectId: parseInt(subject),
        questions: allQuestions
    }

    try{
    const response = await fetch("api/save-quiz" ,{
        method:'POST',
        headers:{
            'Content-Type' : 'application/json'
        },
        body:JSON.stringify(quizData)
    });
    const result = await response.json();

    if(response.ok && result.success){
        alert("Success: "+result.message);
    allQuestions=[];
    questionCount.innerText="0";
    emptyMessage.style.display='block';
    const preview = document.querySelectorAll('.preview-item'); 
    preview.forEach(p => p.remove());

    resetForm();

    }
    else{
        alert("Error: "+(result.message||"Failed to save quiz to database!"));
    }
    }catch(error){
        console.error("Network Error:",error);
        alert("couldn't connect server! This error is from saveToDatabase catch")
    }
})