// document.onload = createRadioBtn();
// function createRadioBtn() {
//     //Build an array containing testcenter records.
//
//     var table = document.getElementById("future-id")
//     var row = document.createElement("td");
//     //Get the count of columns.
//     var columnCount = testCenters[0].length;
//     //Add the data rows.
//     //create radiobuttons
//     var r1 = document.createElement('input');
//     r1.type = "radio";
//     row.appendChild(r1);
//     r1.setAttribute("name", "test");
//     r1.setAttribute("id", "radiobtn-" + i);
//
//
//
//     for (var i = 0; i < testCenters.length; i++) {
//         row = table.insertRow(-1);
//         for (var j = 0; j < columnCount; j++) {
//             var cell = row.insertCell(-1);
//             cell.appendChild(r1);
//         }
//
//     }
// }



/* Appointment.html - > Toggle Past and Upcmoming cards  */
var pastCard =document.getElementById("pastCard");
var btToggle = document.getElementById ("bttoggle");
var upcomingCard = document.getElementById("upcomingCard");

btToggle.addEventListener('click',myFunction);


function myFunction()
{
    pastCard.classList.toggle("cardtoggle");
    upcomingCard.classList.toggle("cardtoggle");
}

