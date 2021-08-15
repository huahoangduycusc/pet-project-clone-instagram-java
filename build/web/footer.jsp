<div class="duy"><i class="fas fa-angle-up"></i></div>
    <script type="text/javascript">
        $(document).ready(function(){ 
     
            $(window).scroll(function(){
                if ($(this).scrollTop() > 100) {
                    $('.duy').fadeIn();
                } else {
                    $('.duy').fadeOut();
                }
            }); 
     
            $('.duy').click(function(){
                $("html, body").animate({ scrollTop: 0 }, 800);
            });
     
        });
    </script>
    <footer>
        <div class="footer-content">
            <p>© 2020 Hua Hoang Duy. All rights reserved</p>
        </div>
    </footer>
    <script src="/a18138/js/app.js"></script>
</body>
</html>