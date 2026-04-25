document.addEventListener('DOMContentLoaded', async () =>{
 const tableBody = document.querySelector('.results-table tbody') ;
 try{
     const response = await fetch('api/get-results');
     const results = await response.json();
     tableBody.innerHTML = '';

     results.forEach(res => {
         const percent = (res.score/res.total)*100;
         const status = (percent>=50)?'Pass':'Fail';
         const badgeClass = (percent>=50)?'score-high':'score-low';

         const row = document.createElement('tr');
         row.innerHTML=`
        <td>${res.subjectName}</td>
        <td>${res.date}</td>
        <td>${res.total}</td>
        <td>${res.score}/${res.total}</td>
        <td><span class="score-badge ${badgeClass}">${status}</span></td>
    `
         tableBody.appendChild(row);

     });
 }catch(error){
     console.error("Failed to load results history " ,error);
     tableBody.innerHTML='<tr><td colspan="5">Could not load history</td></td>'
 }

});
