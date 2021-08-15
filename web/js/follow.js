$(document).ready(function(){
    $(document).on('click','.btn-follow-sm',function(){
       $flId = $(this).attr("data-fl");
       //alert($flId);
       $.ajax({
          url: '/a18138/servletAccount',
          type: 'GET',
          dataType: 'JSON',
          cache: false,
          data: {
              id : $flId
          },
          success : function(data){
              if(data.msg == 1){
                  $("#fl"+$flId).html("Follow");
              }
              else{
                  $("#fl"+$flId).html("UnFollow");
              }
          },
          error: function(){
              alert("Occur error when following the user...");
          }
       });
    });
    // load more
});