var idPost = "";
var jPost = document.querySelector(".post-index-button");
let functionPage = document.querySelector(".function-fixed");
var url = document.querySelector("#url");
jPost.addEventListener('click',(e) => {
    let id = jPost.getAttribute("data-id");
    idPost = id;
    functionPage.classList.add("function-open");
    url.value = "http://localhost:8080/a18138/post/view.jsp?id="+id;
    //alert(id);
    console.log(idPost);
});
// close function page
function closeFunction(){
    if(functionPage)
    {
        
        functionPage.classList.remove("function-open");
    }
}
if(functionPage)
{
    functionPage.addEventListener('click',(e) => {
        if(e.target.classList.contains("function-fixed") || e.target.classList.contains("btn-huy"))
        {
            closeFunction();
        }
    });
}
// function report
function reportPost(){
        swal({
      title: "Are you sure?",
      text: "Do you want report this post ?",
      icon: "warning",
      buttons: true,
      dangerMode: true,
    })
    .then((willDelete) => {
      if (willDelete) {
        $.ajax({
           url: '/a18138/reportPost',
            type: 'GET',
            dataType: 'JSON',
            cache: false,
            data: {
                id : idPost
            },
            success: function(data){
                if(data != null){
                    if(data.msg == "error"){
                        swal("You cannot report this port!", {
                        icon: "error",
                      });
                    }
                    else if(data.msg == "success"){
                        swal("Reported success!", {
                        icon: "success",
                      });
                    }
                }
            },
            error: function(){
                swal("Occur error when reporting this post!", {
                   icon: "error",
                 });
            }
        });
      } else {
        swal("The post is safe!");
      }
    });
    closeFunction();
}
// function go to post
function gotoPost(){
    window.location.href = "/a18138/post/view.jsp?id="+idPost;
}
// function delete post
function deletePost(){
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
             id : idPost
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
                       window.location.href = "/a18138/account/index.jsp";
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
// edit post
function editPost(){
    window.location.href = "/a18138/post/edit.jsp?id="+idPost;
}
// function copy link
function copyLink() {
    /* Get the text field */
    var copyText = url;
  
    /* Select the text field */
    copyText.select();
    copyText.setSelectionRange(0, 99999); /* For mobile devices */
  
    /* Copy the text inside the text field */
    document.execCommand("copy");
  
    /* Alert the copied text */
    appearPopup();
    closeFunction();
}
function appearPopup(){
    var pop = document.querySelector(".pop-up");
    pop.classList.add("pop-up-open");
    setTimeout(function(){
        closePopup();
    },3000);
}
// function close pop up
function closePopup(){
    var pop = document.querySelector(".pop-up");
    pop.classList.remove("pop-up-open");
}
