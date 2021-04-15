// document.onload = GenerateTable();



// function GenerateTable() {
//     //Build an array containing testcenter records.
//     var testCenters = new Array();
//     testCenters.push(["4/8/2021", "Rapid", "Troy", "2:00pm", "Test", "Completed", ""]);
//     //Create a HTML Table element.
//     var table = document.getElementById("tbodyid")
//     var row = document.createElement("td");
//     //Get the count of columns.
//     var columnCount = testCenters[0].length;
//     //Add the data rows.
//
//
//     var a = document.createElement('a');
//     var link = document.createTextNode("ReportLink");
//     a.appendChild(link);
//     a.title = "ReportLink";
//     a.href = "";
//
//     for (var i = 0; i < testCenters.length; i++) {
//         row = table.insertRow(-1);
//
//         for (var j = 0; j < columnCount; j++) {
//             var cell = row.insertCell(-1);
//             cell.className = "dateCheck"
//             cell.innerHTML = testCenters[i][j];
//             cell.appendChild(a);
//         }
//     }
//     var button = document.getElementById("reschedule");
//     var appointmentDate = document.querySelector(".dateCheck");
//     var actualDate = appointmentDate.innerText;
//     var today = new Date();
//     actualDate = new Date(actualDate);
//     {
//         if (actualDate <= today)
//             button.classList.add("hidebutton")
//         else
//             button.classList.remove("hidebutton");
//
//     }




// }