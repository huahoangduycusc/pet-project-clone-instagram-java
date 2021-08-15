// function delete post
let dPost = $("#pId").val();
function deletePost2(){
    closeFunction();
    swal({
    title: "Are you sure?",
    text: "Once deleted, you will not be able to recover this post!",
    icon: "warning",
    buttons: true,
    dangerMode: true,
  })
  .then((willDelete) => {
    if (willDelete) {
      $.ajax({
         url: '/a18138/updatePost',
         cache: false,
         type: 'GET',
         dataType: 'JSON',
         data : {
             id : dPost
         },
         success : function(data){
             if(data != null)
             {
                 if(data.msg == "no"){
                     swal("Poof! You don't have permission to delete this post !", {
                        icon: "warning",});
                 }
                 else if(data.msg == "success"){
                     swal({ title: "Success!",
                    text: "Delete this post success !",
                    icon: 'success',
                    type: "success"}).then(okay => {
                      if (okay) {
                       window.location.href = "/a18138/admin/index.jsp";
                     }
                   });
                 }
                 else if(data.msg == "error"){
                     swal("Poof! Cannot delete this post due occur error !", {
                        icon: "error",});
                 }
             }
         },
         error: function(){
         swal("Oops! Occur error when deleting post...", {
            icon: "error",
            });
         }
      });
     } 
     else {
      swal("Your post is safe!");
    }
  });
}