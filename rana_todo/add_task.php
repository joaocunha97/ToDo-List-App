<?php
include "init.php";

$task_name = $_POST['task_name'];
$user_id = $_POST['user_id'];

$query = "INSERT into tbl_task (task_name, user_id) VALUES ('$task_name','$user_id')";

if(mysqli_query($conn,$query)){
    echo mysqli_insert_id($conn);
}else{
    echo "failed";
}

?>