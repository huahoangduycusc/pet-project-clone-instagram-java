var lPost = 5;
$(document).ready(function(){
    $(document).on("click","#load-more",function(){
    $("#loading").html('<img src="/a18138/images/loading.gif" style="width: 50px;margin-bottom:10px;">');
    $pos = $(this).attr("data-lm");
    //alert($pos);
    $.ajax({
       url: '/a18138/loadPost',
       type: 'GET',
       cache: false,
       data : {
           type : "post",
           row : lPost
       },
       success: function(data){
           lPost += lPost;
           if(data == ""){
               $("#loading").fadeOut();
               $("#load-more").fadeOut();
           }
           $("#loadpost").append(data);
           $("#loading").html("");
       },
       error: function(){
           alert("Occur when loading the list of account...");
       }
    });
 }); 
});