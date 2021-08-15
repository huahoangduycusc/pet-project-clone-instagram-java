// click function dropdown
window.onclick = function(event){
    openCloseDropdown(event);
}
var func = document.querySelector('.function');
function openCloseDropdown(event){
    if(!event.target.matches('.dropdown-toggle')){
        func.classList.remove('dropdown');
    }
    else{
        event.preventDefault();
        func.classList.toggle('dropdown');
    }
}