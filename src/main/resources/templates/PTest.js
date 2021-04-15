/* PTest.html -> Display time input field if button is clicked:
   var toggleTime = document.getElementById("newElementId");
   var btn = document.getElementById("addtime");
  btn.onclick= function changeStyle (){
      toggleTime.style = "display:compact";
  }
/* PTest.html - > Display time field when date is selected */

var toggleTime = document.getElementById("newElementId");
var dateSelection = document.getElementById("date_of_test");

dateSelection.onchange = handleChange;

function handleChange(e){
    toggleTime.style= "display:compact";
}

