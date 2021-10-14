<?php
include "init.php";

$task_name =$_POST['task_name'];
$task_id = $_POST['task_id'];

$query = "UPDATE tbl_task set task_name = '$task_name' where task_id='$task_id'";

if(mysqli_query($conn,$query)){
    echo "updated";
}else{
    echo "failed";
}

?>