<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    
    
    <style>
    .dropzone {
        height: 400px;
        width: 100%;
        border-radius: 10px;
        border: dashed 10px #2c3e50;
        
    }
    
	#back-to-home{
		width:100%;
		height:100px;
	}
	
	#back-to-home-image{
		display:inline-block;
	}
	
	#publish-instruction-text{
    	color:#FFFFFF;
    	font-size: 1.75em;
      	font-weight: 300;
    }
    
    #back-to-home-text{
    	color:#FFFFFF;
    	font-size: 1.75em;
      	font-weight: 300;
      	padding:30px;
      	margin-top:50px;
      	display:inline-block;
      	
    }
    
    .publish-url{
    	color:#FFFFFF;
    	height: 400px;
        width: 100%;
        display:none;
    }
    
    #publish-url-text{
    	color:#FFFFFF;
    	border-radius: 10px;
    	padding:15px;
    	background-color:  #2c3e50;
    	width:80%;
    	text-align:center;
    	font-size: 1.75em;
      	font-weight: 300;
      	
    }
    
    #copy-url-text{
    	background-color:  #2c3e50;
    	border-radius: 10px;
    	display:inline-block;
    	padding:15px;
    	padding-left:30px;
    	padding-right:30px;
    	font-size: 1.75em;
      	font-weight: 300;
    }
    
    .dz-file-preview{
    position: relative;
      top: 50%;
      transform: translateY(-50%);
    
    }
    
.dz-upload { 
    display: block; 
    background-color:  #2c3e50; 
    height: 40px;
    width: 0%;
}

.dz-filename{
      font-size: 1.75em;
      font-weight: 300;
      padding-top: 10px;
      padding-bottom: 10px;
}

    .dz-message{
      font-size: 1.75em;
      font-weight: 300;
      position: relative;
      top: 50%;
      transform: translateY(-50%);
    }
    
html, body {
    margin: 0;
    height: 100%;
    
}
    
</style>
    

    <title>OTA Drop</title>

    <!-- Bootstrap Core CSS - Uses Bootswatch Flatly Theme: http://bootswatch.com/flatly/ -->
    <link href="static/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="static/css/freelancer.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="static/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


        


</head>

<body id="page-top" class="index" style="background-color:#233140;">

	<div id="container">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#page-top">Over The Air Dropbox</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li class="hidden">
                        <a href="#page-top"></a>
                    </li>

                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

    <!-- Header -->
    <header>
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <form action="." class="dropzone" id="myAwesomeDropzone" enctype="multipart/form-data"></form>
                    <div class="publish-url">
 						<p id="publish-instruction-text">Your app is now available for download at the following link:</p>
						<input type="text"  id="publish-url-text" class="js-copytextarea" value=""></input>
						<button id="copy-url-text" class="js-textareacopybtn">Copy</button>
						<div id="back-to-home">
							 <a id="back-to-home-text" href="."><img id="back-to-home-image" src="./static/img/arrow-back-128.png"></img>Back to home</a>
						</div>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <!-- Footer -->
    <footer class="text-center" >
        <div class="footer-above">
            <div class="container">
                <div class="row">
                    <div class="footer-col col-md-4">
                        <h3>Developer</h3>
                        <p>Spencer Whyte</p>
                        <a href="mailto:spencerwhyte@gmail.com">spencerwhyte@gmail.com</a>
                    </div>
                    <div class="footer-col col-md-4">
                        <h3>Around the Web</h3>
                        <ul class="list-inline">
                            <li>
                                <a href="#" class="btn-social btn-outline"><i class="fa fa-fw fa-facebook"></i></a>
                            </li>
                            <li>
                                <a href="#" class="btn-social btn-outline"><i class="fa fa-fw fa-google-plus"></i></a>
                            </li>
                            <li>
                                <a href="#" class="btn-social btn-outline"><i class="fa fa-fw fa-twitter"></i></a>
                            </li>
                            <li>
                                <a href="#" class="btn-social btn-outline"><i class="fa fa-fw fa-linkedin"></i></a>
                            </li>
                            <li>
                                <a href="#" class="btn-social btn-outline"><i class="fa fa-fw fa-dribbble"></i></a>
                            </li>
                        </ul>
                    </div>
                    <div class="footer-col col-md-4">
                        <h3>About OTA Drop</h3>
                        <p>OTA Drop allows you to easily distribute your iOS applications over the air.</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="footer-below">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        Copyright &copy; Spencer Whyte 2016
                    </div>
                </div>
            </div>
        </div>
    </footer>

   

    <!-- jQuery -->
    <script src="static/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="static/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.3/jquery.easing.min.js"></script>
    <script src="static/js/classie.js"></script>
    <script src="static/js/cbpAnimatedHeader.js"></script>

    <!-- Contact Form JavaScript -->
    <script src="static/js/jqBootstrapValidation.js"></script>
    <script src="static/js/contact_me.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="static/js/freelancer.js"></script>
    
<script>
	    $.getScript('static/js/dropzone.js',function(){
	    	  // instantiate the uploader
	    	  $('#myAwesomeDropzone').dropzone({ 

	    	    init: function () {
	    	        this.on("drop", function (file) {
	    	            console.log("dropped");
	    	            $('.dz-error-mark').hide();
	    	            $('.dz-success-mark').hide();
	    	            $('.dz-default').hide();
	    	        });

	    	        this.on("success", function (file, response) {
	    	            console.log("sucesso");
	    	            $('.dz-error-mark').hide();
	    	            $('.dropzone').hide("500");
	    	            $('.publish-url').show("500");
	    	            $('#publish-url-text').val(response.url);
	    	            console.log(response);
	    	           	
	    	            
	    	        });

	    	        this.on("error", function (file, error, xhr) {
	    	            console.log("error");
	    	        });
	    	  	}
	    	    
	    	  });
	    	});
	    
	    
	    
	    
	    var copyTextareaBtn = document.querySelector('.js-textareacopybtn');

	    copyTextareaBtn.addEventListener('click', function(event) {
	      var copyTextarea = document.querySelector('.js-copytextarea');
	      copyTextarea.select();

	      try {
	        var successful = document.execCommand('copy');
	        var msg = successful ? 'successful' : 'unsuccessful';
	        console.log('Copying text command was ' + msg);
	      } catch (err) {
	        console.log('Oops, unable to copy');
	      }
	    });
	    
	</script>
	
	</div>
</body>

</html>
