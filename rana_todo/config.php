<?php
    $con = mysqli_connect("localhost","root","","rana_db");

    if($con){
        echo "connected";
    }else{
        echo "failed";
    }

?>