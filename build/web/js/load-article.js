var lPost = 6;
$(document).ready(function(){
    $(document).on("click","#load-more",function(){
    $("#loading").html('<img src="/a18138/images/loading.gif" style="width: 50px;margin-bottom:10px;">');
    //alert($pos);
    $.ajax({
       url: '/a18138/loadPost',
       type: 'GET',
       cache: false,
       data : {
           type : "myself",
           id: $("#user").val(),
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