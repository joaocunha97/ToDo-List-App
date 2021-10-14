<?php
    include "init.php";

    $fullname = $_POST['fullname'];
    $username = $_POST['username'];
    $password = $_POST['password'];

    $enc_password = md5($password);

    $query = "INSERT into tb_user (fullname, username, password) VALUES ('$fullname','$username','$enc_password' );";

    if(mysqli_query($conn,$query)){
        echo "success";
    }else{
        echo "failed";
    }
    ?>