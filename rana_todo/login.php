<?php
    include "init.php";

    $username = $_POST['username'];
    $password = $_POST['password'];
    $enc_password = md5($password);

    $query = "SELECT * from tb_user where username='$username' AND password = '$enc_password'";

    $result = mysqli_query($conn,$query);

    if(mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        $userId = $row['user_id'];
        $fullname = $row['fullname'];
        $username = $row['username'];
        $password = $row['password'];

        $appuser = array("user_id" => $userId, "fullname" => $fullname, "username" => $username, "password" => $password);
        echo json_encode($appuser);
    }
    else {
        echo "Login_failed";
    }

    ?>