$(document).on("click",".like-post",function(){
   $pId = $(this).attr("data-post");
   openTim();
   //alert($pId);
   $(this).toggleClass("fas red");
   $.ajax({
        url : '/a18138/servletPost?id='+$pId,
        cache : false,
        type: 'GET',
        dataType: 'JSON',
        success : function(data){
            //alert(data.message);
            $("#like"+$pId).html(data.numlike);
        },
        error : function(){
            alert("Error, can not like this post...");
        }
     });
});
var timbay = document.querySelector(".timbay");
function openTim(){
    timbay.classList.add("active");
    setTimeout(function(){
        closeTimbay();
    },3000);
}
// close timbay
function closeTimbay(){
    timbay.classList.remove("active");
}