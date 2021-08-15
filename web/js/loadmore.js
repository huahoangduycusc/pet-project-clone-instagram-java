$(document).ready(function(){
    $(document).on("click","#load-more",function(){
    $("#loading").html('<img src="/a18138/images/loading.gif" style="width: 45px;margin-bottom:10px;">');
    $pos = $(this).attr("data-lm");
    //alert($pos);
    $.ajax({
       url: '/a18138/servletLoadMore',
       type: 'GET',
       cache: false,
       data : {
           id : $pos,
           row : skip
       },
       success: function(data){
           skip += skip;
           if(data == ""){
               $("#loading").fadeOut();
               $("#load-more").fadeOut();
           }
           $("#loadcmt").append(data);
           $("#loading").html("");
       },
       error: function(){
           alert("Occur when loading the list of account...");
       }
    });
 }); 
});