<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" /> 
  <title>Install ${title}</title>
  <style type="text/css">
    body {
      font-family: Helvetica, arial, sans-serif;
    }
    .step {
      background: white;
      border: 1px <a href="/tag/ccc">#<span class="p-category">ccc</span></a> solid;
      border-radius: 14px;
      padding: 4px 10px;
      margin: 10px 0;
    }
    
    .appName{
    	padding:10px;
    vertical-align: top;
    }
    
    #appImage {
    zoom: 2;  //increase if you have very small images

    display: block;
    margin: auto;

    height: auto;
    max-height: 100%;

    width: auto;
    max-width: 100%;
	}


	.install-button{
		color:#2E7AF2;
		border-style: solid;
		padding:15px;
		text-align:center;
		border-radius: 5px;
		text-decoration:none;
	}
	
	.install-button-container{
		padding:10px;
		float:right;
	}

    table {
      width: 100%;
    }
  </style>
</head>
<body>

<div class="step">
  <table>
  
  <tr>

    <td width="50%">
      <a href="itms-services://?action=download-manifest&url=${manifestUrl}">
        <img id="appImage" src="${iconUrl}" />
      </a>
    </td>
    
    <td width="50%">
    	<h1 class="appName">${title}</h1>
 		<div class="install-button-container">
    		<a href="itms-services://?action=download-manifest&url=${manifestUrl}" class="install-button">INSTALL</a>
    	</div>
    </td>
    
    <td>
    
    </td>
  </tr>
  
  <tr>
  	<td>
  		<h3>Version</h2>
  	</td>
  	<td>
  		<h3>${bundleVersion}</h2>
  	</td>
  </tr>
  
  <tr>
  	<td>
  		<p>Build</p>
  	</td>
  	<td>
  		<p>${buildNumber}</p>
  	</td>
  </tr>
  
  
  </table>
</div>

</body>
</html>
